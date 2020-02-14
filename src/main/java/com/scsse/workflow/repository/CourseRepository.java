package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Course;
import com.scsse.workflow.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findAllByLecturer_Id(Integer userId);
    List<Course> findAllByMembersContains(User user);
}