package edu.hitsz.application;

import javax.swing.*;

/**
 * 程序入口
 * 
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    private static final String DEFAULT_DIFFICULTY = "normal";
    private static final String[] DIFFICULTY_OPTIONS = { "easy", "normal", "hard" };

    public static void main(String[] args) {
        // 所有 Swing 组件都在 EDT 上创建，避免界面线程问题。
        SwingUtilities.invokeLater(() -> {
            System.out.println("Hello Aircraft War");
            AircraftWarApp app = new AircraftWarApp();
            if (args != null && args.length > 0 && args[0] != null && !args[0].trim().isEmpty()) {
                app.show();
                app.startGame(args[0].trim().toLowerCase());
            } else {
                app.show();
            }
        });
    }
}
