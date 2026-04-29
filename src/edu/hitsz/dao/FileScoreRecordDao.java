package edu.hitsz.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 基于文本文件的排行榜 DAO 实现。
 */
public class FileScoreRecordDao implements ScoreRecordDao {

    private static final String STORAGE_DIR = "scores";

    private final Path storagePath;

    public FileScoreRecordDao(String difficulty) {
        String safeDifficulty = normalizeDifficulty(difficulty);
        this.storagePath = Paths.get(STORAGE_DIR, "records-" + safeDifficulty + ".txt");
    }

    public FileScoreRecordDao(Path storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public synchronized List<ScoreRecord> getAll() {
        ensureStorageReady();
        try {
            List<String> lines = Files.readAllLines(storagePath, StandardCharsets.UTF_8);
            List<ScoreRecord> records = new ArrayList<>();
            for (String line : lines) {
                try {
                    ScoreRecord scoreRecord = ScoreRecord.fromStorageLine(line);
                    if (scoreRecord != null) {
                        records.add(scoreRecord);
                    }
                } catch (RuntimeException ignored) {
                    // Skip malformed line to keep leaderboard readable.
                }
            }
            return records;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read score records from " + storagePath, e);
        }
    }

    @Override
    public synchronized List<ScoreRecord> getAllSorted() {
        List<ScoreRecord> records = new ArrayList<>(getAll());
        // 分数降序，分数相同时按记录时间升序，保证排行榜稳定可预期。
        records.sort(
                Comparator.comparingInt(ScoreRecord::getScore).reversed().thenComparing(ScoreRecord::getRecordTime));
        return records;
    }

    @Override
    public synchronized void add(ScoreRecord scoreRecord) {
        List<ScoreRecord> records = new ArrayList<>(getAll());
        records.add(scoreRecord);
        writeAll(records);
    }

    @Override
    public synchronized boolean deleteByRank(int rank) {
        if (rank <= 0) {
            return false;
        }
        // 删除逻辑基于排序后的名次，而不是原始文件中的行号。
        List<ScoreRecord> sorted = new ArrayList<>(getAllSorted());
        if (rank > sorted.size()) {
            return false;
        }
        sorted.remove(rank - 1);
        writeAll(sorted);
        return true;
    }

    private void writeAll(List<ScoreRecord> records) {
        ensureStorageReady();
        List<String> lines = new ArrayList<>();
        for (ScoreRecord record : records) {
            lines.add(record.toStorageLine());
        }
        try {
            Files.write(storagePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write score records to " + storagePath, e);
        }
    }

    private void ensureStorageReady() {
        try {
            Path parent = storagePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            // 首次运行时自动创建对应难度的榜单文件。
            if (!Files.exists(storagePath)) {
                Files.createFile(storagePath);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize storage path " + storagePath, e);
        }
    }

    private static String normalizeDifficulty(String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            return "normal";
        }
        return difficulty.trim().toLowerCase();
    }
}
