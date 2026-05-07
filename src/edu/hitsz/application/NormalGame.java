package edu.hitsz.application;

public class NormalGame extends Game {

    public NormalGame(AircraftWarApp app) {
        super("normal", app);
    }

    @Override
    protected double initialEnemySpawnCycle() {
        return 25;
    }

    @Override
    protected double initialHeroShootCycle() {
        return 12;
    }

    @Override
    protected double initialEnemyShootCycle() {
        return 32;
    }

    @Override
    protected void applyDifficultyProgression() {
        enemySpawnCycle = Math.max(16, enemySpawnCycle - 0.75);
        enemySpeedMultiplier = Math.min(1.5, enemySpeedMultiplier + 0.09);
        enemyHpMultiplier = Math.min(1.5, enemyHpMultiplier + 0.09);
        System.out.printf(
                "Normal difficulty upgraded: enemySpawnCycle=%.1f, enemySpeedMultiplier=%.2f, enemyHpMultiplier=%.2f%n",
                enemySpawnCycle, enemySpeedMultiplier, enemyHpMultiplier);
    }

    @Override
    protected double supplyDropProbability() {
        return 0.25;
    }
}