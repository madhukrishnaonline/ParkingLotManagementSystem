package com.parkinglot.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkResponseDTO {
    private String ticketId;
    private Long spotId;
    private String vehicleNo;
    private String vehicleType;
    private LocalDateTime entryTime;
    private String message;
}