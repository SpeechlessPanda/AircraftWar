package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 环形射击策略，通常给 Boss 或强化火力状态使用。
 */
public class RingShootStrategy implements ShootStrategy {

    private static final double TWO_PI = Math.PI * 2;
    private static final int HERO_BASE_SPEED = 8;
    private static final int ENEMY_BASE_SPEED = 5;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();

        for (int i = 0; i < shootNum; i++) {
            // 把整个圆周按 shootNum 等分，每发子弹占一个固定角度。
            double angle = TWO_PI * i / shootNum;
            if (aircraft instanceof HeroAircraft) {
                // 英雄机环射保留更高基础速度，避免拾取强火力后弹幕显得拖沓。
                int speedX = (int) Math.round(HERO_BASE_SPEED * Math.cos(angle));
                int speedY = (int) Math.round(HERO_BASE_SPEED * Math.sin(angle));
                bullets.add(new HeroBullet(x, y, speedX, speedY, power));
            } else {
                // 敌机环射依然保持较慢基速，避免 Boss 弹幕压迫感过强。
                int speedX = (int) Math.round(ENEMY_BASE_SPEED * Math.cos(angle));
                int speedY = (int) Math.round(ENEMY_BASE_SPEED * Math.sin(angle));
                bullets.add(new EnemyBullet(x, y, speedX, speedY, power));
            }
        }
        return bullets;
    }
}
