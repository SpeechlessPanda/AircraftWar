package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.dao.ScoreRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    @AfterEach
    void restoreHeroState() {
        HeroAircraft.getInstance().resetForNewGame();
    }

    @Test
    void buildDefaultScoreRecordUsesDefaultPlayerName() {
        ScoreRecord scoreRecord = Game.buildDefaultScoreRecord(135);

        assertEquals("Player", scoreRecord.getPlayerName());
        assertEquals(135, scoreRecord.getScore());
        assertNotNull(scoreRecord.getRecordTime());
    }

    @Test
    void supplyDropProbabilityIsRaisedToMakePropsMoreCommon() throws Exception {
        Field field = Game.class.getDeclaredField("SUPPLY_DROP_PROBABILITY");
        field.setAccessible(true);

        assertEquals(0.80, field.getDouble(null), 0.0001);
    }

    @Test
    void creatingNewGameRestoresHeroAfterPreviousGameOver() {
        HeroAircraft hero = HeroAircraft.getInstance();
        hero.decreaseHp(Integer.MAX_VALUE);

        GameFactory.create("easy", null);

        assertEquals(100, hero.getHp());
        assertFalse(hero.notValid());
    }
}