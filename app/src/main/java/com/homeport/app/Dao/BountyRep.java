package com.homeport.app.Dao;

import com.homeport.app.Entity.Bounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BountyRep extends JpaRepository<Bounty, String> {
    @Query(value = "select " +
            "           user_id, balance" +
            "       from " +
            "           homeport.bounty" +
            "       where" +
            "           user_id = :user_id",
            nativeQuery = true)
    Bounty getInfo(String user_id);
}
