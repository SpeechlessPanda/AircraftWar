package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.ElitePlusEnemy;

/**
 * 强化精英敌机工厂。
 */
public class ElitePlusEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        // 这一类会在敌机类内部随机左右漂移方向，工厂不重复决定。
        return ElitePlusEnemy.createDefault(locationX, locationY);
    }
}
