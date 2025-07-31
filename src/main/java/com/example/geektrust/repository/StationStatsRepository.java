package com.example.geektrust.repository;

import com.example.geektrust.model.PassengerType;

import java.util.HashMap;
import java.util.Map;

public class StationStatsRepository {
    private final Map<String, Integer> amountCollected = new HashMap<>();
    private final Map<String, Integer> discountGiven = new HashMap<>();
    private final Map<String, Map<PassengerType, Integer>> passengerCounts = new HashMap<>();

    public void addCollection(String station, int amount, int discount) {
        amountCollected.put(station, amountCollected.getOrDefault(station, 0) + amount);
        discountGiven.put(station, discountGiven.getOrDefault(station, 0) + discount);
    }

    public void addPassenger(String station, PassengerType type) {
        passengerCounts.putIfAbsent(station, new HashMap<>());
        Map<PassengerType, Integer> map = passengerCounts.get(station);
        map.put(type, map.getOrDefault(type, 0) + 1);
    }

    public int getAmountCollected(String station) {
        return amountCollected.getOrDefault(station, 0);
    }

    public int getDiscountGiven(String station) {
        return discountGiven.getOrDefault(station, 0);
    }

    public Map<PassengerType, Integer> getPassengerSummary(String station) {
        return passengerCounts.getOrDefault(station, new HashMap<>());
    }
}
