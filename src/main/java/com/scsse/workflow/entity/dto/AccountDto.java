package com.scsse.workflow.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class AccountDto {
    private Integer accountId;
    private Double price;
    private UserDto submitUser;
    private UserDto checkUser;
    private DeviceDto accountDevice;
    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date submitTime;
    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date checkTime;
    private String des;
    private String summary;
    private Integer status;
    private Set<ItemDto> items;
}
