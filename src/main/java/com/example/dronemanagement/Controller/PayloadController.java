package com.example.dronemanagement.Controller;

import com.example.dronemanagement.Dto.PayloadRequest;
import com.example.dronemanagement.model.DeliveryRequest;
import com.example.dronemanagement.model.DeliveryStatus;
import com.example.dronemanagement.model.Medication;
import com.example.dronemanagement.model.Payload;
import com.example.dronemanagement.Repository.DeliveryRequestRepository;
import com.example.dronemanagement.Repository.MedicationRepository;
import com.example.dronemanagement.Repository.PayloadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payloads")
@CrossOrigin(origins = {"http://localhost:5174", "http://localhost:5173", "https://frontenddrone.vercel.app"})
public class PayloadController {

    private final PayloadRepository payloadRepository;
    private final MedicationRepository medicationRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    public PayloadController(
            PayloadRepository payloadRepository,
            MedicationRepository medicationRepository,
            DeliveryRequestRepository deliveryRequestRepository) {
        this.payloadRepository = payloadRepository;
        this.medicationRepository = medicationRepository;
        this.deliveryRequestRepository = deliveryRequestRepository;
    }

    @PostMapping
    public ResponseEntity<?> createPayload(@RequestBody PayloadRequest request) {
        try {

            Medication medication = medicationRepository.findById(request.getMedicationId())
                    .orElseThrow(() -> new RuntimeException("Medication not found with ID: " + request.getMedicationId()));


            DeliveryRequest deliveryRequest = new DeliveryRequest();
            deliveryRequest.setDestination(request.getDestination());
            deliveryRequest.setStatus(DeliveryStatus.valueOf("PENDING"));
            DeliveryRequest savedRequest = deliveryRequestRepository.save(deliveryRequest);


            Payload payload = new Payload();
            payload.setMedication(medication);
            payload.setQuantity(request.getQuantity());
            payload.setDeliveryRequest(savedRequest);

            Payload savedPayload = payloadRepository.save(payload);
            return new ResponseEntity<>(savedPayload, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating payload: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Payload>> getAllPayloads() {
        return new ResponseEntity<>(payloadRepository.findAll(), HttpStatus.OK);
    }
}
