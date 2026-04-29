package edu.hitsz.application;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * 难度选择页，负责把用户选择转换成游戏启动参数。
 */
public class DifficultySelectPanel extends JPanel {

    public DifficultySelectPanel(AircraftWarApp app) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select Difficulty", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 16));
        buttonPanel.add(createDifficultyButton(app, "Easy", "easy"));
        buttonPanel.add(createDifficultyButton(app, "Normal", "normal"));
        buttonPanel.add(createDifficultyButton(app, "Hard", "hard"));
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createDifficultyButton(AircraftWarApp app, String text, String difficulty) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 22));
        // 这里直接把规范化后的难度字符串交给外层应用。
        button.addActionListener(event -> app.startGame(difficulty));
        return button;
    }
}