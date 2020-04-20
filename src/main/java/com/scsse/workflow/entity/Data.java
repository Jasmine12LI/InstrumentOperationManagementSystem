package com.scsse.workflow.entity;

import java.math.BigDecimal;

@lombok.Data
public class Data {
    private String id;
    private Double meanMrea; //平均面积
    private Double slope; // 斜率
    private Double density;//浓度;

    public Data(String id, Double meanMrea){
        this.id = id;
        this.meanMrea = meanMrea;
    }


}
