package com.parkinglot.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SpotStatusDTO {
    private Long spotId;
    private boolean empty;
    private String lastVehicleNo;
    private LocalDateTime lastExitTime;
}