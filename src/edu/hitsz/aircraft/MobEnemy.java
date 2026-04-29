package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.strategy.NoShootStrategy;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * 
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 6;
    private static final int DEFAULT_HP = 30;

    public static MobEnemy createDefault(int locationX, int locationY) {
        return new MobEnemy(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y, DEFAULT_HP);
    }

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setShootStrategy(new NoShootStrategy());
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

}
