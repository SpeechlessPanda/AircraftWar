package edu.hitsz.application;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * 单个音频文件的播放线程，支持一次性音效和循环背景音乐两种模式。
 */
public class AudioPlayer extends Thread {

    private final String filePath;
    private final boolean loop;
    private volatile boolean running = true;
    private Clip clip;

    public AudioPlayer(String filePath, boolean loop) {
        super("audio-player-" + new File(filePath).getName());
        this.filePath = filePath;
        this.loop = loop;
        setDaemon(true);
    }

    @Override
    public void run() {
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath))) {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                while (running) {
                    Thread.sleep(50);
                }
            } else {
                clip.start();
                // 某些环境里 clip.start() 后不会立刻进入 running，需等待实际播放完成。
                waitForEffectCompletion(clip);
            }
        } catch (Exception ignored) {
            // Audio playback should not crash the game loop.
        } finally {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        }
    }

    public void stopPlayback() {
        running = false;
        interrupt();
    }

    static void waitForEffectCompletion(Clip clip) throws InterruptedException {
        boolean playbackStarted = false;
        while (true) {
            boolean clipRunning = clip.isRunning();
            if (clipRunning) {
                playbackStarted = true;
            } else if (playbackStarted) {
                return;
            }
            Thread.sleep(20);
        }
    }
}