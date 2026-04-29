package edu.hitsz.application;

import edu.hitsz.dao.FileScoreRecordDao;
import edu.hitsz.dao.ScoreRecord;
import edu.hitsz.dao.ScoreRecordDao;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

/**
 * 排行榜页面，负责录入当前成绩、展示指定难度榜单并支持删除记录。
 */
public class LeaderboardPanel extends JPanel {

    private final AircraftWarApp app;
    private final JLabel titleLabel;
    private final JLabel achievementLabel;
    private final LeaderboardTableModel tableModel;
    private final JTable table;
    private String currentDifficulty = "normal";

    public LeaderboardPanel(AircraftWarApp app) {
        this.app = app;
        this.titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        this.achievementLabel = new JLabel(formatAchievementSummaryForDisplay(null), SwingConstants.CENTER);
        this.tableModel = new LeaderboardTableModel();
        this.table = new JTable(tableModel);

        setLayout(new BorderLayout());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        achievementLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(titleLabel);
        headerPanel.add(achievementLabel);
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    public void showLeaderboard(String difficulty, int score) {
        showLeaderboard(difficulty, score, null);
    }

    public void showLeaderboard(String difficulty, int score, String achievementSummary) {
        currentDifficulty = normalizeDifficulty(difficulty);
        achievementLabel.setText(formatAchievementSummaryForDisplay(achievementSummary));
        String playerName = askPlayerName();
        ScoreRecordDao dao = new FileScoreRecordDao(currentDifficulty);
        dao.add(ScoreRecord.ofNow(playerName, score));
        refreshTable();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        JButton deleteButton = new JButton("Delete Selected");
        JButton restartButton = new JButton("Back To Menu");

        deleteButton.addActionListener(event -> deleteSelectedRow());
        restartButton.addActionListener(event -> app.showDifficultySelection());

        buttonPanel.add(deleteButton);
        buttonPanel.add(restartButton);
        return buttonPanel;
    }

    private void deleteSelectedRow() {
        int rowIndex = table.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a leaderboard row first.");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Delete selected record?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        ScoreRecordDao dao = new FileScoreRecordDao(currentDifficulty);
        dao.deleteByRank(rowIndex + 1);
        refreshTable();
    }

    private void refreshTable() {
        ScoreRecordDao dao = new FileScoreRecordDao(currentDifficulty);
        List<ScoreRecord> records = dao.getAllSorted();
        titleLabel.setText("Leaderboard - " + currentDifficulty);
        tableModel.setRecords(records);
    }

    private String askPlayerName() {
        // 允许用户直接回车，避免因为空名字阻塞成绩记录。
        String playerName = JOptionPane.showInputDialog(this, "请输入玩家名：", "Player");
        if (playerName == null || playerName.trim().isEmpty()) {
            return "Player";
        }
        return playerName.trim();
    }

    public static String formatAchievementSummaryForDisplay(String achievementSummary) {
        if (achievementSummary == null || achievementSummary.trim().isEmpty()) {
            return "Achievements: No achievements unlocked";
        }
        return "Achievements: " + achievementSummary.trim();
    }

    private String normalizeDifficulty(String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            return "normal";
        }
        return difficulty.trim().toLowerCase();
    }
}