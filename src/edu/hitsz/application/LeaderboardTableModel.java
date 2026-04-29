package edu.hitsz.application;

import edu.hitsz.dao.ScoreRecord;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜表格模型，把 ScoreRecord 映射成 JTable 需要的二维数据。
 */
public class LeaderboardTableModel extends DefaultTableModel {

    private static final String[] COLUMN_NAMES = { "Rank", "Player", "Score", "Time" };

    public LeaderboardTableModel() {
        super(COLUMN_NAMES, 0);
    }

    public void setRecords(List<ScoreRecord> records) {
        setRowCount(0);
        List<ScoreRecord> safeRecords = records == null ? new ArrayList<>() : records;
        for (int i = 0; i < safeRecords.size(); i++) {
            ScoreRecord record = safeRecords.get(i);
            addRow(new Object[] { i + 1, record.getPlayerName(), record.getScore(), record.getFormattedTime() });
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}