package com.user.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {
    private int id;
    private String name;
    private String password;
    private String email;
    private String mobileNo;
    private String address;
    private String role;

}
