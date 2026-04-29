package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 加血补给，直接恢复英雄机生命值。
 */
public class BloodSupply extends AbstractProp {

    private static final int HEALING_VALUE = 40;
    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 5;

    public static BloodSupply createDefault(int locationX, int locationY) {
        return new BloodSupply(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y);
    }

    public BloodSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        // increaseHp 内部会自动处理不超过最大生命值的上限。
        heroAircraft.increaseHp(HEALING_VALUE);
    }
}
