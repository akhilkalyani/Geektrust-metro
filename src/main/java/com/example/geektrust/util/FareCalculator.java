package com.example.geektrust.util;

import com.example.geektrust.model.PassengerType;

public class FareCalculator {
    public static int getFare(PassengerType type) {
        switch (type) {
            case ADULT: return 200;
            case SENIOR_CITIZEN: return 100;
            case KID: return 50;
            default: return 0;
        }
    }
}
