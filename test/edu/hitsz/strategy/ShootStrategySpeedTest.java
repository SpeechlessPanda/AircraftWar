package edu.hitsz.strategy;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShootStrategySpeedTest {

    private HeroAircraft hero;
    private EliteEnemy eliteEnemy;

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
        hero.resetToDefaultFireMode();
        hero.setShootNum(1);
        eliteEnemy = EliteEnemy.createDefault(200, 100);
    }

    @Test
    void straightShootMakesHeroBulletsFasterThanEnemyBullets() {
        BaseBullet heroBullet = hero.shoot().get(0);
        BaseBullet enemyBullet = eliteEnemy.shoot().get(0);

        assertTrue(Math.abs(heroBullet.getSpeedY()) > Math.abs(enemyBullet.getSpeedY()));
    }

    @Test
    void fanShootMakesHeroBulletsFasterThanEnemyBullets() {
        hero.setShootStrategy(new FanShootStrategy());
        hero.setShootNum(5);
        eliteEnemy.setShootStrategy(new FanShootStrategy());
        eliteEnemy.setShootNum(5);

        List<BaseBullet> heroBullets = hero.shoot();
        List<BaseBullet> enemyBullets = eliteEnemy.shoot();

        assertTrue(Math.abs(heroBullets.get(0).getSpeedY()) > Math.abs(enemyBullets.get(0).getSpeedY()));
    }
}