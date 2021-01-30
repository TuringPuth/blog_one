package com.wang.service;

import com.wang.po.User;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
public interface UserService {

    User checkUser(String username, String password);

}
