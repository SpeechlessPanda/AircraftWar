package edu.hitsz.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaderboardAchievementDisplayTest {

    @Test
    void formatsCurrentRunAchievementSummaryForLeaderboard() {
        assertEquals("Achievements: Score 1000, Bomb Sweep",
                LeaderboardPanel.formatAchievementSummaryForDisplay("Score 1000, Bomb Sweep"));
        assertEquals("Achievements: No achievements unlocked",
                LeaderboardPanel.formatAchievementSummaryForDisplay(""));
    }
}