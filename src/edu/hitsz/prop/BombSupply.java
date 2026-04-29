package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AudioManager;

/**
 * 炸弹补给，目前负责触发爆炸音效和效果入口。
 */
public class BombSupply extends AbstractProp {

    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 5;

    public static BombSupply createDefault(int locationX, int locationY) {
        return new BombSupply(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y);
    }

    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        AudioManager.getInstance().playEffect(AudioManager.BOMB_EXPLOSION_PATH);
        System.out.println("BombSupply active!");
    }
}
