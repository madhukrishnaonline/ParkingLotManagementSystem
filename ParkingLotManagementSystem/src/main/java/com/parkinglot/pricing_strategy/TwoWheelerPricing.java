package com.parkinglot.pricing_strategy;

import java.time.Duration;

import org.springframework.stereotype.Component;

@Component(value = "twoWheelerPricing")
public class TwoWheelerPricing implements PricingStrategy {

	private static final int RATE_PER_HOUR = 20;

	@Override
	public int calculatePrice(Duration duration) {
		long hours = duration.toHours();
		if (hours == 0)
			hours = 1; // Minimum billing of 1 hour
		return (int) (hours * RATE_PER_HOUR);
	}
}