package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;

public class HardGame extends Game {

    private int bossCreateCount = 0;

    public HardGame(AircraftWarApp app) {
        super("hard", app);
    }

    @Override
    protected double initialEnemySpawnCycle() {
        return 22;
    }

    @Override
    protected double initialHeroShootCycle() {
        return 14;
    }

    @Override
    protected double initialEnemyShootCycle() {
        return 28;
    }

    @Override
    protected int maxEnemyCount() {
        return 7;
    }

    @Override
    protected double mobEnemyProbability() {
        return 0.38;
    }

    @Override
    protected double eliteEnemyProbability() {
        return 0.25;
    }

    @Override
    protected double elitePlusEnemyProbability() {
        return 0.22;
    }

    @Override
    protected AbstractAircraft createBossEnemy(int locationX, int locationY) {
        BossEnemy bossEnemy = (BossEnemy) BossEnemy.createDefault(locationX, locationY);
        bossEnemy.increaseMaxHp(1.0 + bossCreateCount * 0.15);
        bossCreateCount++;
        return bossEnemy;
    }

    @Override
    protected void applyDifficultyProgression() {
        enemySpawnCycle = Math.max(13, enemySpawnCycle - 0.75);
        enemySpeedMultiplier = Math.min(1.8, enemySpeedMultiplier + 0.08);
        enemyHpMultiplier = Math.min(1.8, enemyHpMultiplier + 0.08);
        heroShootCycle = Math.max(8, heroShootCycle - 1);
        enemyShootCycle = Math.max(16, enemyShootCycle - 0.75);
        System.out.printf(
                "Hard difficulty upgraded: enemySpawnCycle=%.1f, enemySpeedMultiplier=%.2f, enemyHpMultiplier=%.2f, heroShootCycle=%.1f, enemyShootCycle=%.1f%n",
                enemySpawnCycle, enemySpeedMultiplier, enemyHpMultiplier, heroShootCycle, enemyShootCycle);
    }

    @Override
    protected double supplyDropProbability() {
        return 0.30;
    }

    @Override
    protected int bossSupplyDropCount() {
        return 4;
    }
}