package com.scsse.workflow.service;


import com.scsse.workflow.entity.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRole();
    Role findRoleById(Integer roleId);
    Role createRole(Role role);
    Role updateRole(Role role);
    void deleteRoleById(Integer roleId);
}
