package nl.cge.jbh.csv;

import nl.cge.jbh.common.ApplicationException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {

    private List<String> headers;
    private List<List<String>> lines = new ArrayList<>();

    public CsvReader load(String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            readHeaders(filePath);
            readLines(filePath);
        } catch (IOException e) {
            ApplicationException.wrap(e);
        }
        return this;
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public List<CsvLine> getLines() {
        return lines.stream()
                .map(line -> new CsvLine(headers, line))
                .collect(Collectors.toList());
    }

    private void readLines(Path path) throws IOException {
        Files.lines(path, Charset.forName("UTF-16"))
                .skip(1)
                .map(header -> header.split("\",\""))
                .forEach(arr -> lines.add(Arrays.stream(arr)
                        .map(f -> f.replaceAll("\"", ""))
                        .collect(Collectors.toList())));
    }

    private void readHeaders(Path path) throws IOException {
        headers = Files.lines(path, Charset.forName("UTF-16"))
                .limit(1)
                .map(header -> header.split(","))
                .flatMap(k -> Arrays.stream(k))
                .map(k -> k.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }

}
