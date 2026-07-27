package com.example.dronemanagement.Controller;

import com.example.dronemanagement.Dto.CommandRequest;
import com.example.dronemanagement.Service.DroneCommandService;
import com.example.dronemanagement.Service.DroneSimulationService;
import com.example.dronemanagement.Service.DroneService;
import com.example.dronemanagement.model.Drone;
import com.example.dronemanagement.model.DroneStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drones")
@CrossOrigin(origins = {"http://localhost:5174", "https://your-frontend-app.vercel.app"})
public class DroneController {

    private final DroneService droneService;
    private final DroneSimulationService droneSimulationService;
    private final DroneCommandService droneCommandService;
    private final SimpMessagingTemplate messagingTemplate;

    public DroneController(
            DroneService droneService,
            DroneSimulationService droneSimulationService,
            DroneCommandService droneCommandService,
            SimpMessagingTemplate messagingTemplate) {
        this.droneService = droneService;
        this.droneSimulationService = droneSimulationService;
        this.droneCommandService = droneCommandService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<?> registerDrone(@RequestBody Drone drone) {
        try {
            Drone savedDrone = droneService.registerDrone(drone);
            return ResponseEntity.ok(savedDrone);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Drone>> getAllDrones() {
        return ResponseEntity.ok(droneService.getAllDrones());
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<DroneStatus> getDroneStatus(@PathVariable Long id) {
        return droneService.getDroneById(id)
                .map(drone -> ResponseEntity.ok(drone.getStatus()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/takeoff")
    public ResponseEntity<?> takeoffDrone(@PathVariable Long id, @RequestBody(required = false) Map<String, Double> payload) {
        Double targetLat = (payload != null && payload.containsKey("targetLat")) ? payload.get("targetLat") : 6.5244;
        Double targetLng = (payload != null && payload.containsKey("targetLng")) ? payload.get("targetLng") : 3.3792;

        if (targetLat == null || targetLng == null) {
            return ResponseEntity.badRequest().body("Error: Missing target latitude or longitude.");
        }

        droneSimulationService.startDroneFlight(id, targetLat, targetLng);
        return ResponseEntity.accepted().body("Drone " + id + " takeoff command accepted. Flight simulation started in background.");
    }

    @PostMapping("/{id}/command")
    public ResponseEntity<?> sendCommand(@PathVariable Long id, @RequestBody CommandRequest request) {
        try {
            String command = request.getCommand();
            if (command == null || command.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Missing command type.");
            }

            Drone updatedDrone = droneCommandService.processCommand(id, command);
            messagingTemplate.convertAndSend("/topic/telemetry", updatedDrone);

            return ResponseEntity.ok(Map.of(
                    "status", "SUCCESS",
                    "message", "Command " + command.toUpperCase() + " executed successfully for drone " + id,
                    "drone", updatedDrone
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to process command: " + e.getMessage()));
        }
    }
}