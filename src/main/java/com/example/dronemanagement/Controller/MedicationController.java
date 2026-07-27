package com.example.dronemanagement.Controller;

import com.example.dronemanagement.model.Medication;
import com.example.dronemanagement.Service.MedicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
@CrossOrigin(origins = {"http://localhost:5174", "http://localhost:5173", "https://frontenddrone.vercel.app"})
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @PostMapping("/load/{droneId}")
    public ResponseEntity<?> loadMedication(@PathVariable Long droneId, @RequestBody Medication medication) {
        try {
            Medication savedMedication = medicationService.loadMedicationToDrone(droneId, medication);
            return ResponseEntity.ok(savedMedication);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
