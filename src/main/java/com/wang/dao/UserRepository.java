package com.wang.dao;

import com.wang.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsernameAndPassword(String username, String password);
}
