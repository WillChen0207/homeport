package com.homeport.app.Dao;

import com.homeport.app.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRep extends JpaRepository<Message,Integer> {
    /**查看私信
     *
     * @param receiver_id
     * @param sender_id
     * @return
     */
    @Query(value = "select " +
            "           message_id, sender_id, receiver_id, message_type, message_content" +
            "       from" +
            "           homeport.message" +
            "       where " +
            "           (sender_id = ?1 AND receiver_id = ?2)" +
            "       or " +
            "           (sender_id = ?2 AND receiver_id = ?1)" ,
            nativeQuery = true)
    List<Message> checkMessage(String receiver_id, String sender_id);
}
