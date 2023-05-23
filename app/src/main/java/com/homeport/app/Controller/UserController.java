package com.homeport.app.Controller;

import com.homeport.app.Entity.User;
import com.homeport.app.Dao.UserRep;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserRep userRep;

    /**
     *查询所有用户
     * @return
     **/
    @ResponseBody
    @RequestMapping(value = "/getall",method = {RequestMethod.GET,RequestMethod.POST})
    public List<User> getUserList(){
        return userRep.findAll();
    }

    /**查询用户个人信息
     *
     * @param user_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getinfo/{user_id}",method = {RequestMethod.GET,RequestMethod.POST})
    public User getUserInfo (@PathVariable("user_id") String user_id){
        return userRep.getInfo(user_id);
//        return userRep.getInfo(user_id) != null;
    }

    /**密码进行SHA加密
     *
     * @param password
     * @return
     * @throws Exception
     */
    public String getShaPassword(String password)throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] res = md.digest();
        String pswoutput;
        pswoutput = new BigInteger(1,res).toString(16);
        return pswoutput;
    }

    /**注册用户
     *
     * @param user_id
     * @param user_type
     * @param password
     * @param username
     */
    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "/reg",method = {RequestMethod.POST})
    public boolean regUser(@RequestParam("user_id") String user_id,
                           @RequestParam("user_type") Integer user_type,
                           @RequestParam("password") String password,
                           @RequestParam("username") String username) {
        User user = new User();
        user.setUserId(user_id);
        user.setUserType(user_type);
        user.setPassword(getShaPassword(password));
        user.setUsername(username);

        try {
            userRep.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**修改个人密码
     *
     * @param user_id
     * @param newPassword
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword/{userid}/{newPassword}",method = {RequestMethod.GET,RequestMethod.POST})
    public String updatePassword(@PathVariable("userid") String user_id,
                                  @PathVariable("newPassword") String newPassword) throws Exception{
        String psw;
        psw = getShaPassword(newPassword);
        User user = userRep.getInfo(user_id);
        user.setPassword(getShaPassword(psw));
        try {
            userRep.save(user);
        } catch (Exception e) {
            return "Update failed.";
        }
//        user = userRep.getInfo(user_id);
//        System.out.println(user.password);
        return "Updated successfully.";
    }

    /**登录校验
     *
     * @param user_id
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logCheck/{userid}/{password}",method = {RequestMethod.GET,RequestMethod.POST})
    public String logCheck(@PathVariable("userid") String user_id, @PathVariable("password") String password) {

        boolean flag = false;
        User user= new User();

        //空指针防御
        Optional<User> optional = userRep.findById(user_id);
        if (optional != null && optional.isPresent()) {
            user = optional.get();
        }
        if (user != null) {
            if (password.equals(user.getPassword())) {
                flag = true;
            }
        } else {
            return "error";
        }

        if (flag) {
            return "success";
        } else {
            return "error";
        }
    }
}
