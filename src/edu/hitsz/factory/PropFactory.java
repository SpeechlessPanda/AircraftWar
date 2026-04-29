package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupply;
import edu.hitsz.prop.BombSupply;
import edu.hitsz.prop.FirePlusSupply;
import edu.hitsz.prop.FireSupply;
import edu.hitsz.prop.FreezeSupply;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 补给工厂，集中维护不同敌机可掉落的补给集合。
 */
public class PropFactory {

    public enum PropType {
        BLOOD,
        FIRE,
        FIRE_PLUS,
        BOMB,
        FREEZE
    }

    private static final PropType[] BASIC_ELITE_DROP_TYPES = {
            PropType.BLOOD,
            PropType.FIRE,
            PropType.BOMB,
            PropType.FREEZE
    };

    private static final PropType[] ELITE_DROP_TYPES = {
            PropType.BLOOD,
            PropType.FIRE,
            PropType.FIRE_PLUS,
            PropType.BOMB,
            PropType.FREEZE
    };

    private static final PropType[] ACE_DROP_TYPES = {
            PropType.BLOOD,
            PropType.FIRE,
            PropType.FIRE_PLUS,
            PropType.BOMB,
            PropType.FREEZE
    };

    private PropFactory() {
    }

    public static AbstractProp createProp(PropType propType, int locationX, int locationY) {
        switch (propType) {
            case BLOOD:
                return BloodSupply.createDefault(locationX, locationY);
            case FIRE:
                return FireSupply.createDefault(locationX, locationY);
            case FIRE_PLUS:
                return FirePlusSupply.createDefault(locationX, locationY);
            case BOMB:
                return BombSupply.createDefault(locationX, locationY);
            case FREEZE:
                return FreezeSupply.createDefault(locationX, locationY);
            default:
                throw new IllegalArgumentException("Unknown prop type: " + propType);
        }
    }

    public static AbstractProp createRandomForBasicElite(int locationX, int locationY) {
        int index = ThreadLocalRandom.current().nextInt(BASIC_ELITE_DROP_TYPES.length);
        return createProp(BASIC_ELITE_DROP_TYPES[index], locationX, locationY);
    }

    public static AbstractProp createRandomForElite(int locationX, int locationY) {
        int index = ThreadLocalRandom.current().nextInt(ELITE_DROP_TYPES.length);
        return createProp(ELITE_DROP_TYPES[index], locationX, locationY);
    }

    public static AbstractProp createRandomForAce(int locationX, int locationY) {
        // 王牌敌机和 Boss 使用更完整的掉落池。
        int index = ThreadLocalRandom.current().nextInt(ACE_DROP_TYPES.length);
        return createProp(ACE_DROP_TYPES[index], locationX, locationY);
    }

    static long countDropTypeForTest(PropType propType, boolean basicElitePool, boolean acePool) {
        PropType[] pool = dropPoolForTest(basicElitePool, acePool);
        long count = 0;
        for (PropType type : pool) {
            if (type == propType) {
                count++;
            }
        }
        return count;
    }

    private static PropType[] dropPoolForTest(boolean basicElitePool, boolean acePool) {
        if (basicElitePool) {
            return BASIC_ELITE_DROP_TYPES;
        }
        return acePool ? ACE_DROP_TYPES : ELITE_DROP_TYPES;
    }

    static int getBasicEliteDropPoolSizeForTest() {
        return BASIC_ELITE_DROP_TYPES.length;
    }

    static int getEliteDropPoolSizeForTest() {
        return ELITE_DROP_TYPES.length;
    }

    static int getAceDropPoolSizeForTest() {
        return ACE_DROP_TYPES.length;
    }
}
