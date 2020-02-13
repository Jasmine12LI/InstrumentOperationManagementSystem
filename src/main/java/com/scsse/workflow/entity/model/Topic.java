package com.scsse.workflow.entity.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "topic")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 话题标题
     */
    @Column
    private String title;

    /**
     * 话题内容
     */
    @Column
    private String content;

    /**
     * 赞数
     */
    @Column(name = "like_count")
    private Integer likeCount;

    /**
     * 回复数
     */
    @Column(name = "reply_count")
    private Integer replyCount;

    /**
     * 作者id
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * 创建时间
     */
	@JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @OneToMany(mappedBy = "topic")
	@JsonBackReference(value = "topic.replys")
    private Set<Reply> replys = new HashSet<>();

    @ManyToMany(mappedBy = "topics")
    @JsonBackReference(value = "topic.likers")
    Set<User> likers = new HashSet<>();

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", replyCount=" + replyCount +
                ", createTime=" + createTime +
                '}';
    }
}