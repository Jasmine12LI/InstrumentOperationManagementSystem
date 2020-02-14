package com.scsse.workflow.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "course")
//@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 团队名称（课程名称）
     */
    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "graph_id")
    private Graph graph;
   
    /**
     * 讲师id
     */
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonBackReference(value = "course.members")
    private Set<User> members = new HashSet<>();

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Course(String courseName, Integer courseId, User lecturer, Team team) {
        this.name = courseName;
        this.id = courseId;
        this.lecturer = lecturer;
        this.team = team;
    }

    public Course(String courseName){
        this.name = courseName;
    }
}