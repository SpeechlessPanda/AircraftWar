package edu.hitsz.application;

import edu.hitsz.dao.ScoreRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaderboardTableModelTest {

    @Test
    void convertsScoreRecordsToTableRowsWithRank() {
        List<ScoreRecord> records = List.of(
                new ScoreRecord("Alice", 300, LocalDateTime.of(2026, 4, 24, 10, 0, 0)),
                new ScoreRecord("Bob", 200, LocalDateTime.of(2026, 4, 24, 10, 1, 0)));

        LeaderboardTableModel model = new LeaderboardTableModel();
        model.setRecords(records);

        assertEquals(2, model.getRowCount());
        assertEquals(1, model.getValueAt(0, 0));
        assertEquals("Alice", model.getValueAt(0, 1));
        assertEquals(300, model.getValueAt(0, 2));
        assertEquals("2026-04-24 10:00:00", model.getValueAt(0, 3));
        assertEquals(2, model.getValueAt(1, 0));
    }
}