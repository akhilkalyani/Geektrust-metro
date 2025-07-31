package com.example.geektrust.model;

public class MetroCard {
    private final String cardId;
    private int balance;

    public MetroCard(String cardId, int balance) {
        this.cardId = cardId;
        this.balance = balance;
    }

    public String getCardId() {
        return cardId;
    }

    public int getBalance() {
        return balance;
    }

    public void deduct(int amount) {
        this.balance -= amount;
    }

    public void recharge(int amount) {
        this.balance += amount;
    }
}
