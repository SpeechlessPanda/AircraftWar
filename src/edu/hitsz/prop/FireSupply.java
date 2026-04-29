package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.FireModeManager;
import edu.hitsz.strategy.FanShootStrategy;

/**
 * 基础火力补给，暂时切换为扇形射击。
 */
public class FireSupply extends AbstractProp {

    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 5;

    public static FireSupply createDefault(int locationX, int locationY) {
        return new FireSupply(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y);
    }

    public FireSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        // 火力时长和恢复动作由 FireModeManager 统一托管。
        FireModeManager.getDefaultInstance().activateTemporaryMode(heroAircraft, new FanShootStrategy(), 5);
        System.out.println("FireSupply active!");
    }
}
