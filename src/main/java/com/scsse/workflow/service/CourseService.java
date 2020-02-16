package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.CourseDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Course;

import java.util.List;

public interface CourseService {
    CourseDto getCourse(Integer CourseId);

    List<Course> getAllCourse();

    CourseDto createCourse(Course course);

    CourseDto updateCourse(Course course) throws Exception;

    void deleteCourse(Integer CourseId);

    List<UserDto> getCourseMembers(Integer CourseId);
}
