package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;

/**
 * 基础精英敌机工厂。
 */
public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        // 精英敌机的血量、火力等差异仍封装在具体敌机类内部。
        return EliteEnemy.createDefault(locationX, locationY);
    }
}
