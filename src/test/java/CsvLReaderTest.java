import nl.cge.jbh.csv.CsvLine;
import nl.cge.jbh.csv.CsvReader;
import nl.cge.jbh.transaktie.Transaktie;
import nl.cge.jbh.transaktie.TransaktieMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvLReaderTest {

    @Test
    void test() {
        CsvReader csvReader = new CsvReader().load("/Users/chris/Downloads/tran_aug_2019.csv");
        List<CsvLine> lines = csvReader.getLines();
        CsvLine line = lines.get(2);
        System.out.println(line);
        assertEquals("NL26RABO0133367215", line.getValue("Tegenrekening IBAN/BBAN"));
        assertEquals(new BigDecimal("-6.40"), line.getAmountValue("Bedrag"));
        assertEquals(LocalDate.of(2019, Month.AUGUST, 1), line.getDateValue("Datum"));
    }

    @Test
    void testMap() {
        CsvReader csvReader = new CsvReader().load("/Users/chris/Downloads/tran_aug_2019.csv");
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
        CsvReader csvReader = new CsvReader().load("/Users/chris/Downloads/tran_aug_2019.csv");
        List<Transaktie> transakties = new TransaktieMapper().map(csvReader.getLines());
        Path file = Files.createFile(Paths.get("/tmp/tr.csv"));
        List<String> csvLines = transakties.stream().map(tr -> tr.toCsv()).collect(Collectors.toList());
        Files.write(file, csvLines);

    }

}