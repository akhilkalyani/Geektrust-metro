package com.example.geektrust;

import com.example.geektrust.model.PassengerType;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationStatsRepository;
import com.example.geektrust.service.MetroService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = args[0];

        MetroCardRepository cardRepo = new MetroCardRepository();
        StationStatsRepository statsRepo = new StationStatsRepository();
        MetroService metroService = new MetroService(cardRepo, statsRepo);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;

        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            processCommand(line.trim(), metroService);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processCommand(String line, MetroService metroService) {
        String[] tokens = line.split(" ");

        switch (tokens[0]) {
            case "BALANCE":
                String cardId = tokens[1];
                int balance = Integer.parseInt(tokens[2]);
                metroService.loadBalance(cardId, balance);
                break;

            case "CHECK_IN":
                cardId = tokens[1];
                PassengerType type = PassengerType.valueOf(tokens[2]);
                String station = tokens[3];
                metroService.checkIn(cardId, type, station);
                break;

            case "PRINT_SUMMARY":
                metroService.printSummary();
                break;

            default:
                System.out.println("Invalid command: " + line);
        }
    }
}
