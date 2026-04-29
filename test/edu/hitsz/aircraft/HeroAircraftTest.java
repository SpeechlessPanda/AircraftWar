package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private static final int HERO_MAX_HP = 100;

    private HeroAircraft hero;

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
        int currentHp = hero.getHp();
        if (currentHp < HERO_MAX_HP) {
            hero.increaseHp(HERO_MAX_HP - currentHp);
        } else if (currentHp > HERO_MAX_HP) {
            hero.decreaseHp(currentHp - HERO_MAX_HP);
        }
        hero.setShootNum(1);
    }

    @AfterEach
    void tearDown() {
        hero.setShootNum(1);
    }

    @Test
    void getHp() {
        assertTrue(hero.getHp() > 0, "hero hp should be positive after setup");
        assertEquals(HERO_MAX_HP, hero.getHp(), "hero hp should reset to max in setup");
    }

    @Test
    void increaseHp() {
        hero.decreaseHp(30);
        assertEquals(70, hero.getHp());

        hero.increaseHp(20);
        assertEquals(90, hero.getHp());

        hero.increaseHp(1000);
        assertEquals(HERO_MAX_HP, hero.getHp(), "hp should not exceed max hp");
    }

    @Test
    void shoot() {
        hero.setShootNum(3);
        List<BaseBullet> bullets = hero.shoot();

        assertEquals(3, bullets.size(), "shoot should create bullets based on shootNum");
        for (BaseBullet bullet : bullets) {
            assertTrue(bullet instanceof HeroBullet, "hero should shoot hero bullets");
            assertEquals(hero.getPower(), bullet.getPower(), "bullet power should match hero power");
            assertTrue(bullet.getSpeedY() < 0, "hero bullets should move upward");
        }
    }
}