package nl.cge.jbh.transaktie;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @ToString
public class Transaktie {

    private Long volgnummer;
    private LocalDate datum;
    private BigDecimal bedrag;
    private String tegenrekening;
    private String naamTegenpartij;
    private String omschrijving1;
    private String omschrijving2;
    private String omschrijving3;
}
