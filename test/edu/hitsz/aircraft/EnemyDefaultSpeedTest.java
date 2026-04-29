package edu.hitsz.aircraft;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyDefaultSpeedTest {

    @Test
    void defaultEnemySpeedsAreReducedButDifferencesRemain() throws Exception {
        assertEquals(6, readIntConstant(MobEnemy.class, "DEFAULT_SPEED_Y"));
        assertEquals(5, readIntConstant(EliteEnemy.class, "DEFAULT_SPEED_Y"));
        assertEquals(5, readIntConstant(ElitePlusEnemy.class, "DEFAULT_SPEED_Y"));
        assertEquals(5, readIntConstant(EliteProEnemy.class, "DEFAULT_SPEED_Y"));
        assertEquals(3, readIntConstant(ElitePlusEnemy.class, "DEFAULT_SPEED_X"));
        assertEquals(2, readIntConstant(EliteProEnemy.class, "DEFAULT_SPEED_X"));
        assertEquals(3, readIntConstant(BossEnemy.class, "DEFAULT_SPEED_X"));

        assertTrue(readIntConstant(MobEnemy.class, "DEFAULT_SPEED_Y") > readIntConstant(EliteEnemy.class,
                "DEFAULT_SPEED_Y"));
        assertTrue(readIntConstant(EliteProEnemy.class, "DEFAULT_SHOOT_NUM") > readIntConstant(EliteEnemy.class,
                "DEFAULT_SHOOT_NUM"));
    }

    private int readIntConstant(Class<?> type, String fieldName) throws Exception {
        Field field = type.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getInt(null);
    }
}