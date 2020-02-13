package com.scsse.workflow.entity.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@IdClass(UserFollower.UserFollowerMultiKeysClass.class)
@Table(name = "user_follower")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})

public class UserFollower {
    /**
     * 被关注的用户id
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 粉丝id
     */
    @Id
    @Column(name = "follower_id")
    private Integer followerId;


    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    class UserFollowerMultiKeysClass implements Serializable {
        Integer userId;
        Integer followerId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserFollowerMultiKeysClass)) return false;
            UserFollowerMultiKeysClass that = (UserFollowerMultiKeysClass) o;
            return getUserId().equals(that.getUserId()) &&
                    getFollowerId().equals(that.getFollowerId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getUserId(), getFollowerId());
        }
    }
}