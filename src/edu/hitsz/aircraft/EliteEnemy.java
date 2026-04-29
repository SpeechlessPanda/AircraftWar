package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.FreezeEffectContext;
import edu.hitsz.strategy.StraightShootStrategy;

/**
 * 基础精英敌机，直线下落并使用单发直射。
 */
public class EliteEnemy extends AbstractAircraft {

    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 5;
    private static final int DEFAULT_HP = 40;
    private static final int DEFAULT_SHOOT_NUM = 1;
    private static final int DEFAULT_POWER = 20;
    private static final int DEFAULT_DIRECTION = 1;

    public static EliteEnemy createDefault(int locationX, int locationY) {
        return new EliteEnemy(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y, DEFAULT_HP);
    }

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, DEFAULT_SHOOT_NUM, DEFAULT_POWER, DEFAULT_DIRECTION);
        setShootStrategy(new StraightShootStrategy());
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public void onFreeze(FreezeEffectContext context) {
        freezeMovementAndShootingTemporarily(context, context.getEliteFreezeMillis());
    }

}
