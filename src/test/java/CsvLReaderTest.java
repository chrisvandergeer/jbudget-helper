import nl.cge.jbh.catecorize.CategorizeCommand;
import nl.cge.jbh.csv.CsvLine;
import nl.cge.jbh.csv.CsvReader;
import nl.cge.jbh.transaktie.Transaktie;
import nl.cge.jbh.transaktie.TransaktieMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvLReaderTest {

    private String csvInput;

    @BeforeEach
    void setup() {
        csvInput = "/home/chris/Downloads/tran_aug_2019.csv";
    }

    @Test
    void test() {
        CsvReader csvReader = new CsvReader().load(csvInput);
        List<CsvLine> lines = csvReader.getLines();
        CsvLine line = lines.get(2);
        System.out.println(line);
        assertEquals("NL26RABO0133367215", line.getValue("Tegenrekening IBAN/BBAN"));
        assertEquals(new BigDecimal("-6.40"), line.getAmountValue("Bedrag"));
        assertEquals(LocalDate.of(2019, Month.AUGUST, 1), line.getDateValue("Datum"));
    }

    @Test
    void testMap() {
        CsvReader csvReader = new CsvReader().load(csvInput);
        List<Transaktie> transakties = new TransaktieMapper().map(csvReader.getLines());
        Transaktie transaktie = transakties.get(2);
        assertEquals(Long.valueOf(16500), transaktie.getVolgnummer());
        assertEquals(LocalDate.of(2019, Month.AUGUST, 1), transaktie.getDatum());
        assertEquals(new BigDecimal("-6.40"), transaktie.getBedrag());
        assertEquals("NL26RABO0133367215", transaktie.getTegenrekening());
        assertEquals("Visser Contactlenzenprak", transaktie.getNaamTegenpartij());
        assertEquals("VF19153839", transaktie.getOmschrijving1());
        assertEquals("", transaktie.getOmschrijving2());
        assertEquals("", transaktie.getOmschrijving3());
    }

    @Test
    void testCsvWriter() throws IOException {
        CsvReader csvReader = new CsvReader().load(csvInput);
        List<Transaktie> transakties = new TransaktieMapper().map(csvReader.getLines());
        Path csvOutput = Paths.get("/tmp/tr.csv");
        Files.deleteIfExists(csvOutput);
        Path file = Files.createFile(csvOutput);
        List<String> csvLines = transakties.stream().map(tr -> tr.toCsv()).collect(Collectors.toList());
        csvLines.add(0, Transaktie.csvHeader());
        Files.write(file, csvLines);
    }

    @Test
    void categoriseer() throws IOException {
        CsvReader csvReader = new CsvReader().load(csvInput);
        List<Transaktie> transakties = new TransaktieMapper().map(csvReader.getLines());

        Path filePath = Paths.get("./src/main/resources/categorize.csv");
        List<CategorizeCommand> collect = Files.readAllLines(filePath).stream()
                .map(l -> toCategorizeCommand(l))
                .collect(Collectors.toList());

        collect.forEach(c -> System.out.println(c));

//        transakties.stream().filter(t -> t.getNaamTegenpartij().contains("AH van den Heuvel")).forEach(t -> {
//            t.setBudgetSoort("variabel");
//            t.addTag("boodschappen");
//        });
//
//        Path csvOutput = Paths.get("/tmp/tr.csv");
//        Files.deleteIfExists(csvOutput);
//        Path file = Files.createFile(csvOutput);
//        List<String> csvLines = transakties.stream().map(tr -> tr.toCsv()).collect(Collectors.toList());
//        csvLines.add(0, Transaktie.csvHeader());
//        Files.write(file, csvLines);


    }

    private CategorizeCommand toCategorizeCommand(String line) {
        CategorizeCommand result = new CategorizeCommand();
        String[] splittedLine = line.split(";");
        result.setFieldNameCondition(splittedLine[0].split("=")[0]);
        result.setFieldValueCondition(splittedLine[0].split("=")[1]);
        result.setBudgetSoort(splittedLine[1]);
        result.setTags(Arrays.stream(splittedLine[2].split(" ")).collect(Collectors.toList()));
        return result;
    }

}