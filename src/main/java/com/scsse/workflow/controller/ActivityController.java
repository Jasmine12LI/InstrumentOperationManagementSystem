package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alfred Fu
 * Created on 2019-03-07 20:48
 */

@RestController
public class ActivityController {

    private final ActivityService activityService;

    private final UserUtil userUtil;

    @Autowired
    public ActivityController(ActivityService activityService, UserUtil userUtil) {
        this.activityService = activityService;
        this.userUtil = userUtil;
    }

    /**
     * 获取所有活动
     *
     * @param type {fresh,expire,finish}
     *             fresh：未到报名截止时间
     *             expire：超过报名截止时间，但活动尚未开始
     *             finish：活动已经结束
     * @return List{ActivityDto}
     */
    @GetMapping("/activity/all")
    public Result findAllActivity(@RequestParam(name = "type", required = false, defaultValue = "normal") String type) {
        switch (type) {
            case "fresh":
                return ResultUtil.success(activityService.findAllFreshActivity());
            case "expire":
                return ResultUtil.success(activityService.findAllExpiredActivity());
            case "finish":
                return ResultUtil.success(activityService.findAllFinishedActivity());
            default:
                return ResultUtil.success(activityService.findAllActivity());
        }

    }

    /**
     * 获取关于该活动的所有招聘
     * @param  activityId
     * @return List{RecruitDto}
     */
    @GetMapping("/activity/{activityId}/recruit")
    public Result findRecruitOfActivity(@PathVariable Integer activityId) {
        return ResultUtil.success(activityService.findAllRecruitOfActivity(activityId));
    }

    /**
     * 获取某个活动的具体信息
     *
     * @param activityId 活动Id
     * @return ActivityDto
     * <p>
     * e.g.
     * GET /activity/1
     */
    @GetMapping("/activity/{activityId}")
    public Result getActivityDetail(@PathVariable Integer activityId) {
        return ResultUtil.success(activityService.findActivityById(activityId));
    }

    /**
     * 创建一个活动
     *
     * @param activity 活动
     * @return Activity
     * 例:
     * url:
     * /recruit
     * @see Activity
     */
    @PostMapping("/activity")
    public Result createOneActivity(@RequestBody Activity activity) {
        activity.setPromoter(userUtil.getLoginUser());
        activity.setQuantityType(true);
        return ResultUtil.success(activityService.createActivity(activity));
    }

    @PutMapping("/activity/{activityId}")
    public Result updateOneActivity(@RequestBody Activity activity, @PathVariable Integer activityId) {
        activity.setId(activityId);
        return ResultUtil.success(activityService.updateActivity(activity));
    }

    @DeleteMapping("/activity/{activityId}")
    public Result deleteOneActivity(@PathVariable Integer activityId) {
        activityService.deleteActivityById(activityId);
        return ResultUtil.success();
    }
}
