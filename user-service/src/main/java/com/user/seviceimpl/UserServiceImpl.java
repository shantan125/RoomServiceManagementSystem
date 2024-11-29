package com.user.seviceimpl;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public UserBean save(UserBean userBean) {
        User user = new User(userBean.getId(), userBean.getName(), userBean.getPassword(), userBean.getEmail(),
                             userBean.getMobileNo(), userBean.getAddress());
        user = userRepository.save(user);
        return new UserBean(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getMobileNo(), user.getAddress());
    }

    @Override
    public UserBean update(UserBean userBean) throws RecordNotFoundException {
        User user = userRepository.findById(userBean.getId())
                                  .orElseThrow(() -> new RecordNotFoundException("User not found"));
        user.setName(userBean.getName());
        user.setPassword(userBean.getPassword());
        user.setEmail(userBean.getEmail());
        user.setMobileNo(userBean.getMobileNo());
        user.setAddress(userBean.getAddress());
        user = userRepository.save(user);
        return new UserBean(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getMobileNo(), user.getAddress());
    }

    @Override
    public void delete(int id) throws RecordNotFoundException {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RecordNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserBean getById(int id) {
        User user = null;
		try {
			user = userRepository.findById(id)
			                          .orElseThrow(() -> new RecordNotFoundException("User not found"));
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new UserBean(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getMobileNo(), user.getAddress());
    }

    @Override
    public List<UserBean> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(user -> new UserBean(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getMobileNo(), user.getAddress()))
                    .collect(Collectors.toList());
    }
}
