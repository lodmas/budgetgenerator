//@formatter:off
/**
 *  $$Id$$
 *       . * .
 *     * RRRR  *    Copyright (c) 2017 EUIPO: European Union Intellectual
 *   .   RR  R   .  Property Office (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package com.jskno.budgetgenerator.model.database.listener;

import com.jskno.budgetgenerator.model.database.base.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;
import java.util.Date;

public class EntityListener {

    @PrePersist
    public void onCreate(final AbstractEntity entity) {
        //final String currentUser = SecurityContextHolder.getContext().getAuthentication().
    //                                      getName();
        final String currentUser = "SYSTEM";
        final LocalDate currentDate = LocalDate.now();
        entity.setCreatedByUser(currentUser);
        entity.setCreatedAt(currentDate);
        entity.setUpdatedByUser(currentUser);
        entity.setUpdatedAt(currentDate);
    }

    @PreUpdate
    public void onUpdate(final AbstractEntity entity) {
        final String currentUser = "SYSTEM";
        final LocalDate currentDate = LocalDate.now();
        entity.setUpdatedByUser(currentUser);
        entity.setUpdatedAt(currentDate);
    }
}
