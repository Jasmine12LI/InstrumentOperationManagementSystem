package com.scsse.workflow.entity.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "team")
//@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 团队名称
     */
    @Column
    private String name;

//    @OneToOne
//    @JoinColumn(name = "graph_id")
//    private Graph graph;
   
    /**
     * 队长id
     */
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private User leader;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
    
    @ManyToMany
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonBackReference(value = "team.members")
    private Set<User> members = new HashSet<>();
  
    @OneToMany(mappedBy = "team")
    @JsonBackReference(value = "team.recruits")
    private Set<Recruit> recruits = new HashSet<>();
    
    @Column(name = "enroll_id")
    private String enroll_id;
    
}