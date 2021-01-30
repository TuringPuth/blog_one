package com.wang.service;

import com.wang.dao.UserRepository;
import com.wang.po.User;
import com.wang.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Charlie Puth
 * @version 1.0
 **/

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5.code(password));
        return user;
    }
}
