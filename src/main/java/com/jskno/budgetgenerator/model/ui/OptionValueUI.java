package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.OptionValue;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.util.Objects;

public class OptionValueUI {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty value = new SimpleStringProperty(this, "value", "");
    private final StringProperty label = new SimpleStringProperty(this, "label", "");
    private final IntegerProperty position = new SimpleIntegerProperty(this, "position", 0);
    private BooleanProperty disable = new SimpleBooleanProperty(this, "disable", false);
    private OptionValueSetUI optionValueSetUI;

    public OptionValueUI() {
    }

    public OptionValueUI(Long id, String value, String label, Integer position,
                         Boolean disable) {
        this.id.set(id);
        this.value.set(value);
        this.label.set(label);
        this.position.set(position);
        this.disable.set(disable);
    }

    public OptionValueUI(OptionValue optionValue) {
        this.id.set(optionValue.getId());
        this.value.set(optionValue.getValue());
        this.label.set(optionValue.getLabel());
        this.position.set(optionValue.getPosition());
        this.disable.set(optionValue.isDisable());
        this.optionValueSetUI = new OptionValueSetUI(optionValue.getOptionValueSet());
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

    public int getPosition() {
        return position.get();
    }

    public IntegerProperty positionProperty() {
        return position;
    }

    public void setPosition(int position) {
        this.position.set(position);
    }

    public boolean isDisable() {
        return disable.get();
    }

    public BooleanProperty disableProperty() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable.set(disable);
    }

    public OptionValueSetUI getOptionValueSetUI() {
        return optionValueSetUI;
    }

    public void setOptionValueSetUI(OptionValueSetUI optionValueSetUI) {
        this.optionValueSetUI = optionValueSetUI;
    }

    @Override
    public String toString() {
        return getPosition() + ". " + getLabel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionValueUI that = (OptionValueUI) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static Callback<OptionValueUI, Observable[]> extractor = optionValueUI -> new Observable[]
            {optionValueUI.labelProperty(), optionValueUI.positionProperty()};
}
