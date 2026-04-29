package edu.hitsz.application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 应用外层容器，负责在难度选择、游戏页和排行榜页之间切换。
 */
public class AircraftWarApp {

    private static final String CARD_DIFFICULTY = "difficulty";
    private static final String CARD_GAME = "game";
    private static final String CARD_LEADERBOARD = "leaderboard";

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel rootPanel;
    private final DifficultySelectPanel difficultySelectPanel;
    private final GamePagePanel gamePagePanel;
    private final LeaderboardPanel leaderboardPanel;

    public AircraftWarApp() {
        this.frame = new JFrame("Aircraft War");
        this.cardLayout = new CardLayout();
        this.rootPanel = new JPanel(cardLayout);
        this.difficultySelectPanel = new DifficultySelectPanel(this);
        this.gamePagePanel = new GamePagePanel(this);
        this.leaderboardPanel = new LeaderboardPanel(this);
        initFrame();
        rootPanel.add(difficultySelectPanel, CARD_DIFFICULTY);
        rootPanel.add(gamePagePanel, CARD_GAME);
        rootPanel.add(leaderboardPanel, CARD_LEADERBOARD);
        frame.setContentPane(rootPanel);
    }

    private void initFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setBounds(((int) screenSize.getWidth() - Main.WINDOW_WIDTH) / 2, 0,
                Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void show() {
        frame.setVisible(true);
        showDifficultySelection();
    }

    public void showDifficultySelection() {
        // 返回菜单时只切卡片，不在这里重建页面状态。
        cardLayout.show(rootPanel, CARD_DIFFICULTY);
    }

    public void startGame(String difficulty) {
        gamePagePanel.startGame(difficulty);
        cardLayout.show(rootPanel, CARD_GAME);
    }

    public void showLeaderboard(String difficulty, int score) {
        showLeaderboard(difficulty, score, null);
    }

    public void showLeaderboard(String difficulty, int score, String achievementSummary) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 游戏结束可能来自计时线程，因此切页操作统一回到 EDT 执行。
            leaderboardPanel.showLeaderboard(difficulty, score, achievementSummary);
            cardLayout.show(rootPanel, CARD_LEADERBOARD);
        });
    }
}