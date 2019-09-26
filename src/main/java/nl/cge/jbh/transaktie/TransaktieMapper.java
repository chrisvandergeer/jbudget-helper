package nl.cge.jbh.transaktie;

import nl.cge.jbh.csv.CsvLine;

import java.util.List;
import java.util.stream.Collectors;

public class TransaktieMapper {

    public Transaktie map(CsvLine csvLine) {
        Transaktie transaktie = new Transaktie();
        transaktie.setVolgnummer(csvLine.getLongValue("Volgnr"));
        transaktie.setDatum(csvLine.getDateValue("Datum"));
        transaktie.setBedrag(csvLine.getAmountValue("Bedrag"));
        transaktie.setTegenrekening(csvLine.getValue("Tegenrekening IBAN/BBAN"));
        transaktie.setNaamTegenpartij(csvLine.getValue("Naam tegenpartij"));
        transaktie.setOmschrijving1(csvLine.getValue("Omschrijving-1"));
        transaktie.setOmschrijving2(csvLine.getValue("Omschrijving-2"));
        transaktie.setOmschrijving3(csvLine.getValue("Omschrijving-3"));
        return transaktie;
    }

    public List<Transaktie> map(List<CsvLine> csvLines) {
        return csvLines.stream()
                .map(line -> map(line))
                .collect(Collectors.toList());
    }
}
