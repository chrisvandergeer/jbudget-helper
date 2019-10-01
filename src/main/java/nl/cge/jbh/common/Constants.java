package nl.cge.jbh.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface Constants {

    static final String CSV_DIR = "/home/chris/Downloads/";

    static Path getPath(String filename) {
        return Paths.get(CSV_DIR + filename);
    }
}
