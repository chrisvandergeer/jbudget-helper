import nl.cge.jbh.csv.CsvLine;
import nl.cge.jbh.csv.CsvLoader;
import nl.cge.jbh.transaktie.Transaktie;
import nl.cge.jbh.transaktie.TransaktieMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvLoaderTest {

    @Test
    void test() {
        CsvLoader csvLoader = new CsvLoader().load("/Users/chris/Downloads/tran_aug_2019.csv");
        List<CsvLine> lines = csvLoader.getLines();
        CsvLine line = lines.get(2);
        System.out.println(line);
        assertEquals("NL26RABO0133367215", line.getValue("Tegenrekening IBAN/BBAN"));
        assertEquals(new BigDecimal("-6.40"), line.getAmountValue("Bedrag"));
        assertEquals(LocalDate.of(2019, Month.AUGUST, 1), line.getDateValue("Datum"));
    }

    @Test
    void testMap() {
        CsvLoader csvLoader = new CsvLoader().load("/Users/chris/Downloads/tran_aug_2019.csv");
        List<Transaktie> transakties = new TransaktieMapper().map(csvLoader.getLines());

//        transakties.forEach(tr -> System.out.println(tr));

        System.out.println(csvLoader.getLines().get(2));
        Transaktie transaktie = transakties.get(2);
        System.out.println(transaktie);
        assertEquals(Long.valueOf(16500), transaktie.getVolgnummer());
        assertEquals(LocalDate.of(2019, Month.AUGUST, 1), transaktie.getDatum());
        assertEquals(new BigDecimal("-6.40"), transaktie.getBedrag());
        assertEquals("NL26RABO0133367215", transaktie.getTegenrekening());
        assertEquals("Visser Contactlenzenprak", transaktie.getNaamTegenpartij());
    }

}