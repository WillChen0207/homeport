package com.homeport.app.Dao;

import com.homeport.app.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRep extends JpaRepository<Message,Integer> {

    /**查看指定两人私信
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
    List<Message> load(String receiver_id, String sender_id);

    /**查看自身全部私信
     *
     * @param user_id
     * @return
     */
    @Query(value = "select " +
            "           message_id, sender_id, receiver_id, message_type, message_content" +
            "       from" +
            "           homeport.message" +
            "       where " +
            "           (sender_id = :user_id OR receiver_id = :user_id)",
            nativeQuery = true)
    List<Message> loadAll(String user_id);
}
