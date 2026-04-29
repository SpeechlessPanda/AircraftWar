package edu.hitsz.prop;

import edu.hitsz.basic.AbstractFlyingObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.WeakHashMap;

public class FreezeEffectContext {

    public static final long DEFAULT_ELITE_FREEZE_MILLIS = 4000;
    public static final long DEFAULT_ELITE_PLUS_FREEZE_MILLIS = 3000;
    public static final long DEFAULT_ELITE_PRO_SLOW_MILLIS = 5000;
    public static final long DEFAULT_BULLET_FREEZE_MILLIS = 5000;

    private final long eliteFreezeMillis;
    private final long elitePlusFreezeMillis;
    private final long eliteProSlowMillis;
    private final long bulletFreezeMillis;
    private final ScheduledExecutorService scheduler;
    private int affectedTargetCount;
    private static final Map<AbstractFlyingObject, RestoreState> RESTORE_STATES = new WeakHashMap<>();

    public FreezeEffectContext() {
        this(DEFAULT_ELITE_FREEZE_MILLIS, DEFAULT_ELITE_PLUS_FREEZE_MILLIS,
                DEFAULT_ELITE_PRO_SLOW_MILLIS, DEFAULT_BULLET_FREEZE_MILLIS);
    }

    public FreezeEffectContext(long eliteFreezeMillis, long elitePlusFreezeMillis,
            long eliteProSlowMillis, long bulletFreezeMillis) {
        this.eliteFreezeMillis = eliteFreezeMillis;
        this.elitePlusFreezeMillis = elitePlusFreezeMillis;
        this.eliteProSlowMillis = eliteProSlowMillis;
        this.bulletFreezeMillis = bulletFreezeMillis;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
    }

    public long getEliteFreezeMillis() {
        return eliteFreezeMillis;
    }

    public long getElitePlusFreezeMillis() {
        return elitePlusFreezeMillis;
    }

    public long getEliteProSlowMillis() {
        return eliteProSlowMillis;
    }

    public long getBulletFreezeMillis() {
        return bulletFreezeMillis;
    }

    public void freezeTemporarily(AbstractFlyingObject object, long durationMillis) {
        freezeTemporarily(object, durationMillis, null);
    }

    public void freezeTemporarily(AbstractFlyingObject object, long durationMillis, Runnable afterRestore) {
        recordAffectedTarget();
        RestoreState state = beginTemporarySpeedEffect(object);
        object.setSpeed(0, 0);
        restoreSpeedLater(object, state, durationMillis, afterRestore);
    }

    public void slowTemporarily(AbstractFlyingObject object, double factor, long durationMillis) {
        recordAffectedTarget();
        if (object.isTemporarySpeedLocked()) {
            return;
        }
        RestoreState state = beginTemporarySpeedEffect(object);
        object.setSpeed((int) Math.round(state.originalSpeedX * factor),
                Math.max(1, (int) Math.round(state.originalSpeedY * factor)));
        restoreSpeedLater(object, state, durationMillis, null);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    public void recordAffectedTarget() {
        affectedTargetCount++;
    }

    public int getAffectedTargetCount() {
        return affectedTargetCount;
    }

    private RestoreState beginTemporarySpeedEffect(AbstractFlyingObject object) {
        synchronized (RESTORE_STATES) {
            RestoreState state = RESTORE_STATES.get(object);
            if (state == null) {
                state = new RestoreState(object.getSpeedX(), object.getSpeedY());
                RESTORE_STATES.put(object, state);
            }
            state.generation++;
            return state.copyForSchedule();
        }
    }

    private void restoreSpeedLater(AbstractFlyingObject object, RestoreState scheduledState, long durationMillis,
            Runnable afterRestore) {
        scheduler.schedule(() -> {
            boolean restored = false;
            synchronized (RESTORE_STATES) {
                RestoreState currentState = RESTORE_STATES.get(object);
                if (currentState != null && currentState.generation == scheduledState.generation) {
                    object.setSpeed(scheduledState.originalSpeedX, scheduledState.originalSpeedY);
                    RESTORE_STATES.remove(object);
                    restored = true;
                }
            }
            if (restored && afterRestore != null) {
                afterRestore.run();
            }
        }, durationMillis, TimeUnit.MILLISECONDS);
    }

    private static class RestoreState {

        private final int originalSpeedX;
        private final int originalSpeedY;
        private int generation;

        RestoreState(int originalSpeedX, int originalSpeedY) {
            this.originalSpeedX = originalSpeedX;
            this.originalSpeedY = originalSpeedY;
        }

        RestoreState copyForSchedule() {
            RestoreState copy = new RestoreState(originalSpeedX, originalSpeedY);
            copy.generation = generation;
            return copy;
        }
    }

    private static class DaemonThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "freeze-effect-reset");
            thread.setDaemon(true);
            return thread;
        }
    }
}