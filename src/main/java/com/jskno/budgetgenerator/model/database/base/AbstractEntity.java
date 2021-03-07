package com.jskno.budgetgenerator.model.database.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jskno.budgetgenerator.model.database.listener.EntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@MappedSuperclass
@EntityListeners(EntityListener.class)
public class AbstractEntity implements Serializable {

    @Column(name = "CREATED_BY_USER")
    @JsonIgnore
    private String createdByUser;

    @Column(name = "CREATED_AT")
    @JsonIgnore
    private LocalDate createdAt;

    @Column(name = "UPDATED_BY_USER")
    @JsonIgnore
    private String updatedByUser;

    @Column(name = "UPDATED_AT")
    @JsonIgnore
    private LocalDate updatedAt;

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

}
