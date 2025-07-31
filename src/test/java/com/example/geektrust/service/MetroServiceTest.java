package com.example.geektrust.service;

import com.example.geektrust.model.PassengerType;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MetroServiceTest {
    private MetroCardRepository cardRepo;
    private StationStatsRepository statsRepo;
    private MetroService metroService;

    @BeforeEach
    void setup() {
        cardRepo = new MetroCardRepository();
        statsRepo = new StationStatsRepository();
        metroService = new MetroService(cardRepo, statsRepo);
    }

    @Test
    void testSampleInput1() throws Exception {
        assertOutputMatches("sample_input/input1.txt", "sample_output/output1.txt");
    }

    @Test
    void testSampleInput2() throws Exception {
        assertOutputMatches("sample_input/input2.txt", "sample_output/output2.txt");
    }

    private void assertOutputMatches(String inputFilePath, String expectedOutputPath) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Feed input commands
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processCommand(line.trim());
            }
        }

        metroService.printSummary();
        System.setOut(oldOut); // Reset stdout

        String actualOutput = outputStream.toString().strip();

        // Load expected output from file
        String expectedOutput = Files.readString(Paths.get(expectedOutputPath)).strip();

        assertLinesMatch(
                Arrays.asList(expectedOutput.split("\\R")),
                Arrays.asList(actualOutput.split("\\R"))
        );
    }

    private void processCommand(String line) {
        String[] tokens = line.split(" ");

        switch (tokens[0]) {
            case "BALANCE":
                metroService.loadBalance(tokens[1], Integer.parseInt(tokens[2]));
                break;
            case "CHECK_IN":
                metroService.checkIn(tokens[1],
                        PassengerType.valueOf(tokens[2]),
                        tokens[3]);
                break;
            case "PRINT_SUMMARY":
                // skip, we call it manually later
                break;
        }
    }

}