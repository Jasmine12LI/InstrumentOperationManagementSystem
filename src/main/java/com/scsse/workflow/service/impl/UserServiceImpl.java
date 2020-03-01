package com.scsse.workflow.service.impl;


import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.dto.RoleDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Access;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Role;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.AccountRepository;
import com.scsse.workflow.repository.RoleRepository;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional

public class UserServiceImpl implements UserService {
     private final ModelMapper modelMapper;
     private final DtoTransferHelper dtoTransferHelper;
     private final UserRepository userRepository;
     private final AccountRepository accountRepository;
     private final RoleRepository roleRepository;

     @Autowired
     public UserServiceImpl(ModelMapper modelMapper,DtoTransferHelper dtoTransferHelper,
                            UserRepository userRepository,AccountRepository accountRepository,
                            RoleRepository roleRepository)
     {
         this.modelMapper= modelMapper;
         this.dtoTransferHelper = dtoTransferHelper;
         this.userRepository = userRepository;
         this.accountRepository = accountRepository;
         this.roleRepository = roleRepository;
     }

    @Override
    public UserDto getUser(Integer userId) {
        return dtoTransferHelper.transferToUserDto(userRepository.findOne(userId));
    }

    @Override
    public List<UserDto> findAllUser() {
        return dtoTransferHelper.transferToListDto(userRepository.findAll(),
                eachItem -> dtoTransferHelper.transferToUserDto((User) eachItem));
    }

    @Override
    public UserDto createUser(User user) {
        return  dtoTransferHelper.transferToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(User user) {
        Integer userId= user.getId();
        User oldUser = userRepository.findOne(userId);
        modelMapper.map(user,oldUser);
        return dtoTransferHelper.transferToUserDto(userRepository.save(oldUser));
    }

    @Override
    public void deleteUserById(Integer userId) {
         userRepository.deleteById(userId);
         return;
    }

    @Override
    public User findUserByName(String name) {
        User user= userRepository.findName(name);
        System.out.println(user);
        return user;
    }

    @Override
    public Set<Role> findRole(Integer userId) {
         User user = userRepository.findOne(userId);
         if(user!=null)
             return user.getRoles();
        return null;
    }

    @Override
    public Set<Access> findAccess(Integer userId) {
         User user = userRepository.findOne(userId);
         Set<Role> roles = findRole(userId);
         Set<Access> accesses = new HashSet<>();
         for(Role role :roles){
             accesses.addAll(role.getAccess());
         }
        return accesses;
    }

    @Override
    public void addRole(Integer userId, Integer roleId) {
       Role role = roleRepository.findOne(roleId);
       User user = userRepository.findOne(userId);
       if(role!=null&&!user.getRoles().contains(role)){
           user.getRoles().add(role);
           userRepository.save(user);

       }
    }

    @Override
    public void remove(Integer userId, Integer roleId) {
        Role role = roleRepository.findOne(roleId);
        User user = userRepository.findOne(userId);
        if(role!=null&&user.getRoles().contains(role)){
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }

    @Override
    public List<RoleDto> findAllRole() {
        return dtoTransferHelper.transferToListDto(roleRepository.findAll(),
                eachItem -> dtoTransferHelper.transferToRoleDto((Role) eachItem));
    }


}
