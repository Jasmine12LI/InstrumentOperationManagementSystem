package com.scsse.workflow.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {
    private Integer userId;
    private String userName;
    private String userPhone;
    private Set<RoleDto> userRoles;

}
