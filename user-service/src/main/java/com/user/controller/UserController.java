package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.bean.UserBean;
import com.user.exception.RecordNotFoundException;
import com.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Save a new user
    @PostMapping("/save")
    public ResponseEntity<UserBean> saveUser(@RequestBody UserBean userBean) {
        UserBean savedUser = userService.save(userBean);
        return ResponseEntity.ok(savedUser);
    }

    // Update an existing user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserBean> updateUser(@PathVariable int id, @RequestBody UserBean userBean) {
        userBean.setId(id);
        UserBean updatedUser = null;
		try {
			updatedUser = userService.update(userBean);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Get a user by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<UserBean> getUser(@PathVariable int id) {
        UserBean user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // Get all users
    @GetMapping("/getAll")
    public ResponseEntity<List<UserBean>> getAllUsers() {
        List<UserBean> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
}
