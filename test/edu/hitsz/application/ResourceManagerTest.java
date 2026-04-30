package edu.hitsz.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void opensClasspathResourceWhenPackagedInJar() throws IOException {
        try (InputStream inputStream = ResourceManager.openStream("edu/hitsz/application/Main.class")) {
            byte[] header = new byte[4];
            int read = inputStream.read(header);

            assertArrayEquals(new byte[] { (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE }, header);
            assertArrayEquals(new byte[] { 4 }, new byte[] { (byte) read });
        }
    }

    @Test
    void fallsBackToDevelopmentFilePath() throws IOException {
        Path resourceFile = tempDir.resolve("resource.txt");
        Files.write(resourceFile, "fallback".getBytes(StandardCharsets.UTF_8));

        try (InputStream inputStream = ResourceManager.openStream(resourceFile.toString())) {
            byte[] bytes = new byte[8];
            int read = inputStream.read(bytes);

            assertArrayEquals("fallback".getBytes(StandardCharsets.UTF_8), bytes);
            assertArrayEquals(new byte[] { 8 }, new byte[] { (byte) read });
        }
    }

    @Test
    void opensProjectImageResource() throws IOException {
        try (InputStream inputStream = ResourceManager.openStream("src/images/hero.png")) {
            byte[] header = new byte[4];
            int read = inputStream.read(header);

            assertArrayEquals(new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47 }, header);
            assertArrayEquals(new byte[] { 4 }, new byte[] { (byte) read });
        }
    }

    @Test
    void opensProjectAudioResource() throws Exception {
        try (AudioInputStream audioInputStream = ResourceManager.openAudioInputStream("src/videos/bgm.wav")) {
            org.junit.jupiter.api.Assertions.assertTrue(audioInputStream.getFrameLength() > 0);
        }
    }

    @Test
    void reportsMissingResourceWithLocation() {
        IOException exception = assertThrows(IOException.class,
                () -> ResourceManager.openStream("missing-release-resource.bin"));

        org.junit.jupiter.api.Assertions.assertTrue(exception.getMessage().contains("missing-release-resource.bin"));
    }
}