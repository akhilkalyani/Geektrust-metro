package com.example.geektrust.application;

import com.example.geektrust.command.BalanceCommand;
import com.example.geektrust.command.CheckInCommand;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationStatsRepository;
import com.example.geektrust.service.MetroService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MetroApplication {
    private final MetroService metroService;
    private BufferedReader reader;

    public MetroApplication(String filePath) {
        MetroCardRepository cardRepo = new MetroCardRepository();
        StationStatsRepository statsRepo = new StationStatsRepository();
        metroService = new MetroService(cardRepo, statsRepo);
        reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
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

    private void processCommand(String line, MetroService metroService) {
        String[] tokens = line.split(" ");
        switch (tokens[0]) {
            case "BALANCE":
                new BalanceCommand(tokens,metroService).run();
                break;
            case "CHECK_IN":
                new CheckInCommand(tokens,metroService).run();
                break;
            case "PRINT_SUMMARY":
                metroService.printSummary();
                break;
            default:
                System.out.println("Invalid command: " + line);
        }
    }
}
