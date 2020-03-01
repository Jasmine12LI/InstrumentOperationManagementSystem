package com.scsse.workflow.repository;

import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAccountByDeviceId(Integer device_id);
    Account findAccountById(Integer account_id);
    List<Account> findAccountByCheckUser(Integer checkUser_Id);
    List<Account> findAccountsBySubmitUser(Integer submitUser_id);
    List<Account> findAccountsByStatus(Integer status);
    void deleteAccountById(Integer id);
     List<AccountDto> findAccountsByIsExpense(Boolean isExpense);
}
