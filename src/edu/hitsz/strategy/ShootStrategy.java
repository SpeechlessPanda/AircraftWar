package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 射击策略接口，把不同机体的弹幕生成逻辑从飞机类中拆分出去。
 */
public interface ShootStrategy {
    List<BaseBullet> shoot(AbstractAircraft aircraft);
}
