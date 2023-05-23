package com.homeport.app.Dao;

import com.homeport.app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRep extends JpaRepository<User,String> {

    /**用户登录
     *
     * @param user_id
     * @param password
     */
    @Query(value = "select" +
            "           user_id, user_type, username" +
            "       from " +
            "           homeport.user " +
            "       where " +
            "           user_id=?1 and password=?2",
            nativeQuery = true)
    Boolean logCheck(String user_id, String password);

    /**查看用户个人信息
     *
     * @param user_id
     */
    @Query(value = "select " +
            "           user_id, user_type, username, password" +
            "       from" +
            "           homeport.user" +
            "       where " +
            "           user_id = :user_id",
            nativeQuery = true)
    User getInfo(String user_id);

}
