package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("select  r.name from Role r")
    List<String> findAllName();
}
