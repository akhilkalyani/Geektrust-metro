package com.example.geektrust.command;

import com.example.geektrust.service.MetroService;

public class BalanceCommand implements ICommand{
    private int balance;
    private final String cardId;
    private final MetroService metroService;

    public BalanceCommand(String[] tokens, MetroService metroService){
        this.cardId=tokens[1];
        this.balance=Integer.parseInt(tokens[2]);
        this.metroService = metroService;
    }
    @Override
    public void run() {
        metroService.loadBalance(cardId, balance);
    }
}
