package com.user.service;

import java.util.List;

import com.user.bean.UserBean;
import com.user.exception.RecordNotFoundException;

public interface UserService {
    UserBean save(UserBean userBean);                // Method to save a user
    UserBean update(UserBean userBean) throws RecordNotFoundException;              // Method to update a user
    void delete(int id) throws RecordNotFoundException;  // Method to delete a user
    UserBean getById(int id);                        // Method to get user by ID
    List<UserBean> getAll();                         // Method to get all users
}
