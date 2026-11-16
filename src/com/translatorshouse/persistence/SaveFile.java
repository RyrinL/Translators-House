package com.translatorshouse.persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//Low-level: read and write a file as a list of text lines.
public final class SaveFile {
    private SaveFile() {}

    public static void writeLines(String path, List<String> lines) throws IOException {
        Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
    }

    public static List<String> readLines(String path) throws IOException {
        return Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
    }

    public static boolean exists(String path) {
        return Files.exists(Path.of(path));
    }
}
