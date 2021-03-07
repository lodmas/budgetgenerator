package com.jskno.budgetgenerator.model.database;

import com.jskno.budgetgenerator.model.database.base.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OptionValue extends AbstractEntity implements Comparable<OptionValue> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    private String label;

    private Integer position;

    private Boolean disable;

    @ManyToOne(fetch = FetchType.EAGER)
    private OptionValueSet optionValueSet;

    public OptionValue() {
    }

    public OptionValue(Long id, String value, String label, Integer position, Boolean disable, OptionValueSet optionValueSet) {
        this.id = id;
        this.value = value;
        this.label = label;
        this.position = position;
        this.disable = disable;
        this.optionValueSet = optionValueSet;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public OptionValueSet getOptionValueSet() {
        return optionValueSet;
    }

    public void setOptionValueSet(OptionValueSet optionValueSet) {
        this.optionValueSet = optionValueSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionValue that = (OptionValue) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(OptionValue o) {
        return this.getPosition() - o.getPosition();
    }
}
