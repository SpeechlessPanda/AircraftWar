package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.aircraft.MobEnemy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameSpawnLimitTest {

    @Test
    void rejectSpawnWhenOverallEnemyLimitIsReached() {
        List<AbstractAircraft> enemies = Arrays.asList(
                MobEnemy.createDefault(10, 10),
                MobEnemy.createDefault(20, 10),
                MobEnemy.createDefault(25, 10),
                EliteEnemy.createDefault(30, 10),
                ElitePlusEnemy.createDefault(35, 10),
                EliteProEnemy.createDefault(40, 10),
                MobEnemy.createDefault(45, 10));

        assertFalse(Game.canSpawnEnemy(enemies, MobEnemy.createDefault(50, 10)));
    }

    @Test
    void rejectSecondEliteOfSameType() {
        List<AbstractAircraft> enemies = Collections.singletonList(
                EliteProEnemy.createDefault(30, 10));

        assertFalse(Game.canSpawnEnemy(enemies, EliteProEnemy.createDefault(60, 10)));
        assertTrue(Game.canSpawnEnemy(enemies, MobEnemy.createDefault(60, 10)));
    }

    @Test
    void rejectSpawnWhenCombinedEliteCountWouldExceedThree() {
        List<AbstractAircraft> enemies = Arrays.asList(
                EliteEnemy.createDefault(20, 10),
                ElitePlusEnemy.createDefault(30, 10),
                EliteProEnemy.createDefault(40, 10));

        assertFalse(Game.canSpawnEnemy(enemies, ElitePlusEnemy.createDefault(60, 10)));
    }

    @Test
    void rejectBossDuplicateAndEliteDuplicate() {
        assertFalse(Game.canSpawnEnemy(Collections.singletonList(EliteEnemy.createDefault(20, 10)),
                EliteEnemy.createDefault(40, 10)));
        assertFalse(Game.canSpawnEnemy(Collections.singletonList(BossEnemy.createDefault(20, 10)),
                BossEnemy.createDefault(40, 10)));
    }
}
