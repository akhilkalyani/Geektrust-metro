package com.example.geektrust.repository;

import com.example.geektrust.model.MetroCard;

import java.util.HashMap;
import java.util.Map;

public class MetroCardRepository {
    private final Map<String, MetroCard> metroCards = new HashMap<>();

    public void addCard(String cardId, int balance) {
        metroCards.put(cardId, new MetroCard(cardId, balance));
    }

    public MetroCard getCard(String cardId) {
        return metroCards.get(cardId);
    }

    public boolean hasCard(String cardId) {
        return metroCards.containsKey(cardId);
    }
}
