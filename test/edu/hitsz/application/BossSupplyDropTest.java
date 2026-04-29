package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.prop.AbstractProp;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BossSupplyDropTest {

    @Test
    void bossSuppliesAreSpreadAroundBossPosition() throws Exception {
        HardGame game = new HardGame(null);
        BossEnemy boss = BossEnemy.createDefault(240, 80);
        Method createSupplyAfterEnemyCrash = Game.class.getDeclaredMethod("createSupplyAfterEnemyCrash",
                edu.hitsz.aircraft.AbstractAircraft.class);
        createSupplyAfterEnemyCrash.setAccessible(true);

        createSupplyAfterEnemyCrash.invoke(game, boss);

        Set<String> locations = propsOf(game).stream()
                .map(prop -> prop.getLocationX() + ":" + prop.getLocationY())
                .collect(Collectors.toSet());
        assertTrue(locations.size() > 1, "Boss drops should not all overlap at the same location");
    }

    @SuppressWarnings("unchecked")
    private List<AbstractProp> propsOf(Game game) throws Exception {
        Field field = Game.class.getDeclaredField("props");
        field.setAccessible(true);
        return (List<AbstractProp>) field.get(game);
    }
}