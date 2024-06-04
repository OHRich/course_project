package org.example.tanchiki.services;

import java.util.Random;

import static org.example.tanchiki.GlobalConstance.mapSize;

public class RandGenerate {

    private final static RandGenerate INSTANCE = new RandGenerate();

    private final Random random;

    private RandGenerate() {
        random = new Random();
    }

    public int getRanBetween(int from, int to) {
        int result;
        do {
            result = random.nextInt(to);
        } while (!(result >= from && result < to));
        return result;
    }

    public int getRanEnemyLoc() {
        return switch (random.nextInt(4)) {
            case 0 -> 0;
            case 1 -> mapSize / 4 + 1;
            case 2 -> mapSize / 4 * 3;
            default -> mapSize - 1;
        };
    }

    public static RandGenerate getINSTANCE() {
        return INSTANCE;
    }
}
