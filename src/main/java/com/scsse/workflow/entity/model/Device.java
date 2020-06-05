package com.scsse.workflow.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name ="device")
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(scale = 2)
    private Double expense;

    @OneToMany(mappedBy = "device")
    @JsonBackReference(value = "device.accounts")
    private Set<Account> accounts = new HashSet<>();

    @Override
    public int hashCode(){
        return Objects.hash(getId(),getExpense(),getName());
    }

    @Override
    public String toString(){
        return "Device{"+
                "id="+id+
                ",name='"+name+'\''+
                ",expense="+ expense +
                "}";
    }





}
