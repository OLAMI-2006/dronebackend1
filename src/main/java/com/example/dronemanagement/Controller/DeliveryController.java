package com.example.dronemanagement.Controller;

import com.example.dronemanagement.Repository.DeliveryRequestRepository;
import com.example.dronemanagement.model.DeliveryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deleveries")
@CrossOrigin(origins = {"http://localhost:5174", "https://your-frontend-app.vercel.app"})
public class DeliveryController {
private  final DeliveryRequestRepository deliveryRequestRepository;

 public  DeliveryController(DeliveryRequestRepository deliveryRequestRepository) {
     this.deliveryRequestRepository = deliveryRequestRepository;
 }

 //creating a new delivery request
 @PostMapping
 public ResponseEntity<DeliveryRequest> createRequest(@RequestBody DeliveryRequest request) {
     DeliveryRequest savedRequest = deliveryRequestRepository.save(request);
     return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
 }
 @GetMapping
 public ResponseEntity<List<DeliveryRequest>> getAllDeliveries() {
     return new ResponseEntity<>(deliveryRequestRepository.findAll(), HttpStatus.OK);
 }

}
