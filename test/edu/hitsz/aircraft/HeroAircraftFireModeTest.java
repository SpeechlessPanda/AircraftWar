package edu.hitsz.aircraft;

import edu.hitsz.application.FireModeManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.FanShootStrategy;
import edu.hitsz.strategy.RingShootStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeroAircraftFireModeTest {

    private HeroAircraft hero;
    private FireModeManager fireModeManager;

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
        hero.resetToDefaultFireMode();
        fireModeManager = new FireModeManager(80);
    }

    @AfterEach
    void tearDown() {
        fireModeManager.shutdown();
        hero.resetToDefaultFireMode();
    }

    @Test
    void temporaryFireModeResetsToDefaultAfterDuration() throws InterruptedException {
        fireModeManager.activateTemporaryMode(hero, new FanShootStrategy(), 5);
        assertEquals(5, hero.getShootNum());

        Thread.sleep(140);

        assertEquals(1, hero.getShootNum());
        assertShootsSingleStraightBullet(hero.shoot());
    }

    @Test
    void repeatedActivationRefreshesResetCountdown() throws InterruptedException {
        fireModeManager.activateTemporaryMode(hero, new FanShootStrategy(), 5);
        Thread.sleep(40);

        fireModeManager.activateTemporaryMode(hero, new RingShootStrategy(), 12);
        Thread.sleep(55);

        assertEquals(12, hero.getShootNum(), "latest activation should still be active before refreshed timeout");

        Thread.sleep(70);

        assertEquals(1, hero.getShootNum());
        assertShootsSingleStraightBullet(hero.shoot());
    }

    private void assertShootsSingleStraightBullet(List<BaseBullet> bullets) {
        assertEquals(1, bullets.size(), "default fire mode should shoot one bullet");
        BaseBullet bullet = bullets.get(0);
        int xBefore = bullet.getLocationX();
        int yBefore = bullet.getLocationY();
        bullet.forward();
        assertEquals(xBefore, bullet.getLocationX(), "straight bullet should not drift horizontally");
        assertTrue(bullet.getLocationY() < yBefore, "straight bullet should move upward");
    }
}