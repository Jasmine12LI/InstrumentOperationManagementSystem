package com.scsse.workflow.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class DeviceDto {
    private Integer deviceId;
    private String  deviceName;
    private Double deviceExpense;
}
