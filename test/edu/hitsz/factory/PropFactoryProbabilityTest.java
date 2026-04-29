package edu.hitsz.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PropFactoryProbabilityTest {

    @Test
    void eliteAndAceDropPoolsGiveFreezeSupplyHigherWeight() {
        assertTrue(PropFactory.countDropTypeForTest(PropFactory.PropType.FREEZE, true) >= 2);
        assertTrue(PropFactory.countDropTypeForTest(PropFactory.PropType.FREEZE, false) >= 3);
        assertTrue(PropFactory.getEliteDropPoolSizeForTest() > 4);
        assertTrue(PropFactory.getAceDropPoolSizeForTest() > PropFactory.PropType.values().length);
    }
}