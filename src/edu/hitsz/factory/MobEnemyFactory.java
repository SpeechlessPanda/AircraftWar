package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

/**
 * 普通敌机工厂。
 */
public class MobEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        // 普通敌机没有额外随机属性，直接按默认模板创建。
        return MobEnemy.createDefault(locationX, locationY);
    }
}
