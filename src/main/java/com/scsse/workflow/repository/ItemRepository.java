package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {
}
