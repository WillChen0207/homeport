package com.homeport.app.Controller;

import com.homeport.app.Dao.BountyRep;
import com.homeport.app.Dao.ChargeRecordRep;
import com.homeport.app.Entity.Bounty;
import com.homeport.app.Entity.ChargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@CrossOrigin
@Controller
@RequestMapping("/bounty")
public class BountyController {

    @Autowired
    public BountyRep bountyRep;

    @Autowired
    public ChargeRecordRep chargeRecordRep;

    @ResponseBody
    @RequestMapping(value = "/charge", method = {RequestMethod.POST})
    public Boolean charge(@RequestParam("user_id") String user_id, @RequestParam("amount") Double amount) {
        Bounty bounty = bountyRep.getInfo(user_id);
        ChargeRecord chargeRecord = new ChargeRecord(user_id, amount, Instant.now());
        Double balance = bounty.getBalance();
        balance += amount;
        bounty.setBalance(balance);
        try {
            bountyRep.save(bounty);
            chargeRecordRep.save(chargeRecord);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
