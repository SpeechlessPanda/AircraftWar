package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.StraightShootStrategy;

/**
 * 英雄飞机，游戏玩家操控
 * 
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private static volatile HeroAircraft heroAircraft;

    private static final int INIT_LOCATION_X = Main.WINDOW_WIDTH / 2;
    private static final int INIT_LOCATION_Y = Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight();
    private static final int INIT_SPEED_X = 0;
    private static final int INIT_SPEED_Y = 0;
    private static final int INIT_HP = 100;
    private static final int INIT_SHOOT_NUM = 1;
    private static final int INIT_POWER = 30;
    private static final int INIT_DIRECTION = -1;

    private HeroAircraft() {
        super(INIT_LOCATION_X, INIT_LOCATION_Y, INIT_SPEED_X, INIT_SPEED_Y, INIT_HP,
                INIT_SHOOT_NUM, INIT_POWER, INIT_DIRECTION);
        setShootStrategy(new StraightShootStrategy());
    }

    public static HeroAircraft getInstance() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft();
                }
            }
        }
        return heroAircraft;
    }

    public void resetToDefaultFireMode() {
        // 临时火力结束后统一恢复成默认直射模式。
        setShootStrategy(new StraightShootStrategy());
        setShootNum(INIT_SHOOT_NUM);
    }

    public void resetForNewGame() {
        resetAircraftState(INIT_LOCATION_X, INIT_LOCATION_Y, INIT_SPEED_X, INIT_SPEED_Y, INIT_HP, INIT_SHOOT_NUM,
                INIT_POWER, INIT_DIRECTION, new StraightShootStrategy());
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

}
