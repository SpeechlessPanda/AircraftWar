package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.ShootStrategy;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 统一管理临时火力道具的生效时长，并在到时后恢复默认火力。
 */
public class FireModeManager {

    private static final long DEFAULT_DURATION_MILLIS = 10_000L;
    private static final FireModeManager DEFAULT_INSTANCE = new FireModeManager(DEFAULT_DURATION_MILLIS);

    private final ScheduledExecutorService scheduler;
    private final long durationMillis;
    private ScheduledFuture<?> pendingResetTask;

    public FireModeManager(long durationMillis) {
        this.durationMillis = durationMillis;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new FireModeThreadFactory());
    }

    public static FireModeManager getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public synchronized void activateTemporaryMode(HeroAircraft heroAircraft, ShootStrategy shootStrategy,
            int shootNum) {
        // 重复拾取同类道具时刷新倒计时，而不是并发安排多个恢复任务。
        heroAircraft.setShootStrategy(shootStrategy);
        heroAircraft.setShootNum(shootNum);

        if (pendingResetTask != null) {
            pendingResetTask.cancel(false);
        }

        pendingResetTask = scheduler.schedule(heroAircraft::resetToDefaultFireMode, durationMillis,
                TimeUnit.MILLISECONDS);
    }

    public synchronized void shutdown() {
        if (pendingResetTask != null) {
            pendingResetTask.cancel(false);
            pendingResetTask = null;
        }
        scheduler.shutdownNow();
    }

    private static class FireModeThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "fire-mode-reset-thread");
            // 该线程只负责定时恢复火力，不应阻止程序退出。
            thread.setDaemon(true);
            return thread;
        }
    }
}