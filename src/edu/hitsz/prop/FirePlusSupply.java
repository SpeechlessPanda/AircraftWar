package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.FireModeManager;
import edu.hitsz.strategy.RingShootStrategy;

/**
 * 强化火力补给，暂时切换为环形射击。
 */
public class FirePlusSupply extends AbstractProp {

    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 5;

    public static FirePlusSupply createDefault(int locationX, int locationY) {
        return new FirePlusSupply(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y);
    }

    public FirePlusSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        FireModeManager.getDefaultInstance().activateTemporaryMode(heroAircraft, new RingShootStrategy(), 12);
        System.out.println("FirePlusSupply active!");
    }
}
