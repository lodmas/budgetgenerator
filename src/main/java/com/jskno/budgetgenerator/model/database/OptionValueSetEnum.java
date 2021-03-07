package com.jskno.budgetgenerator.model.database;

import java.util.Arrays;

public enum OptionValueSetEnum {

    CATEGORY(1L, "category", "Categoría"),
    UNIT(2L, "units", "Unidades");

    private Long id;
    private String value;
    private String label;

    OptionValueSetEnum(Long id, String value, String label) {
        this.id = id;
        this.value = value;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static OptionValueSetEnum getFromId(Long id) {
        return Arrays.stream(values())
                .filter(optionValueSetEnum -> optionValueSetEnum.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The option value set with id: " + id + " does not exist"));
    }

}
