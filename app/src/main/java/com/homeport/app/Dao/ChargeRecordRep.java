package com.homeport.app.Dao;

import com.homeport.app.Entity.ChargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRecordRep extends JpaRepository<ChargeRecord,Integer> {

//    Boolean addRecord(ChargeRecord chargeRecord);
}
