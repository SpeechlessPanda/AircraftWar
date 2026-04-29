package edu.hitsz.application;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioPlayerTest {

    @Test
    void waitsUntilEffectPlaybackActuallyStartsAndStops() throws Exception {
        AtomicInteger isRunningCalls = new AtomicInteger();

        Clip clip = (Clip) Proxy.newProxyInstance(
                Clip.class.getClassLoader(),
                new Class<?>[] { Clip.class },
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "isRunning":
                            int call = isRunningCalls.incrementAndGet();
                            if (call == 1) {
                                return false;
                            }
                            if (call <= 3) {
                                return true;
                            }
                            return false;
                        case "start":
                        case "stop":
                        case "close":
                            return null;
                        case "isOpen":
                            return true;
                        default:
                            Class<?> returnType = method.getReturnType();
                            if (returnType == boolean.class) {
                                return false;
                            }
                            if (returnType == int.class || returnType == long.class || returnType == short.class
                                    || returnType == byte.class) {
                                return 0;
                            }
                            if (returnType == float.class || returnType == double.class) {
                                return 0.0;
                            }
                            return null;
                    }
                });

        AudioPlayer.waitForEffectCompletion(clip);

        assertTrue(isRunningCalls.get() >= 4, "effect playback should wait through startup and completion");
    }
}