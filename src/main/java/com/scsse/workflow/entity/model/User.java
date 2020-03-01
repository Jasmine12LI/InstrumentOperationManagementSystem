package com.scsse.workflow.entity.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name ="user")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private  String password;

    @Column
    private String salt;//加密密码的盐

    @Column
    private String phone;
    //0表示账户锁定，1表示账户正常
    @Column
    private Integer state;
    public User(String username, String userPassword)
    {
        this.name =  username;
        this.password= userPassword;
    }


//提交的账单
    @OneToMany(mappedBy = "submitUser",fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.accounts")
    private Set<Account> accounts = new HashSet<>();

    //审核的账单
    @OneToMany(mappedBy = "checkUser",fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.accounts")
    private Set<Account> checkAccounts = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.roles")
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(),user.getName());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getId(),getName());
    }

    @Override
    public String toString(){
        return "User{"+
                "id="+id+
                ", name =' "+ name + '\''+
                '}';
    }

}
