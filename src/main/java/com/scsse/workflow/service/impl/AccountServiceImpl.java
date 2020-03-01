package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Device;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.AccountRepository;
import com.scsse.workflow.repository.DeviceRepository;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.AccountService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import org.apache.shiro.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

private final ModelMapper modelMapper;
private final DtoTransferHelper dtoTransferHelper;
private final AccountRepository accountRepository;
private final DeviceRepository deviceRepository;
private final UserRepository  userRepository;

@Autowired
public AccountServiceImpl(ModelMapper modelMapper,DtoTransferHelper dtoTransferHelper,
                          AccountRepository accountRepository,DeviceRepository deviceRepository,
                          UserRepository userRepository){
    this.modelMapper = modelMapper;
    this.dtoTransferHelper =  dtoTransferHelper;
    this.accountRepository = accountRepository;
    this.deviceRepository = deviceRepository;
    this.userRepository = userRepository;
}
    @Override
    public AccountDto getOneAccount(Integer id) {
        return dtoTransferHelper.transferToAccountDto(accountRepository.findOne(id));
    }

    @Override
    public List<AccountDto> findAllAccount() {
        return dtoTransferHelper.transferToListDto(accountRepository.findAll(),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public AccountDto createAccount(Account account) {
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto updateAccount(Account account) {
        Integer accountId = account.getId();
        Account oldAccount = accountRepository.findOne(accountId);
        modelMapper.map(account,oldAccount);
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(oldAccount));
    }

    @Override
    public Boolean deleteAccount(Integer id) {
        Account oldAccount = accountRepository.findOne(id);
        if(oldAccount.getStatus()==1)
            return false;
        accountRepository.deleteAccountById(id);
        return true;
    }

    @Override
    public List<AccountDto> findAccountByDeviceId(Integer deviceId) {
        return dtoTransferHelper.transferToListDto(accountRepository.findAccountByDeviceId(deviceId),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public List<AccountDto> findAccountByCheckUser(Integer checkUserId) {
        User user= userRepository.findOne(checkUserId);
        Set<Account> accounts=  user.getCheckAccounts();
        return dtoTransferHelper.transferToListDto(accounts,
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public List<AccountDto> findAccountBySubmit(Integer submitUserId) {
        User user= userRepository.findOne(submitUserId);
        Set<Account> accounts= user.getAccounts();
        return dtoTransferHelper.transferToListDto(accounts,
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public List<AccountDto> findAccountByStatus(Integer status) {
        return dtoTransferHelper.transferToListDto(accountRepository.findAccountsByStatus(status),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public AccountDto checkAccountAndPass(Integer accountId) {
        Account oldAccount = accountRepository.findOne(accountId);
        Device device= oldAccount.getDevice();
        BigDecimal price = oldAccount.getPrice(),oldExpense = device.getExpense(),
                   oldIncome = device.getIncome();
        if(oldAccount.getIsExpense())
        {
            oldExpense = oldExpense.add(price);
            device.setExpense(oldExpense);
        }
        else{
            oldIncome = oldIncome.add(price);
            device.setIncome(oldIncome);
        }
         deviceRepository.save(device);
        oldAccount.setStatus(1);
        oldAccount.setCheckTime(new Date());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        oldAccount.setSubmitUser(user);
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(oldAccount));
    }

    @Override
    public AccountDto checkAccountAndNoPass(Integer accountId) {
        Account oldAccount = accountRepository.findOne(accountId);
        oldAccount.setStatus(2);
        oldAccount.setCheckTime(new Date());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        oldAccount.setSubmitUser(user);
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(oldAccount));
    }

    @Override
    public List<AccountDto> findAccountByIsExpense(Boolean IsExpense) {
        return dtoTransferHelper.transferToListDto(accountRepository.findAccountsByIsExpense(IsExpense),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }
}
