package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.BombEffectContext;
import edu.hitsz.prop.FreezeEffectContext;
import edu.hitsz.strategy.FanShootStrategy;

/**
 * 王牌精英敌机，使用扇形弹幕并保留一定横向机动。
 */
public class EliteProEnemy extends AbstractAircraft {

    private static final int BOMB_DAMAGE = 30;

    private static final int DEFAULT_SPEED_X = 2;
    private static final int DEFAULT_SPEED_Y = 5;
    private static final int DEFAULT_HP = 70;

    private static final int DEFAULT_SHOOT_NUM = 5;
    private static final int DEFAULT_POWER = 30;
    private static final int DEFAULT_DIRECTION = 1;

    public static EliteProEnemy createDefault(int locationX, int locationY) {
        int speedX = Math.random() < 0.5 ? DEFAULT_SPEED_X : -DEFAULT_SPEED_X;
        return new EliteProEnemy(locationX, locationY, speedX, DEFAULT_SPEED_Y, DEFAULT_HP);
    }

    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, DEFAULT_SHOOT_NUM, DEFAULT_POWER, DEFAULT_DIRECTION);
        setShootStrategy(new FanShootStrategy());
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public void onBomb(BombEffectContext context) {
        boolean wasAlive = !notValid();
        decreaseHp(BOMB_DAMAGE);
        if (wasAlive && notValid()) {
            context.addScoreBonus(BOMB_SCORE_BONUS);
        }
    }

    @Override
    public void onFreeze(FreezeEffectContext context) {
        context.slowTemporarily(this, 0.5, context.getEliteProSlowMillis());
    }

}
