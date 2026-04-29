package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.prop.BombEffectContext;
import edu.hitsz.prop.FreezeEffectContext;
import edu.hitsz.strategy.RingShootStrategy;

/**
 * Boss 敌机，横向往返移动并使用环形射击。
 */
public class BossEnemy extends AbstractAircraft {

    private static final int DEFAULT_SPEED_X = 3;
    private static final int DEFAULT_SPEED_Y = 0;
    private static final int DEFAULT_HP = 300;
    private static final int DEFAULT_SHOOT_NUM = 20;
    private static final int DEFAULT_POWER = 25;
    private static final int DEFAULT_DIRECTION = 1;

    public static BossEnemy createDefault(int locationX, int locationY) {
        int speedX = Math.random() < 0.5 ? DEFAULT_SPEED_X : -DEFAULT_SPEED_X;
        return new BossEnemy(locationX, locationY, speedX, DEFAULT_SPEED_Y, DEFAULT_HP);
    }

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, DEFAULT_SHOOT_NUM, DEFAULT_POWER, DEFAULT_DIRECTION);
        setShootStrategy(new RingShootStrategy());
    }

    @Override
    public void forward() {
        locationX += speedX;
        int halfWidth = ImageManager.BOSS_ENEMY_IMAGE.getWidth() / 2;
        if (locationX <= halfWidth || locationX >= Main.WINDOW_WIDTH - halfWidth) {
            // Boss 只在屏幕上方横向巡航，触边后立即反向。
            speedX = -speedX;
            locationX += speedX;
        }
        if (locationY < ImageManager.BOSS_ENEMY_IMAGE.getHeight() / 2) {
            locationY = ImageManager.BOSS_ENEMY_IMAGE.getHeight() / 2;
        }
    }

    @Override
    public void onBomb(BombEffectContext context) {
    }

    @Override
    public void onFreeze(FreezeEffectContext context) {
    }
}
