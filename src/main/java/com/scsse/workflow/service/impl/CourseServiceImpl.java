package com.scsse.workflow.service.impl;

import com.scsse.workflow.constant.ErrorMessage;
import com.scsse.workflow.entity.dto.CourseDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Course;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.repository.CourseRepository;
import com.scsse.workflow.service.CourseService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import com.scsse.workflow.util.dao.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private final DtoTransferHelper dtoTransferHelper;

    private final UserUtil userUtil;

    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, DtoTransferHelper dtoTransferHelper,
                           UserUtil userUtil, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.dtoTransferHelper = dtoTransferHelper;
        this.userUtil = userUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto getCourse(Integer courseId) {
        Course result = courseRepository.findOne(courseId);
        return dtoTransferHelper.transferToCourseDto(result);

//        return result.map.map(dtoTransferHelper::transferToCourseDto).orElse(null);
    }

    @Override
    public CourseDto createCourse(Course course) {
        User loginUser = userUtil.getLoginUser();
        course.setLecturer(loginUser);
        course.getStudents().add(loginUser);
        return dtoTransferHelper.transferToCourseDto(courseRepository.save(course));
    }

    @Override
    public CourseDto updateCourse(Course course) throws Exception {
        Course result = courseRepository.findOne(course.getId());
        if (result!=null) {
            Course oldCourse = result;
            modelMapper.map(course, oldCourse);
            return dtoTransferHelper.transferToCourseDto(courseRepository.save(oldCourse));
        } else {
            throw new WrongUsageException(ErrorMessage.UPDATE_ENTITY_NOT_FOUND);
        }
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseRepository.delete(courseId);
    }

    @Override
    public List<UserDto> getCourseMembers(Integer courseId) {
//        return null;
        Course result = courseRepository.findOne(courseId);
        if (result!=null) {
            return dtoTransferHelper.transferToListDto(
                    result.getStudents(), user -> dtoTransferHelper.transferToUserDto((User) user)
            );
        } else
            return new ArrayList<>();
    }
}
