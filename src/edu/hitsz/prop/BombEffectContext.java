package edu.hitsz.prop;

public class BombEffectContext {

    private int scoreBonus;

    public void addScoreBonus(int value) {
        scoreBonus += value;
    }

    public int getScoreBonus() {
        return scoreBonus;
    }
}