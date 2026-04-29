package edu.hitsz.bullet;

/**
 * 英雄机子弹
 * 
 * @Author hitsz
 */
public class HeroBullet extends BaseBullet {

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        // 具体阵型和速度由射击策略决定，子弹类只负责承载结果。
        super(locationX, locationY, speedX, speedY, power);
    }

}
