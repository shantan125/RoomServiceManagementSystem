package com.user.seviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.bean.UserBean;
import com.user.entity.User;
import com.user.exception.RecordNotFoundException;
import com.user.repository.UserRepository;
import com.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserBean save(UserBean userBean) {
        logger.info("Saving user with details: {}", userBean);

        User user = new User(userBean.getId(), userBean.getName(), userBean.getPassword(), 
                             userBean.getEmail(), userBean.getMobileNo(), userBean.getAddress());
        user = userRepository.save(user);

        logger.info("User saved successfully with ID: {}", user.getId());
        

        return new UserBean(user.getId(), user.getName(), user.getPassword(), 
                            user.getEmail(), user.getMobileNo(), user.getAddress());
    }


    @Override
    public UserBean update(UserBean userBean) throws RecordNotFoundException {
        logger.info("Updating user with ID: {}", userBean.getId());

        User user = userRepository.findById(userBean.getId())
                                  .orElseThrow(() -> {
                                      logger.error("User not found with ID: {}", userBean.getId());
                                      return new RecordNotFoundException("User not found");
                                  });

        logger.info("User found with ID: {}. Updating details.", userBean.getId());
        user.setName(userBean.getName());
        user.setPassword(userBean.getPassword());
        user.setEmail(userBean.getEmail());
        user.setMobileNo(userBean.getMobileNo());
        user.setAddress(userBean.getAddress());

        user = userRepository.save(user);
        logger.info("User updated successfully with ID: {}", user.getId());

        return new UserBean(user.getId(), user.getName(), user.getPassword(),
                            user.getEmail(), user.getMobileNo(), user.getAddress());
    }


    @Override
    public void delete(int id) throws RecordNotFoundException {
        logger.info("Attempting to delete user with ID: {}", id);

        User user = userRepository.findById(id)
                                  .orElseThrow(() -> {
                                      logger.error("User not found with ID: {}", id);
                                      return new RecordNotFoundException("User not found");
                                  });

        userRepository.delete(user);
        logger.info("User successfully deleted with ID: {}", id);
    }


    @Override
    public UserBean getById(int id) {
        logger.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                                  .orElseThrow(() -> {
                                      logger.error("User not found with ID: {}", id);
                                      return new RecordNotFoundException("User not found with ID: " + id);
                                  });

        logger.info("User successfully fetched with ID: {}", id);
        return new UserBean(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getMobileNo(), user.getAddress());
    }


    @Override
    public List<UserBean> getAll() {
        logger.info("Fetching all users");

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            logger.warn("No users found in the database");
        } else {
            logger.info("Successfully fetched {} users", users.size());
        }

        return users.stream()
                    .map(user -> new UserBean(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getMobileNo(), user.getAddress()))
                    .collect(Collectors.toList());
    }

}
