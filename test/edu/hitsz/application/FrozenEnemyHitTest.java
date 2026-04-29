package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.FreezeEffectContext;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FrozenEnemyHitTest {

    @Test
    void frozenEnemyCanStillBeDestroyedWhenFrozenEnemyBulletOverlapsIt() throws Exception {
        NormalGame game = new NormalGame(null);
        MobEnemy enemy = MobEnemy.createDefault(120, 120);
        enemy.onFreeze(new FreezeEffectContext(20, 20, 20, 20));
        HeroBullet heroBullet = new HeroBullet(120, 120, 0, -10, 40);
        EnemyBullet frozenEnemyBullet = new EnemyBullet(120, 120, 0, 5, 10);
        frozenEnemyBullet.onFreeze(new FreezeEffectContext(20, 20, 20, 20));

        enemiesOf(game).add(enemy);
        heroBulletsOf(game).add(heroBullet);
        enemyBulletsOf(game).add(frozenEnemyBullet);

        Method crashCheckAction = Game.class.getDeclaredMethod("crashCheckAction");
        crashCheckAction.setAccessible(true);
        crashCheckAction.invoke(game);

        assertTrue(enemy.notValid(), "frozen enemy should not be protected by frozen enemy bullets");
    }

    @SuppressWarnings("unchecked")
    private List<AbstractAircraft> enemiesOf(Game game) throws Exception {
        Field field = Game.class.getDeclaredField("enemyAircrafts");
        field.setAccessible(true);
        return (List<AbstractAircraft>) field.get(game);
    }

    @SuppressWarnings("unchecked")
    private List<BaseBullet> heroBulletsOf(Game game) throws Exception {
        Field field = Game.class.getDeclaredField("heroBullets");
        field.setAccessible(true);
        return (List<BaseBullet>) field.get(game);
    }

    @SuppressWarnings("unchecked")
    private List<BaseBullet> enemyBulletsOf(Game game) throws Exception {
        Field field = Game.class.getDeclaredField("enemyBullets");
        field.setAccessible(true);
        return (List<BaseBullet>) field.get(game);
    }
}