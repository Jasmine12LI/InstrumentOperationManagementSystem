package com.scsse.workflow.entity.dto;

import com.scsse.workflow.entity.model.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Alfred Fu
 * Created on 2019/9/22 6:58 下午
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ActivityDto extends Activity {
    private boolean isFollowed;

    public ActivityDto(String activityName, Date activityTime, String activityPlace, String activityDescription, Date activitySignUpDeadline) {
        super(activityName, activityTime, activityPlace, activityDescription, activitySignUpDeadline);
    }

    public ActivityDto(String activityName) {
        super(activityName);
    }
}
