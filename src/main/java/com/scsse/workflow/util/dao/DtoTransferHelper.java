package com.scsse.workflow.util.dao;


import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.dto.DeviceDto;
import com.scsse.workflow.entity.dto.RoleDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Device;
import com.scsse.workflow.entity.model.Role;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.AccountRepository;
import com.scsse.workflow.repository.DeviceRepository;
import com.scsse.workflow.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

@Component
public class DtoTransferHelper {

    private final ModelMapper modelMapper;
    private  final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final AccountRepository accountRepository;
    private final static Logger logger = LoggerFactory.getLogger(DtoTransferHelper.class);

    @Autowired
    public DtoTransferHelper(ModelMapper modelMapper,UserRepository userRepository,
                             DeviceRepository deviceRepository, AccountRepository accountRepository){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.accountRepository = accountRepository;
    }
    public <T> List<T> transferToListDto(Collection<?> collection, TransferToListDtoOneParam<T> method) {
        List<T> result = new ArrayList<>();
        collection.stream().map(method::transferToDto).forEach(result::add);
        return result;
    }
    public <T> List<T> transferToListDto(Collection<?> collection) {
        final String PATTERN_PREFIX = "transferTo";
        final String PATTERN_SUFFIX = "(Dto)?";
        final String LIST_ADD = "add";

        if (collection.toArray().length > 0) {
            Class instanceClass = collection.toArray()[0].getClass();
            // search methods in this helper to find if there is a suitable method to transfer instance
            for (Method method : this.getClass().getMethods()) {
                // if matches then call the method
                if (method.getName().matches(PATTERN_PREFIX + instanceClass.getSimpleName() + PATTERN_SUFFIX)) {
                    List<T> result = new ArrayList<>();
                    collection.stream().map(object -> {
                        // call the method to cast the class
                        try {
                            Class dtoClass = method.getReturnType();
                            return dtoClass.cast(method.invoke(this, object));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            logger.error(e.getMessage(), e);
                            return null;
                        }
                    }).forEach(
                            // then add it to the list
                            object -> {
                                try {
                                    result.getClass().getMethod(LIST_ADD, Object.class)
                                            .invoke(result, object);
                                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                    logger.error(e.getMessage(), e);
                                }
                            }
                    );
                    return result;
                }
            }
        }
        return new ArrayList<>();
    }


    public <T> List<T> transferToListDto(Collection<?> collection, Object secondParam, TransferToListDtoTwoParam<T> method) {
        List<T> result = new ArrayList<>();
        collection.stream().map(each -> method.transferToDto(each, secondParam)).forEach(result::add);
        return result;
    }

    public UserDto transferToUserDto(User user){
        UserDto result = new UserDto();
        modelMapper.map(user,result);
        return result;
    }
    public AccountDto transferToAccountDto(Account account)
    {
        AccountDto result = new AccountDto();
        modelMapper.map(account,result);
        return result;
    }

    public DeviceDto transferToDeviceDto(Device device){
        DeviceDto result = new DeviceDto();
        Set<Account> accounts= device.getAccounts();
        BigDecimal expense = new BigDecimal("0"),
                   income = new BigDecimal("0"),
                   com = new BigDecimal("0");
        Iterator it = accounts.iterator();
        while(it.hasNext())
        {
            Account a = (Account) it.next();
            if(!a.getIsExpense()&& a.getStatus()==1)
                income=income.add(a.getPrice());
            else if(a.getIsExpense()&& a.getStatus()==1)
                expense=expense.add(a.getPrice());
        }
        device.setExpense(expense);
        device.setIncome(income);
        deviceRepository.save(device);
        modelMapper.map(device,result);
        return result;
    }

    public RoleDto transferToRoleDto(Role role)
    {
        RoleDto result = new RoleDto();
        modelMapper.map(role,result);
        return result;
    }


}
