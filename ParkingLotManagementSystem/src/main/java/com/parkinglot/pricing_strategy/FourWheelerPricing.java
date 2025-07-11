package com.parkinglot.pricing_strategy;

import java.time.Duration;

import org.springframework.stereotype.Component;

@Component(value = "fourWheelerPricing")
public class FourWheelerPricing implements PricingStrategy {

	private static final int RATE_PER_MINUTES = 40; 

	@Override
	public int calculatePrice(Duration duration) {
		long minutes = duration.toMinutes();//.toHours();
		if (minutes == 0)
			minutes = 5; // Minimum 1 hour
		return (int) (minutes * RATE_PER_MINUTES);
	}
}