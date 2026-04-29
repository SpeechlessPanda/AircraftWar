package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.MobEnemy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameDifficultyTemplateTest {

    @Test
    void factoryCreatesDifficultySpecificGameSubclasses() {
        assertTrue(EasyGame.class.isInstance(GameFactory.create("easy", null)));
        assertTrue(NormalGame.class.isInstance(GameFactory.create("normal", null)));
        assertTrue(HardGame.class.isInstance(GameFactory.create("hard", null)));
        assertTrue(NormalGame.class.isInstance(GameFactory.create("unknown", null)));
    }

    @Test
    void easyDifficultyDoesNotCreateBoss() {
        Game game = new EasyGame(null);

        assertNull(game.createBossForTest(500));
    }

    @Test
    void normalBossHpStaysFixedButHardBossHpIncreases() {
        BossEnemy firstNormal = (BossEnemy) new NormalGame(null).createBossForTest(500);
        BossEnemy secondNormal = (BossEnemy) new NormalGame(null).createBossForTest(1000);
        assertEquals(firstNormal.getHp(), secondNormal.getHp());

        HardGame hard = new HardGame(null);
        BossEnemy firstHard = (BossEnemy) hard.createBossForTest(500);
        BossEnemy secondHard = (BossEnemy) hard.createBossForTest(1000);
        assertTrue(secondHard.getHp() > firstHard.getHp());
    }

    @Test
    void normalAndHardProgressDifficultyButEasyDoesNot() {
        EasyGame easy = new EasyGame(null);
        double easySpawn = easy.getEnemySpawnCycleForTest();
        easy.applyDifficultyProgressionForTest();
        assertEquals(easySpawn, easy.getEnemySpawnCycleForTest());

        NormalGame normal = new NormalGame(null);
        double normalSpawn = normal.getEnemySpawnCycleForTest();
        normal.applyDifficultyProgressionForTest();
        assertTrue(normal.getEnemySpawnCycleForTest() < normalSpawn);

        HardGame hard = new HardGame(null);
        double hardHeroShoot = hard.getHeroShootCycleForTest();
        hard.applyDifficultyProgressionForTest();
        assertTrue(hard.getHeroShootCycleForTest() < hardHeroShoot);
    }

    @Test
    void progressionScalesNewlyCreatedEnemySpeedAndHp() {
        NormalGame normal = new NormalGame(null);
        normal.applyDifficultyProgressionForTest();
        MobEnemy mob = MobEnemy.createDefault(20, 20);

        normal.scaleEnemyForTest(mob);

        assertTrue(mob.getSpeedY() > 6);
        assertTrue(mob.getHp() > 30);
    }
}