package edu.hitsz.prop;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropObserverEffectTest {

    @Test
    void bombDestroysNonBossEnemiesAndEnemyBulletsButOnlyDamagesAceEnemy() {
        MobEnemy mob = MobEnemy.createDefault(20, 20);
        EliteEnemy elite = EliteEnemy.createDefault(30, 20);
        ElitePlusEnemy elitePlus = ElitePlusEnemy.createDefault(40, 20);
        EliteProEnemy elitePro = EliteProEnemy.createDefault(50, 20);
        BossEnemy boss = BossEnemy.createDefault(60, 20);
        EnemyBullet bullet = new EnemyBullet(70, 20, 0, 5, 10);
        int eliteProHpBefore = elitePro.getHp();
        int bossHpBefore = boss.getHp();

        BombSubject subject = new BombSubject();
        subject.addObserver(mob);
        subject.addObserver(elite);
        subject.addObserver(elitePlus);
        subject.addObserver(elitePro);
        subject.addObserver(boss);
        subject.addObserver(bullet);

        BombEffectContext context = subject.trigger();

        assertTrue(mob.notValid());
        assertTrue(elite.notValid());
        assertTrue(elitePlus.notValid());
        assertFalse(elitePro.notValid());
        assertTrue(elitePro.getHp() < eliteProHpBefore);
        assertEquals(bossHpBefore, boss.getHp());
        assertFalse(boss.notValid());
        assertTrue(bullet.notValid());
        assertEquals(30, context.getScoreBonus());
    }

    @Test
    void freezeStopsOrSlowsObserversAndRestoresTimedTargets() throws InterruptedException {
        EliteEnemy elite = EliteEnemy.createDefault(30, 20);
        ElitePlusEnemy elitePlus = ElitePlusEnemy.createDefault(40, 20);
        EliteProEnemy elitePro = EliteProEnemy.createDefault(50, 20);
        EnemyBullet bullet = new EnemyBullet(70, 20, 0, 5, 10);

        FreezeEffectContext context = new FreezeEffectContext(20, 30, 40, 30);
        FreezeSubject subject = new FreezeSubject(context);
        subject.addObserver(elite);
        subject.addObserver(elitePlus);
        subject.addObserver(elitePro);
        subject.addObserver(bullet);
        subject.trigger();

        assertEquals(0, elite.getSpeedY());
        assertEquals(0, elitePlus.getSpeedY());
        assertTrue(elitePro.getSpeedY() > 0 && elitePro.getSpeedY() < 5);
        assertEquals(0, bullet.getSpeedY());

        Thread.sleep(90);

        assertEquals(5, elite.getSpeedY());
        assertEquals(5, elitePlus.getSpeedY());
        assertEquals(5, elitePro.getSpeedY());
        assertEquals(5, bullet.getSpeedY());
        context.shutdown();
    }

    @Test
    void repeatedFreezeRestoresOriginalSpeedInsteadOfFrozenSpeed() throws InterruptedException {
        EliteEnemy elite = EliteEnemy.createDefault(30, 20);
        EnemyBullet bullet = new EnemyBullet(70, 20, 0, 5, 10);
        FreezeEffectContext firstContext = new FreezeEffectContext(60, 60, 60, 60);
        FreezeEffectContext secondContext = new FreezeEffectContext(120, 120, 120, 120);

        elite.onFreeze(firstContext);
        bullet.onFreeze(firstContext);
        Thread.sleep(10);
        elite.onFreeze(secondContext);
        bullet.onFreeze(secondContext);

        Thread.sleep(170);

        assertEquals(5, elite.getSpeedY());
        assertEquals(5, bullet.getSpeedY());
        firstContext.shutdown();
        secondContext.shutdown();
    }

    @Test
    void frozenTimedEnemyDoesNotShootUntilFreezeRestores() throws InterruptedException {
        EliteEnemy elite = EliteEnemy.createDefault(30, 20);
        FreezeEffectContext context = new FreezeEffectContext(80, 80, 80, 80);

        assertFalse(elite.shoot().isEmpty());

        elite.onFreeze(context);

        assertTrue(elite.shoot().isEmpty());

        Thread.sleep(120);

        assertFalse(elite.shoot().isEmpty());
        context.shutdown();
    }

    @Test
    void frozenEnemyBulletStaysStoppedAndRestoresOriginalSpeed() throws InterruptedException {
        EnemyBullet bullet = new EnemyBullet(70, 20, 0, 6, 10);
        FreezeEffectContext context = new FreezeEffectContext(20, 20, 20, 80);

        bullet.onFreeze(context);
        context.slowTemporarily(bullet, 0.5, 20);

        assertEquals(0, bullet.getSpeedY());
        assertTrue(bullet.isFrozenBySupply());

        Thread.sleep(120);

        assertEquals(6, bullet.getSpeedY());
        assertFalse(bullet.isFrozenBySupply());
        context.shutdown();
    }

    @Test
    void frozenEnemyBulletRestoresBothHorizontalAndVerticalSpeed() throws InterruptedException {
        EnemyBullet bullet = new EnemyBullet(70, 20, -4, 5, 10);
        FreezeEffectContext context = new FreezeEffectContext(20, 20, 20, 80);

        bullet.onFreeze(context);

        assertEquals(0, bullet.getSpeedX());
        assertEquals(0, bullet.getSpeedY());

        Thread.sleep(120);

        assertEquals(-4, bullet.getSpeedX());
        assertEquals(5, bullet.getSpeedY());
        assertFalse(bullet.isFrozenBySupply());
        context.shutdown();
    }

    @Test
    void freezeSubjectOnlyAffectsBulletsRegisteredAtTriggerTime() {
        EnemyBullet existingBullet = new EnemyBullet(70, 20, 0, 6, 10);
        EnemyBullet laterBullet = new EnemyBullet(90, 20, 0, 6, 10);
        FreezeEffectContext context = new FreezeEffectContext(20, 20, 20, 80);
        FreezeSubject subject = new FreezeSubject(context);
        subject.addObserver(existingBullet);

        subject.trigger();

        assertEquals(0, existingBullet.getSpeedY());
        assertTrue(existingBullet.isFrozenBySupply());
        assertEquals(6, laterBullet.getSpeedY());
        assertFalse(laterBullet.isFrozenBySupply());
        context.shutdown();
    }
}