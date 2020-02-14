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
    private String courseName;
}
