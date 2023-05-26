package com.homeport.app.Controller;

import com.homeport.app.Dao.MessageRep;
import com.homeport.app.Entity.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    public MessageRep messageRep;


    /**
     * 发消息
     */
    @ResponseBody
    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    public Boolean send(@RequestParam("sender_id") String sender_id,
                        @RequestParam("receiver_id") String receiver_id,
                        @RequestParam("message_type") Integer message_type,
                        @RequestParam("message_content") String message_content,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        Message message = new Message();
        message.setSenderId(sender_id);
        message.setReceiverId(receiver_id);
        message.setMessageType(message_type);
        message.setMessageContent(message_content);
        try {
            messageRep.save(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
