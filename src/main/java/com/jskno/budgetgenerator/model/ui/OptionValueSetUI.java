package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.util.Objects;

public class OptionValueSetUI {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty value = new SimpleStringProperty(this, "value", "");
    private final StringProperty label = new SimpleStringProperty(this, "label", "");

    public OptionValueSetUI() {
    }

    public OptionValueSetUI(Long id, String value, String label) {
        this.id.set(id);
        this.value.set(value);
        this.label.set(label);
    }

    public OptionValueSetUI(OptionValueSet optionValueSet) {
        this.id.set(optionValueSet.getId());
        this.value.set(optionValueSet.getValue());
        this.label.set(optionValueSet.getLabel());
    }

    public Long getId() {
        return id != null ? id.get() : null;
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    @Override
    public String toString() {
        return "OptionValueUI{" +
                "value=" + getValue() +
                ", label=" + getLabel() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionValueSetUI that = (OptionValueSetUI) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static Callback<OptionValueSetUI, Observable[]> extractor = optionValueUI -> new Observable[]
            {optionValueUI.valueProperty(), optionValueUI.labelProperty()};
}
