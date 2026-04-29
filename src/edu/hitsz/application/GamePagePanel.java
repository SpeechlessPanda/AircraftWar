package edu.hitsz.application;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * 游戏页容器，每次开始新局时重建一个 Game 实例。
 */
public class GamePagePanel extends JPanel {

    private final AircraftWarApp app;
    private Game currentGame;

    public GamePagePanel(AircraftWarApp app) {
        this.app = app;
        setLayout(new BorderLayout());
    }

    public void startGame(String difficulty) {
        // 重新开始时直接替换旧面板，避免沿用上局的定时器和对象状态。
        removeAll();
        currentGame = GameFactory.create(difficulty, app);
        add(currentGame, BorderLayout.CENTER);
        revalidate();
        repaint();
        currentGame.action();
        currentGame.requestFocusInWindow();
    }
}