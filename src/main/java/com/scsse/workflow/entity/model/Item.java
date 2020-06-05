package com.scsse.workflow.entity.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name ="item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String des;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;


    @Override
    public int hashCode(){
        return Objects.hash(getId(),getPrice(),getDes(),
                getName());
    }

    @Override
    public String toString(){
        return "accountItem{"+
                "id="+id+
                ",name="+name+
                ",price="+price+
                ",des="+des+
                '}';

    }

}
