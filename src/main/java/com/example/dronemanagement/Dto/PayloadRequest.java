package com.example.dronemanagement.Dto;

import lombok.Data;

@Data
public class PayloadRequest {
    private Long medicationId;
    private Integer quantity;
    private String destination;
}
