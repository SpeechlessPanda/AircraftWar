package edu.hitsz.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 排行榜分数记录 VO。
 */
public class ScoreRecord {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String playerName;
    private final int score;
    private final LocalDateTime recordTime;

    public ScoreRecord(String playerName, int score, LocalDateTime recordTime) {
        this.playerName = Objects.requireNonNull(playerName, "playerName cannot be null");
        this.score = score;
        this.recordTime = Objects.requireNonNull(recordTime, "recordTime cannot be null");
    }

    public static ScoreRecord ofNow(String playerName, int score) {
        return new ScoreRecord(playerName, score, LocalDateTime.now());
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public String getFormattedTime() {
        return recordTime.format(FORMATTER);
    }

    public String toStorageLine() {
        return playerName + "," + score + "," + getFormattedTime();
    }

    public static ScoreRecord fromStorageLine(String line) {
        // 存储格式固定为 玩家名,分数,时间，异常行由 DAO 调用方兜底跳过。
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            return null;
        }
        String playerName = parts[0].trim();
        int score = Integer.parseInt(parts[1].trim());
        LocalDateTime time = LocalDateTime.parse(parts[2].trim(), FORMATTER);
        return new ScoreRecord(playerName, score, time);
    }
}
