package com.translatorshouse.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Encodes/decodes an ordered key=value map to/from the plain-text save format. */
public final class SaveFormat {
    private SaveFormat() {}

    public static void write(String path, Map<String, String> fields) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, String> e : fields.entrySet()) {
            lines.add(e.getKey() + "=" + e.getValue());
        }
        SaveFile.writeLines(path, lines);
    }

    public static Map<String, String> read(String path) throws IOException {
        Map<String, String> fields = new LinkedHashMap<>();
        for (String line : SaveFile.readLines(path)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            int eq = line.indexOf('=');
            if (eq < 0) continue;
            fields.put(line.substring(0, eq), line.substring(eq + 1));
        }
        return fields;
    }
}
