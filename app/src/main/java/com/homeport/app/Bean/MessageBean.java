package com.homeport.app.Bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "message")
@Component
public class MessageBean {
    private Integer message_id;

    private String sender_id;

    private String receiver_id;

    private Integer message_type;

    private String message_content;
}
