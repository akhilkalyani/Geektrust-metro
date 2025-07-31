package com.example.geektrust.command;

import com.example.geektrust.model.PassengerType;
import com.example.geektrust.service.MetroService;

public class CheckInCommand implements ICommand {
    private final MetroService metroService;
    private final String cardId;
    private final PassengerType paasengertype;
    private final String station;

    public CheckInCommand(String[] tokens, MetroService metroService) {
        this.metroService = metroService;
        cardId = tokens[1];
        paasengertype = PassengerType.valueOf(tokens[2]);
        station = tokens[3];
    }

    @Override
    public void run() {
        metroService.checkIn(cardId, paasengertype, station);
    }
}
