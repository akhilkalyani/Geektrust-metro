package com.example.geektrust.model;

public class Journey {
    private final String cardId;
    private final PassengerType passengerType;
    private final String fromStation;

    public Journey(String cardId, PassengerType passengerType, String fromStation) {
        this.cardId = cardId;
        this.passengerType = passengerType;
        this.fromStation = fromStation;
    }

    public String getCardId() {
        return cardId;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }

    public String getFromStation() {
        return fromStation;
    }
}
