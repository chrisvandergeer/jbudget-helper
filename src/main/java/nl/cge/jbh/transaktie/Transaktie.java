package nl.cge.jbh.transaktie;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @ToString
public class Transaktie {

    public static final String QUOTE = "\"";
    public static final String COMMA = ",";

    private Long volgnummer;
    private LocalDate datum;
    private BigDecimal bedrag;
    private String tegenrekening;
    private String naamTegenpartij;
    private String omschrijving1;
    private String omschrijving2;
    private String omschrijving3;

    public String toCsv() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(QUOTE).append(volgnummer).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(datum).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(bedrag).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(tegenrekening).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(naamTegenpartij).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(omschrijving1).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(omschrijving2).append(QUOTE);
        stringBuilder.append(COMMA + QUOTE).append(omschrijving3).append(QUOTE);
        return stringBuilder.toString();
    }
}
