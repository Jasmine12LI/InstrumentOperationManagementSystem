package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.dto.ActivityDto;
import com.scsse.workflow.entity.dto.RecruitDto;
import com.scsse.workflow.entity.model.*;
import com.scsse.workflow.repository.*;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.service.RoleService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import com.scsse.workflow.util.dao.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:18
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AccessRepository accessRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, AccessRepository accessRepository) {
        this.roleRepository = roleRepository;
        this.accessRepository = accessRepository;
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(Integer roleId) {
        return roleRepository.findOne(roleId);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Integer roleId) {
        roleRepository.delete(roleId);
    }

    @Override
    public Set<Access> findAccessByRoleId(Integer roleId) {
        Set<Access> set = new HashSet<>();
        Role role = roleRepository.findOne(roleId);
        if (role != null)
            set = role.getAccesses();
        return set;
    }

    @Override
    public void addAccess(Integer roleId, Integer accessId) {
        Role role = roleRepository.findOne(roleId);
        Access access = accessRepository.findOne(accessId);
        if (access != null) {
            role.getAccesses().add(access);
            roleRepository.save(role);
        }
    }

    @Override
    public void removeAccess(Integer roleId, Integer accessId) {
        Role role = roleRepository.findOne(roleId);
        Access access = accessRepository.findOne(accessId);
        if (access != null&&role.getAccesses().contains(access)) {
            role.getAccesses().remove(access);
            roleRepository.save(role);
        }
    }
}
