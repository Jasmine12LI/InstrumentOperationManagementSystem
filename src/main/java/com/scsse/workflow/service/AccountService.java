package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.model.Account;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AccountService {
    @Transactional
    AccountDto getOneAccount(Integer id);

    List<AccountDto> findAllAccount();
    AccountDto createAccount(Account account);
    AccountDto updateAccount(Account account);
    Boolean deleteAccount(Integer id);
    List<AccountDto> findAccountByDeviceId(Integer deviceId);
    List<AccountDto> findAccountByCheckUser(Integer checkUserId);
    List<AccountDto> findAccountBySubmit(Integer status);
    List<AccountDto> findAccountByStatus(Integer status);
    AccountDto checkAccountAndPass(Integer accountId,String des);
    AccountDto checkAccountAndNoPass(Integer accountId,String des);
    File pringFile(Integer accountId) throws IOException;
}
