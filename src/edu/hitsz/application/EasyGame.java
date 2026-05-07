package edu.hitsz.application;

public class EasyGame extends Game {

    public EasyGame(AircraftWarApp app) {
        super("easy", app);
    }

    @Override
    protected double initialEnemySpawnCycle() {
        return 28;
    }

    @Override
    protected double initialHeroShootCycle() {
        return 12;
    }

    @Override
    protected double initialEnemyShootCycle() {
        return 36;
    }

    @Override
    protected int maxEnemyCount() {
        return 5;
    }

    @Override
    protected double mobEnemyProbability() {
        return 0.70;
    }

    @Override
    protected double eliteEnemyProbability() {
        return 0.20;
    }

    @Override
    protected double elitePlusEnemyProbability() {
        return 0.08;
    }

    @Override
    protected boolean canCreateBoss() {
        return false;
    }

    @Override
    protected double supplyDropProbability() {
        return 0.40;
    }
}