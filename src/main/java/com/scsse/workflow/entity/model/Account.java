package com.scsse.workflow.entity.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name ="account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    //负数表示支出， 证书表示受益
    @Column(scale = 2)
    private Double price;

    @Column
    private String des; //审核意见

    @Column
    private String summary;

   //0 未审核 1 审核通过 2审核未通过
   @NotNull
    @Column(columnDefinition = "int default 0",nullable = false)
    private Integer status;


    @ManyToOne
    @JoinColumn(name = "submit_id")
    private User submitUser;

    @ManyToOne
    @JoinColumn(name = "checkUser_id")
    private User checkUser;


    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "submit_time")
    private Date submitTime;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "check_time")
    private Date checkTime;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private  Device device;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    @JsonBackReference(value = "account.items")
    private Set<Item> items = new HashSet<>();

    @Override
    public int hashCode(){
        return Objects.hash(getId(),getPrice(),getCheckTime(),getDes(),
                getStatus(),getSubmitTime());
    }

    @Override
    public String toString(){
        return "account{"+
                "id="+id+
                ",price="+price+
                '}';

    }

}
