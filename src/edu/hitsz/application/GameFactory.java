package edu.hitsz.application;

public class GameFactory {

    private GameFactory() {
    }

    public static Game create(String difficulty, AircraftWarApp app) {
        if (difficulty == null) {
            return new NormalGame(app);
        }
        switch (difficulty.trim().toLowerCase()) {
            case "easy":
                return new EasyGame(app);
            case "hard":
                return new HardGame(app);
            case "normal":
            default:
                return new NormalGame(app);
        }
    }
}