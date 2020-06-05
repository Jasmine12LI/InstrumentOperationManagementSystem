package com.scsse.workflow.repository;

import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAccountByDeviceId(Integer device_id);
    Account findAccountById(Integer account_id);
    @Query(value = "select a.* from account a join  user u on  a.check_user_id = u.id where u.id=?1",nativeQuery = true)
    List<Account> findByCheckUser_id(Integer check_id);
    List<Account> findAccountsByStatus(Integer status);
    void deleteAccountById(Integer id);
}
