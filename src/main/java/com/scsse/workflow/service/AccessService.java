package com.scsse.workflow.service;


import com.scsse.workflow.entity.model.Access;

import java.util.List;

public interface AccessService {
    List<Access> findAllAccess();
    Access findAccessById(Integer accessId);
    Access createAccess(Access access);
    Access updateAccess(Access access);
    void deleteAccessById(Integer accessId);
}
