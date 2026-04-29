package edu.hitsz.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropFactoryProbabilityTest {

    @Test
    void dropPoolsKeepFreezeSupplyAtSameWeightAsIncludedProps() {
        assertEquals(1, PropFactory.countDropTypeForTest(PropFactory.PropType.FREEZE, true, false));
        assertEquals(1, PropFactory.countDropTypeForTest(PropFactory.PropType.FREEZE, false, false));
        assertEquals(1, PropFactory.countDropTypeForTest(PropFactory.PropType.FREEZE, false, true));
        assertEquals(4, PropFactory.getBasicEliteDropPoolSizeForTest());
        assertEquals(PropFactory.PropType.values().length, PropFactory.getEliteDropPoolSizeForTest());
        assertEquals(PropFactory.PropType.values().length, PropFactory.getAceDropPoolSizeForTest());
    }
}