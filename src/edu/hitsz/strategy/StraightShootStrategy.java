package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 直射策略，可按 shootNum 生成并排的直线子弹。
 */
public class StraightShootStrategy implements ShootStrategy {

    private static final int HERO_SPEED_Y_OFFSET = -14;
    private static final int ENEMY_SPEED_Y_OFFSET = 2;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();

        for (int i = 0; i < shootNum; i++) {
            int speedX = 0;
            int bulletX = x + (i * 2 - shootNum + 1) * 10;
            if (aircraft instanceof HeroAircraft) {
                // 英雄机子弹额外给更大的向上速度，让手感更快。
                int speedY = aircraft.getSpeedY() + HERO_SPEED_Y_OFFSET;
                bullets.add(new HeroBullet(bulletX, y, speedX, speedY, power));
            } else {
                // 敌机保留较慢的子弹速度，和英雄机区分节奏。
                int speedY = aircraft.getSpeedY() + ENEMY_SPEED_Y_OFFSET;
                bullets.add(new EnemyBullet(bulletX, y, speedX, speedY, power));
            }
        }
        return bullets;
    }
}
