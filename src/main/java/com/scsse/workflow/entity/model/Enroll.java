package com.scsse.workflow.entity.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "enroll")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Enroll {
    /**
     * 报名号
     */
    @Id
    @Column(name = "enroll_id")
    private String enrollId;

    /**
     * 报名用户id
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    /**
     * 活动id
     */
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    /**
     * 报名时间
     */
    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "sign_up_time")
    private Date signUpTime;

    /**
     * 比赛类型：个人赛（0）、团队赛（1）
     */
    @Column(name = "quantity_type")
    private Boolean quantityType;

    /**
     * 成功/失败
     */
    @Column
    private Byte status;

   
}