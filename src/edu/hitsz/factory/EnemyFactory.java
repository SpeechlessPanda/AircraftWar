package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 敌机工厂接口，统一不同敌机的创建入口。
 */
public interface EnemyFactory {
    // 生成位置由 Game 决定，工厂只负责返回对应类型的默认敌机实例。
    AbstractAircraft createEnemy(int locationX, int locationY);
}
