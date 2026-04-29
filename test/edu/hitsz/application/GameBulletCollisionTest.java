package edu.hitsz.application;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBulletCollisionTest {

    @Test
    void opposingBulletsCancelEachOtherOnCollision() {
        BaseBullet heroBullet = new HeroBullet(100, 100, 0, -10, 30);
        BaseBullet enemyBullet = new EnemyBullet(100, 100, 0, 10, 20);
        List<BaseBullet> heroBullets = new ArrayList<>();
        List<BaseBullet> enemyBullets = new ArrayList<>();
        heroBullets.add(heroBullet);
        enemyBullets.add(enemyBullet);

        assertTrue(Game.resolveBulletCollisions(heroBullets, enemyBullets));
        assertTrue(heroBullet.notValid());
        assertTrue(enemyBullet.notValid());
    }

    @Test
    void nonCollidingBulletsRemainActive() {
        BaseBullet heroBullet = new HeroBullet(100, 100, 0, -10, 30);
        BaseBullet enemyBullet = new EnemyBullet(250, 250, 0, 10, 20);
        List<BaseBullet> heroBullets = new ArrayList<>();
        List<BaseBullet> enemyBullets = new ArrayList<>();
        heroBullets.add(heroBullet);
        enemyBullets.add(enemyBullet);

        assertFalse(Game.resolveBulletCollisions(heroBullets, enemyBullets));
        assertFalse(heroBullet.notValid());
        assertFalse(enemyBullet.notValid());
    }

    @Test
    void frozenEnemyBulletCanStillBeCancelledByHeroBullet() {
        BaseBullet heroBullet = new HeroBullet(100, 100, 0, -10, 30);
        BaseBullet enemyBullet = new EnemyBullet(100, 100, 0, 10, 20);
        enemyBullet.onFreeze(new edu.hitsz.prop.FreezeEffectContext(80, 80, 80, 80));
        List<BaseBullet> heroBullets = new ArrayList<>();
        List<BaseBullet> enemyBullets = new ArrayList<>();
        heroBullets.add(heroBullet);
        enemyBullets.add(enemyBullet);

        assertTrue(Game.resolveBulletCollisions(heroBullets, enemyBullets));
        assertTrue(heroBullet.notValid());
        assertTrue(enemyBullet.notValid());
    }
}