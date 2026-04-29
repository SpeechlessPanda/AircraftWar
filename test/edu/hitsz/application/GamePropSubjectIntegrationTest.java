package edu.hitsz.application;

import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.BombEffectContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamePropSubjectIntegrationTest {

    @Test
    void bombNotificationAffectsCurrentEnemiesAndEnemyBullets() {
        MobEnemy mob = MobEnemy.createDefault(20, 20);
        EnemyBullet bullet = new EnemyBullet(20, 20, 0, 5, 10);

        BombEffectContext context = Game.triggerBombEffectForObservers(List.of(mob), List.of(bullet));

        assertTrue(mob.notValid());
        assertTrue(bullet.notValid());
        assertEquals(10, context.getScoreBonus());
    }
}