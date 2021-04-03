package com.example.ki_shaan;

import java.util.HashMap;

public class UserInfo {
    String firstname, lastname, mobile, email, role, groupname;

    public UserInfo(String firstname, String lastname, String mobile, String email, String role, String groupname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.email = email;
        this.role = role;
        this.groupname = groupname;
    }
    UserInfo()
    {}
    public HashMap<String, Object> getHashMap()
    {
        HashMap<String,Object> newUser=new HashMap<>();
        newUser.put("firstname",firstname);
        newUser.put("lastname",lastname);
        newUser.put("mobile",mobile);
        newUser.put("email",email);
        newUser.put("role",role);
        newUser.put("groupname",groupname);
        return  newUser;
    }
}
