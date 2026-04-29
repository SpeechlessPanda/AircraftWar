package edu.hitsz.application;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AchievementManagerTest {

    @Test
    void unlocksAchievementOnlyOnceAndKeepsOrder() {
        AchievementManager manager = new AchievementManager();

        assertTrue(manager.unlock(Achievement.SCORE_1000));
        assertFalse(manager.unlock(Achievement.SCORE_1000));
        assertTrue(manager.unlock(Achievement.BOMB_SWEEP));

        assertEquals(List.of(Achievement.SCORE_1000, Achievement.BOMB_SWEEP), manager.getUnlockedAchievements());
    }

    @Test
    void summaryShowsDisplayNamesOrEmptyText() {
        AchievementManager manager = new AchievementManager();
        assertEquals("No achievements unlocked", manager.summary());

        manager.unlock(Achievement.FIRST_BOSS_DOWN);

        assertEquals("First Boss Down", manager.summary());
    }
}