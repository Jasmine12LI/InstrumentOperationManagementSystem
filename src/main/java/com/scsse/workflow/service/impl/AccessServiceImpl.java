package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.model.Access;
import com.scsse.workflow.entity.model.Role;
import com.scsse.workflow.repository.AccessRepository;
import com.scsse.workflow.repository.RoleRepository;
import com.scsse.workflow.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:18
 */
@Service
@Transactional
public class AccessServiceImpl implements AccessService {

    private final AccessRepository accessRepository;

    @Autowired
    public AccessServiceImpl(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    @Override
    public List<Access> findAllAccess() {
        return accessRepository.findAll();
    }

    @Override
    public Access findAccessById(Integer roleId) {
        return accessRepository.findOne(roleId);
    }

    @Override
    public Access createAccess(Access access) {
        return accessRepository.save(access);
    }

    @Override
    public Access updateAccess(Access access) {
        return accessRepository.save(access);
    }

    @Override
    public void deleteAccessById(Integer accessId) {
        accessRepository.delete(accessId);
    }

}
