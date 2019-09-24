package nl.cge.jbh;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Starter {

    private List<String> headers;
    private List<List<String>> lines = new ArrayList<>();


    public static void main(String... args) throws IOException {

        new Starter().run();


    }

    private void run() throws IOException {
        Path path = Paths.get("/home/chris/Downloads/tran_aug_2019.csv");
        readHeaders(path);
        readLines(path);
        lines.forEach(l -> System.out.println(l.get(0)));

    }

    private void readLines(Path path) throws IOException {
        Files.lines(path, Charset.forName("UTF-16"))
                .skip(1)"
                .map(header -> header.split(","))
                .map(arr -> Arrays.stream(arr).map(s -> s.replaceAll("\"", "")))
                .forEach(arr -> lines.add(Arrays.stream(arr).collect(Collectors.toList())));
//                .flatMap(k -> Arrays.stream(k))
//                .map(k -> k.replaceAll("\"", ""))
//                .collect(Collectors.toList());
    }

    private void mapColumns(String[] arr) {
        Arrays.stream(arr).collect(Collectors.toList());
    }

    private void readHeaders(Path path) throws IOException {
        headers = Files
                .lines(path, Charset.forName("UTF-16"))
                .limit(1)
                .map(header -> header.split(","))
                .flatMap(k -> Arrays.stream(k))
                .map(k -> k.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }

}
