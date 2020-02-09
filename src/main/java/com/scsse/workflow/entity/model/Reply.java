package com.scsse.workflow.entity.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "reply")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 话题id
     */
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    /**
     * 回复内容
     */
    @Column
    private String content;

    /**
     * 评论者id
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * 创建日期
     */
	@JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 被回复的reply_id
     */
    @OneToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @JoinColumn(name = "replyee_id")
    private Reply replyee;

    @ManyToMany(mappedBy = "replys")
    @JsonBackReference(value = "reply.likers")
  	Set<User> likers = new HashSet<>();
      
  
}