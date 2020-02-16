package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.dto.ActivityDto;
import com.scsse.workflow.entity.dto.RecruitDto;
import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.entity.model.Recruit;
import com.scsse.workflow.entity.model.Tag;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.ActivityRepository;
import com.scsse.workflow.repository.RecruitRepository;
import com.scsse.workflow.repository.TagRepository;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import com.scsse.workflow.util.dao.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:18
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ModelMapper modelMapper;

    private final UserUtil userUtil;

    private final DtoTransferHelper dtoTransferHelper;

    private final ActivityRepository activityRepository;

    private final RecruitRepository recruitRepository;

    private final TagRepository tagRepository;

    @Autowired
    public ActivityServiceImpl(ModelMapper modelMapper, UserUtil userUtil, DtoTransferHelper dtoTransferHelper, ActivityRepository activityRepository, RecruitRepository recruitRepository, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.userUtil = userUtil;
        this.dtoTransferHelper = dtoTransferHelper;
        this.activityRepository = activityRepository;
        this.recruitRepository = recruitRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<ActivityDto> findAllActivity(String type) {
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        return dtoTransferHelper.transferToListDto(list, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));
    }

    @Override
    public List<ActivityDto> findAllExpiredActivity(String type) {
        List<Activity> activities = new ArrayList<>();
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        list.stream()
                // if the time now is greater than the signUpDeadline
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getEndTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) > 0)
                // but less than the activity time
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getActTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) < 0)
                .forEach(activities::add);
        return dtoTransferHelper.transferToListDto(activities, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));
    }

    @Override
    public List<ActivityDto> findAllFinishedActivity(String type) {
        List<Activity> activities = new ArrayList<>();
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        list.stream()
                // if the time now is greater than the activity time
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getActTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) > 0)
                .forEach(activities::add);
        return dtoTransferHelper.transferToListDto(activities, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));

    }

    @Override
    public List<ActivityDto> findAllFreshActivity(String type) {
        List<Activity> activities = new ArrayList<>();
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        list.stream()
                // if the time now is less than the signUpDeadline
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getEndTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) < 0)
                .forEach(activities::add);
        return dtoTransferHelper.transferToListDto(activities, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));

    }

    @Override
    public ActivityDto findActivityById(Integer activityId) {
        return dtoTransferHelper.transferToActivityDto(activityRepository.findOne(activityId),userUtil.getLoginUser());
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        Integer activityId = activity.getId();
        Activity oldActivity = activityRepository.findOne(activityId);
        modelMapper.map(activity, oldActivity);
        return activityRepository.save(oldActivity);
    }

    @Override
    public void deleteActivityById(Integer activityId) {
        activityRepository.delete(activityId);
    }

    @Override
    public List<RecruitDto> findAllRecruitOfActivity(Integer activityId) {
        return dtoTransferHelper.transferToListDto(
                recruitRepository.findAllByActivity_Id(activityId), userUtil.getLoginUser(),
                (firstParam, secondParam) -> dtoTransferHelper.transferToRecruitDto((Recruit) firstParam, (User) secondParam)
        );
    }

    @Override
    public Set<Tag> findAllTagOfActivity(Integer activityId) {
        Activity activity = activityRepository.findOne(activityId);
        return activity.getActivityTags();
    }

    @Override
    public void bindTagToActivity(Integer activityId, Integer tagId) {
        Activity activity = activityRepository.findOne(activityId);
        Tag tag = tagRepository.findByTagId(tagId);
        if (activity != null && tag != null && !activity.getActivityTags().contains(tag)) {
            activity.getActivityTags().add(tag);
            activityRepository.save(activity);
        }

    }

    @Override
    public void unBindTagToActivity(Integer activityId, Integer tagId) {
        Activity activity = activityRepository.findOne(activityId);
        activity.getActivityTags().remove(tagRepository.findByTagId(tagId));
        activityRepository.save(activity);
    }
}
