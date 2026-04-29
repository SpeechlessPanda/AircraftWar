package edu.hitsz.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBalanceAchievementTest {

    @Test
    void difficultiesExposeDifferentSupplyAndBossRewardBalance() {
        EasyGame easy = new EasyGame(null);
        NormalGame normal = new NormalGame(null);
        HardGame hard = new HardGame(null);

        assertTrue(easy.getSupplyDropProbabilityForTest() > normal.getSupplyDropProbabilityForTest());
        assertTrue(normal.getSupplyDropProbabilityForTest() > hard.getSupplyDropProbabilityForTest());
        assertTrue(easy.getSupplyDropProbabilityForTest() > 0.85);
        assertTrue(normal.getSupplyDropProbabilityForTest() > 0.70);
        assertTrue(hard.getSupplyDropProbabilityForTest() > 0.55);
        assertTrue(hard.getBossSupplyDropCountForTest() > normal.getBossSupplyDropCountForTest());
    }

    @Test
    void normalAndHardAreSofterButStillDistinctFromEasy() {
        EasyGame easy = new EasyGame(null);
        NormalGame normal = new NormalGame(null);
        HardGame hard = new HardGame(null);

        assertTrue(normal.initialEnemySpawnCycle() >= 24);
        assertTrue(hard.initialEnemySpawnCycle() >= 22);
        assertTrue(normal.initialEnemyShootCycle() >= 30);
        assertTrue(hard.initialEnemyShootCycle() >= 28);
        assertTrue(normal.maxEnemyCount() > easy.maxEnemyCount());
        assertTrue(hard.maxEnemyCount() >= normal.maxEnemyCount());
        assertTrue(!easy.canCreateBoss() && normal.canCreateBoss() && hard.canCreateBoss());
    }

    @Test
    void scoreBombAndFreezeEventsUnlockAchievements() {
        NormalGame game = new NormalGame(null);

        game.addScoreForTest(1000);
        game.unlockBombSweepForTest(30);
        game.unlockFreezeControlForTest(3);

        assertTrue(game.getAchievementManagerForTest().getUnlockedAchievements().contains(Achievement.SCORE_1000));
        assertTrue(game.getAchievementManagerForTest().getUnlockedAchievements().contains(Achievement.BOMB_SWEEP));
        assertTrue(game.getAchievementManagerForTest().getUnlockedAchievements().contains(Achievement.FROST_CONTROL));
    }
}