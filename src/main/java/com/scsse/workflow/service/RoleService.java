package com.scsse.workflow.service;


import com.scsse.workflow.entity.model.Access;
import com.scsse.workflow.entity.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAllRole();
    Role findRoleById(Integer roleId);
    Role createRole(Role role);
    Role updateRole(Role role);
    void deleteRoleById(Integer roleId);
    Set<Access> findAccessByRoleId(Integer roleId);
    void addAccess(Integer roleId,Integer accessId);
    void removeAccess(Integer roleId,Integer accessId);
}
