package com.scsse.workflow.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class AccountDto {
    private Integer accountId;
    private BigDecimal price;
    private UserDto submitUser;
    private UserDto checkUser;
    private DeviceDto accountDevice;
    private Date submitTime;
    private Date checkTime;
    private Integer status;
    private  Boolean  isExpense;

}
