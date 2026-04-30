package edu.hitsz.application;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class ResourceManager {

    private ResourceManager() {
    }

    static InputStream openStream(String location) throws IOException {
        String resourceLocation = normalizeResourceLocation(location);
        InputStream resourceStream = ResourceManager.class.getClassLoader().getResourceAsStream(resourceLocation);
        if (resourceStream != null) {
            return new BufferedInputStream(resourceStream);
        }

        Path filePath = Paths.get(location);
        if (Files.exists(filePath)) {
            return new BufferedInputStream(new FileInputStream(filePath.toFile()));
        }

        throw new IOException("Resource not found: " + location + " (classpath: " + resourceLocation + ")");
    }

    static AudioInputStream openAudioInputStream(String location) throws IOException, UnsupportedAudioFileException {
        return AudioSystem.getAudioInputStream(openStream(location));
    }

    private static String normalizeResourceLocation(String location) {
        String normalized = location.replace('\\', '/');
        if (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        if (normalized.startsWith("src/")) {
            normalized = normalized.substring("src/".length());
        }
        return normalized;
    }
}