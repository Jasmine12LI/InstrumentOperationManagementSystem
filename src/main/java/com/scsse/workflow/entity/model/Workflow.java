package com.scsse.workflow.entity.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "workflow")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
    private Integer id;

    /**
     * 工作进展
     */
	@Column
    private String content;

    /**
     * 创建时间
     */
	@JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime = new Date();

    /**
     * 团队id
     */
	@ManyToOne
	@JoinColumn(name="team_id")
    private Team team;

    /**
     * 创建该条工作流的用户id
     */
	@ManyToOne
	@JoinColumn(name="creator_id")
    private User creator;
	

    /**
     * 根据Task生成ActionRecord {完成}
     *
     * @param user triggerUser
     * @param team 保存ActionRecord的Team
     * @param task 状态为完成的task
     * @return ActionRecord
     */
//    public static ActionRecord generateTaskFinishedRecord(User user, Team team, Vector task) {
//        final String SPLITTER = " ";
//        final String SUCCESS_STATUS = "成功";
//        
//        ActionRecord actionRecord = new ActionRecord();
//        actionRecord.setCreator(user);
//        actionRecord.setTeam(team);
//        actionRecord.setContent(
//                new SimpleDateFormat("hh:mm").format(new Date()) + SPLITTER +
//                        user.getName() + SPLITTER +
//                        task.getVectorName() + SPLITTER +
//                        SUCCESS_STATUS
//        );
//
//        return actionRecord;
//    }

}