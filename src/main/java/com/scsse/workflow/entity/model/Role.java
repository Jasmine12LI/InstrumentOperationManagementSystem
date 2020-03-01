package com.scsse.workflow.entity.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name ="role")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private  String name;

    //角色状态 有效1 无效0
    @Column
    private Boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference(value = "role.access")
    @JoinTable(name="role_access",joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name ="access_id"))
    private Set<Access> access = new HashSet<>();


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
