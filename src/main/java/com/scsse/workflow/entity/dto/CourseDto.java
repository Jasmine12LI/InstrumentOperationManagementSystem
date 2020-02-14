package com.scsse.workflow.entity.dto;

import com.scsse.workflow.entity.model.Course;
import com.scsse.workflow.entity.model.Team;
import com.scsse.workflow.entity.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class CourseDto extends Course {
    private boolean isFollowed;

    public CourseDto(String courseName, Integer courseId, User lecturer, Team team) {
        super(courseName,courseId,lecturer,team);
    }

    public CourseDto(String courseName) {
        super(courseName);
    }
}
