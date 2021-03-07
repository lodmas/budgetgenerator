package com.jskno.budgetgenerator.model.database;

import com.jskno.budgetgenerator.model.database.base.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class OptionValueSet extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    private String label;

    @OneToMany(
            mappedBy = "optionValueSet",
            fetch = FetchType.EAGER
    )
    private List<OptionValue> optionValues = new ArrayList<>();

    public OptionValueSet() {
    }

    public OptionValueSet(Long id, String value, String label) {
        this.id = id;
        this.value = value;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<OptionValue> getOptionValues() {
        return optionValues;
    }

//    public void setOptionValues(List<OptionValue> optionValues) {
//        this.optionValues = optionValues;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionValueSet that = (OptionValueSet) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
