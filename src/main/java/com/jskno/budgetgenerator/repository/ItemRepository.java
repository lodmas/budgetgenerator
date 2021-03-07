package com.jskno.budgetgenerator.repository;

import com.jskno.budgetgenerator.model.database.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByConcept(String concept);

    Item findById(long id);

    @Override
    List<Item> findAll();
}
