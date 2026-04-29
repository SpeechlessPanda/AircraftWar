package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 扇形射击策略，通过 speedX 递增制造散射效果。
 */
public class FanShootStrategy implements ShootStrategy {

    private static final int SPEED_X_STEP = 2;
    private static final int HERO_SPEED_Y_OFFSET = -14;
    private static final int ENEMY_SPEED_Y_OFFSET = 2;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        int x = aircraft.getLocationX();
        // 让子弹从机体前方一点的位置出发，避免视觉上像是从机身中心冒出来。
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();

        for (int i = 0; i < shootNum; i++) {
            // 以中间子弹为对称轴，左右逐步拉开横向速度，形成扇面。
            int speedX = (i - shootNum / 2) * SPEED_X_STEP;
            if (aircraft instanceof HeroAircraft) {
                // 英雄机扇射继续保留较快弹速，避免强化火力后反而显慢。
                int speedY = aircraft.getSpeedY() + HERO_SPEED_Y_OFFSET;
                bullets.add(new HeroBullet(x, y, speedX, speedY, power));
            } else {
                // 敌机扇射只保留较缓的推进速度，压低整体难度。
                int speedY = aircraft.getSpeedY() + ENEMY_SPEED_Y_OFFSET;
                bullets.add(new EnemyBullet(x, y, speedX, speedY, power));
            }
        }
        return bullets;
    }
}
