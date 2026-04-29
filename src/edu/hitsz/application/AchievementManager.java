package edu.hitsz.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AchievementManager {

    private final Set<Achievement> unlockedAchievements = new LinkedHashSet<>();

    public boolean unlock(Achievement achievement) {
        if (achievement == null || !unlockedAchievements.add(achievement)) {
            return false;
        }
        System.out.println(
                "Achievement unlocked: " + achievement.getDisplayName() + " - " + achievement.getDescription());
        return true;
    }

    public List<Achievement> getUnlockedAchievements() {
        return Collections.unmodifiableList(new ArrayList<>(unlockedAchievements));
    }

    public String summary() {
        if (unlockedAchievements.isEmpty()) {
            return "No achievements unlocked";
        }
        return unlockedAchievements.stream()
                .map(Achievement::getDisplayName)
                .collect(Collectors.joining(", "));
    }
}