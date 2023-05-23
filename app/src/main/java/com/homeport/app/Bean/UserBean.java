package com.homeport.app.Bean;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "user")
@Component
public class UserBean {

    private String userId;

    private Integer userType;

    private String password;

    private String username;
}
