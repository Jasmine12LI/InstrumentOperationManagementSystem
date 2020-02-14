package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:08
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
