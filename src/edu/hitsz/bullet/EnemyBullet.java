package edu.hitsz.bullet;

/**
 * 敌机子弹
 * 
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        // 敌机子弹与英雄机子弹共享同一套运动/碰撞逻辑，只区分来源与图片。
        super(locationX, locationY, speedX, speedY, power);
    }

}
