package edu.hitsz.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 音频门面，集中管理循环 BGM 和一次性音效的播放。
 */
public class AudioManager {

    public static final String BGM = "bgm";
    public static final String BOSS_BGM = "boss_bgm";
    public static final String BGM_PATH = "src/videos/bgm.wav";
    public static final String BOSS_BGM_PATH = "src/videos/bgm_boss.wav";
    public static final String BULLET_HIT_PATH = "src/videos/bullet_hit.wav";
    public static final String BOMB_EXPLOSION_PATH = "src/videos/bomb_explosion.wav";
    public static final String GET_SUPPLY_PATH = "src/videos/get_supply.wav";
    public static final String GAME_OVER_PATH = "src/videos/game_over.wav";

    private static final AudioManager INSTANCE = new AudioManager();

    private final Map<String, AudioPlayer> loopPlayers = new ConcurrentHashMap<>();

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        return INSTANCE;
    }

    public synchronized void playEffect(String filePath) {
        // 一次性音效互不覆盖，不需要纳入 loopPlayers 管理。
        new AudioPlayer(filePath, false).start();
    }

    public synchronized void playLoop(String key, String filePath) {
        // 同一类循环音频始终只保留一个实例，避免 BGM 叠加。
        stop(key);
        AudioPlayer player = new AudioPlayer(filePath, true);
        loopPlayers.put(key, player);
        player.start();
    }

    public synchronized void stop(String key) {
        AudioPlayer player = loopPlayers.remove(key);
        if (player != null) {
            player.stopPlayback();
        }
    }

    public synchronized void stopAllLoops() {
        for (String key : loopPlayers.keySet()) {
            stop(key);
        }
    }
}