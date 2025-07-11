
package com.parkinglot.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ticket")
public class Ticket {

	@Id
	private String id = UUID.randomUUID().toString();

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime entryTime;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime exitTime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "spot_id")
	private ParkingSpot spot;

	@OneToOne(cascade = CascadeType.ALL)
	private Vehicle vehicle;

	private Integer price;

	private boolean paid = false;
}