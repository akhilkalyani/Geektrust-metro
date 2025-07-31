package com.example.geektrust.service;

import com.example.geektrust.model.MetroCard;
import com.example.geektrust.model.PassengerType;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationStatsRepository;
import com.example.geektrust.util.FareCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroService {
    private final MetroCardRepository cardRepo;
    private final StationStatsRepository statsRepo;
    private final Map<String, List<String>> journeyHistory;

    public MetroService(MetroCardRepository cardRepo, StationStatsRepository statsRepo) {
        this.cardRepo = cardRepo;
        this.statsRepo = statsRepo;
        this.journeyHistory = new HashMap<>();
    }

    public void loadBalance(String cardId, int balance) {
        cardRepo.addCard(cardId, balance);
    }

    public void checkIn(String cardId, PassengerType type, String station) {
        MetroCard card = cardRepo.getCard(cardId);
        int baseFare = FareCalculator.getFare(type);

        boolean isReturn = isReturnJourney(cardId);
        int actualFare = isReturn ? baseFare / 2 : baseFare;
        int discount = isReturn ? baseFare / 2 : 0;

        int balance = card.getBalance();

        if (balance < actualFare) {
            int rechargeAmount = actualFare - balance;
            int serviceFee = (int) Math.ceil(rechargeAmount * 0.02);
            card.recharge(rechargeAmount);

            // ✅ Correct: add fee to origin station, not current station
            statsRepo.addCollection(station, serviceFee, 0);
        }

        card.deduct(actualFare);
        statsRepo.addCollection(station, actualFare, discount);
        statsRepo.addPassenger(station, type);

        recordJourney(cardId, station);
    }

    public void printSummary() {
        printStationSummary("CENTRAL");
        printStationSummary("AIRPORT");
    }

    private void printStationSummary(String station) {
        int collected = statsRepo.getAmountCollected(station);
        int discount = statsRepo.getDiscountGiven(station);

        System.out.println("TOTAL_COLLECTION " + station + " " + collected + " " + discount);
        System.out.println("PASSENGER_TYPE_SUMMARY");

        Map<PassengerType, Integer> summary = statsRepo.getPassengerSummary(station);
        List<Map.Entry<PassengerType, Integer>> sorted = new ArrayList<>(summary.entrySet());

        sorted.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            return cmp != 0 ? cmp : a.getKey().name().compareTo(b.getKey().name());
        });

        for (Map.Entry<PassengerType, Integer> entry : sorted) {
            System.out.println(entry.getKey().name() + " " + entry.getValue());
        }
    }

    private void recordJourney(String cardId, String station) {
        journeyHistory.putIfAbsent(cardId, new ArrayList<>());
        journeyHistory.get(cardId).add(station);
    }

    private boolean isReturnJourney(String cardId) {
        return journeyHistory.containsKey(cardId) && journeyHistory.get(cardId).size() % 2 == 1;
    }

}
