package com.scsse.workflow.service.shiro;
import com.scsse.workflow.entity.model.Access;
import com.scsse.workflow.entity.model.Role;
import com.scsse.workflow.repository.AccessRepository;
import com.scsse.workflow.repository.RoleRepository;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
@Validated
public class AuthorityQuerier {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessRepository accessRepository;

    public List<Role> getRoleListByUser_id(@NotNull Integer user_id) {
        List<Role> roleList = new ArrayList<>(userService.findRole(user_id));

        return roleList;
    }

    public List<String> getRoleNameListByUser_id(@NotNull Integer user_id){
        List<Role> roleList = getRoleListByUser_id(user_id);
        List<String> roleNameList = new ArrayList<>();
        for(Role role:roleList){
            String roleName = role.getName();
            roleNameList.add(roleName);
        }
        return roleNameList;
    }

    public List<Access> getPermissionListByUser_id(@NotNull Integer user_id){
        List<Access> permissionList = new ArrayList<>(userService.findAccess(user_id));

        return permissionList;
    }


    public List<String> getPermissionNameListByUser_id(@NotNull Integer user_id){
        List<Access> permissionList = getPermissionListByUser_id(user_id);
        List<String> permissionNameList = new ArrayList<>();
        for(Access permission:permissionList){
            String permissionName = permission.getUrl();
            permissionNameList.add(permissionName);
        }
        return permissionNameList;
    }

}

