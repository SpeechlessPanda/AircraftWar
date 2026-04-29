package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.FreezeEffectContext;
import edu.hitsz.strategy.StraightShootStrategy;

/**
 * 强化精英敌机，带横向漂移并拥有更高的弹量和血量。
 */
public class ElitePlusEnemy extends AbstractAircraft {

    private static final int DEFAULT_SPEED_X = 3;
    private static final int DEFAULT_SPEED_Y = 5;
    private static final int DEFAULT_HP = 55;
    private static final int DEFAULT_SHOOT_NUM = 2;
    private static final int DEFAULT_POWER = 25;
    private static final int DEFAULT_DIRECTION = 1;

    public static ElitePlusEnemy createDefault(int locationX, int locationY) {
        int speedX = Math.random() < 0.5 ? DEFAULT_SPEED_X : -DEFAULT_SPEED_X;
        return new ElitePlusEnemy(locationX, locationY, speedX, DEFAULT_SPEED_Y, DEFAULT_HP);
    }

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        freezeMovementAndShootingTemporarily(context, context.getElitePlusFreezeMillis());
    }

}
