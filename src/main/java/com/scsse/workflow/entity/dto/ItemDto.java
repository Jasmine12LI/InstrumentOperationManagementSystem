package com.scsse.workflow.entity.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemDto {

    private Integer id;
    private String  name;
    private Double itemPrice;
    private String des;
}
