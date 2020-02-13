package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:06
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByOpenId(String openid);

//    Optional<User> findById(Integer userId);

    @Query(" select count(f) from UserFollower f where f.followerId = :userId")
    Integer findFollowerNumberByUserId(@Param("userId") Integer userId);

    @Query("select u from User u where u.id in(select f.userId from UserFollower f where f.followerId = :userId)")
    List<User> findUserFollower(@Param("userId") Integer userId);

    void deleteById(Integer userId);

    boolean existsDistinctByOpenId(String openid);
}
