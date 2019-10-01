package nl.cge.jbh.transaktie;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
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
    private String budgetSoort = "";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<String> tags = new HashSet<>();

    public Transaktie addTag(String tagName) {
        tags.add(tagName);
        return this;
    }

    public static String csvHeader() {
        StringBuilder result = new StringBuilder();
        result.append(QUOTE).append("volgnummer").append(QUOTE);
        result.append(COMMA + QUOTE).append("datum").append(QUOTE);
        result.append(COMMA + QUOTE).append("bedrag").append(QUOTE);
        result.append(COMMA + QUOTE).append("tegenrekening").append(QUOTE);
        result.append(COMMA + QUOTE).append("naamTegenpartij").append(QUOTE);
        result.append(COMMA + QUOTE).append("omschrijving").append(QUOTE);
        result.append(COMMA + QUOTE).append("budgetSoort").append(QUOTE);
        result.append(COMMA + QUOTE).append("tags").append(QUOTE);
        return result.toString();
    }

    public String toCsv() {
        StringBuilder result = new StringBuilder();
        result.append(QUOTE).append(volgnummer).append(QUOTE);
        result.append(COMMA + QUOTE).append(datum).append(QUOTE);
        result.append(COMMA + QUOTE).append(bedrag).append(QUOTE);
        result.append(COMMA + QUOTE).append(tegenrekening).append(QUOTE);
        result.append(COMMA + QUOTE).append(naamTegenpartij).append(QUOTE);
        result.append(COMMA + QUOTE).append(omschrijving1).append(omschrijving2).append(omschrijving3).append(QUOTE);
        result.append(COMMA + QUOTE).append(budgetSoort).append(QUOTE);
        result.append(COMMA + QUOTE)
                .append(tags.stream().reduce("", (subresult, tag) -> subresult + " " + tag))
                .append(QUOTE);
        return result.toString();
    }
}
