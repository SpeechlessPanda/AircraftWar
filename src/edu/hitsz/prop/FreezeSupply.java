package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 冰冻补给，占位实现，当前只保留激活入口。
 */
public class FreezeSupply extends AbstractProp {

    private static final int DEFAULT_SPEED_X = 0;
    private static final int DEFAULT_SPEED_Y = 5;

    public static FreezeSupply createDefault(int locationX, int locationY) {
        return new FreezeSupply(locationX, locationY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y);
    }

    public FreezeSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        // 当前实验阶段只保留接口占位，后续若接入减速效果可从这里扩展。
        System.out.println("FreezeSupply active!");
    }
}
