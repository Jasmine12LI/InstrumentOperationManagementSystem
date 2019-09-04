package com.scsse.workflow.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019-01-27 10:42
 */


@Getter
@Setter
@ToString(exclude = {"followRecruits","successRecruits","applyRecruits"})
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer userId;
    @Column
    private String username;
    @Column
    private String userNumber;
    @Column
    private String userGrade;
    @Column
    private String userPhone;
    @Column
    private String userEmail;
    @Column
    private String userSpecialty;
    @Column
    private String userResume;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference(value = "user.userTags")
    @JoinTable(name = "user_tag",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> userTags = new HashSet<>();


    @ManyToMany
    @JsonBackReference(value = "user.followRecruits")
    @JoinTable(name = "user_recruit_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
    private Set<Recruit> followRecruits = new HashSet<>();

    @ManyToMany
    @JsonBackReference(value = "user.applyRecruits")
    @JoinTable(name = "user_recruit_applicant",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
    private Set<Recruit> applyRecruits = new HashSet<>();


    @ManyToMany(mappedBy = "members")
    @JsonBackReference(value = "user.successRecruits")
    private Set<Recruit> successRecruits = new HashSet<>();

    public User(String username, String userNumber, String userGrade, String userPhone, String userEmail, String userSpecialty, String userResume) {
        this.username = username;
        this.userNumber = userNumber;
        this.userGrade = userGrade;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userSpecialty = userSpecialty;
        this.userResume = userResume;
    }
}
