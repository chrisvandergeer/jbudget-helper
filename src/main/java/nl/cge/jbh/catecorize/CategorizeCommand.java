package nl.cge.jbh.catecorize;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CategorizeCommand {

    private String fieldNameCondition;
    private String fieldValueCondition;
    private String budgetSoort;
    private List<String> tags = new ArrayList<>();

}
