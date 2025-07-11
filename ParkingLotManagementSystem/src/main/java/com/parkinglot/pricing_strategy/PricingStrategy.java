package com.parkinglot.pricing_strategy;

import java.time.Duration;

public interface PricingStrategy {

	int calculatePrice(Duration duration);
}