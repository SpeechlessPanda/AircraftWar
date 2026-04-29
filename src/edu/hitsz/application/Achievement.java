package edu.hitsz.application;

public enum Achievement {
    SCORE_1000("Score 1000", "Reach 1000 points in one run"),
    FIRST_BOSS_DOWN("First Boss Down", "Defeat a Boss enemy for the first time"),
    BOMB_SWEEP("Bomb Sweep", "Destroy at least three scoring targets with one bomb"),
    FROST_CONTROL("Frost Control", "Freeze or slow at least three targets with one freeze supply"),
    ACE_HUNTER("Ace Hunter", "Destroy an ace enemy");

    private final String displayName;
    private final String description;

    Achievement(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}