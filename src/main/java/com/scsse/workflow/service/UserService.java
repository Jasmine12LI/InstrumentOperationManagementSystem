package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.dto.RoleDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Access;
import com.scsse.workflow.entity.model.Role;
import com.scsse.workflow.entity.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface UserService {
    @Transactional
    UserDto getUser(Integer userId);

    List<UserDto> findAllUser();
    UserDto createUser(User user);
    UserDto updateUser(User user);
    void deleteUserById(Integer userId);
    User findUserByName(String name);
    Set<Role> findRole(Integer userId);
    Set<Access> findAccess(Integer userId);
    void addRole(Integer userId, Integer roleId);
    void remove(Integer userId, Integer roleId);
    List<RoleDto> findAllRole();

}
