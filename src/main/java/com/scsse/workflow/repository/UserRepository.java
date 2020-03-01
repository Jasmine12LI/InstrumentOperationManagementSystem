package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository  extends JpaRepository<User, Integer> {
    User findById(Integer id);
    void deleteById(Integer userId);

@Query(value = "select * from User  u where u.name = ?1",nativeQuery = true)
   public User findName ( String name);

}
