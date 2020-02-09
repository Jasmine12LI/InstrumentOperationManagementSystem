package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Team;
import com.scsse.workflow.service.TeamService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    private final UserUtil userUtil;

    private final TeamService teamService;

    private final UserService userService;

    @Autowired
    public CourseController(UserUtil userUtil, TeamService teamService, UserService userService) {
        this.userUtil = userUtil;
        this.teamService = teamService;
        this.userService = userService;
    }

    /**
     * 获取我加入的课程
     *
     * @return List{CourseDto}
     */
    @GetMapping("/course/joinedCourse")
    public Result getJoinCourse() {
        return ResultUtil.success(
                userService.findJoinedCourse(userUtil.getLoginUser()));
    }

    /**
     * 获取课程学生
     *
     * @param courseId courseId
     * @return List{User}
     */
    @GetMapping("/course/{courseId}/members")
    public Result getColleague(@PathVariable Integer courseId) {
        return ResultUtil.success(
                teamService.getTeamMembers(courseId)
        );
    }

}
