package nl.cge.jbh.csv;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvLine {

    private Map<String, String> line = new HashMap<>();

    public CsvLine(List<String> headers, List<String> values) {
        for (int i = 0; i < headers.size(); i++) {
            line.put(headers.get(i), values.get(i));
        }
    }

    public String getValue(String header) {
        return line.get(header);
    }

    public BigDecimal getAmountValue(String fieldName) {
        String amount = getValue(fieldName).replaceAll(",", ".");
        return new BigDecimal(amount);
    }

    public LocalDate getDateValue(String fieldName) {
        String date = getValue(fieldName);
        return LocalDate.parse(date);
    }

    @Override
    public String toString() {
        String lf = System.lineSeparator();
        String tab = "\t";
        String sep = "," + lf + tab;
        String prefix = "{" + lf + tab;
        String postfix = lf + "}";
        return line.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(sep, prefix, postfix));
    }

    public Long getLongValue(String fieldName) {
        String longValue = getValue(fieldName);
        return Long.valueOf(longValue);
    }
}
