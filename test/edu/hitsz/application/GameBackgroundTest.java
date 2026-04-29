package edu.hitsz.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class GameBackgroundTest {

    @Test
    void returnsDifficultySpecificBackgroundImage() {
        assertSame(ImageManager.BACKGROUND_IMAGE, ImageManager.getBackgroundImageForDifficulty("easy"));
        assertSame(ImageManager.BACKGROUND_IMAGE_2, ImageManager.getBackgroundImageForDifficulty("normal"));
        assertSame(ImageManager.BACKGROUND_IMAGE_3, ImageManager.getBackgroundImageForDifficulty("hard"));
        assertSame(ImageManager.BACKGROUND_IMAGE_2, ImageManager.getBackgroundImageForDifficulty("unknown"));
    }
}