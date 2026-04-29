package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteProEnemy;

/**
 * 王牌精英敌机工厂。
 */
public class EliteProEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        // 王牌精英的扇形射击配置由具体类默认值提供。
        return EliteProEnemy.createDefault(locationX, locationY);
    }
}
