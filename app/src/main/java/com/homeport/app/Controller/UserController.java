package com.homeport.app.Controller;

import com.homeport.app.Dao.MessageRep;
import com.homeport.app.Dao.UserRep;
import com.homeport.app.Entity.Message;
import com.homeport.app.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    public MessageRep messageRep;

    /**
     *查询所有用户
     * @return
     **/
    @ResponseBody
    @RequestMapping(value = "/getall",method = {RequestMethod.GET})
    public List<User> getUserList(){
        return userRep.findAll();
    }

    /**查询用户个人信息
     *
     * @param user_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getinfo/{user_id}",method = {RequestMethod.GET})
    public User getUserInfo(@PathVariable("user_id") String user_id){
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
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword/{user_id}/{newPassword}",method = {RequestMethod.POST})
    public String updatePassword(@PathVariable("user_id") String user_id,
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
     */
    @ResponseBody
    @RequestMapping(value = "/logCheck/{user_id}/{password}",method = {RequestMethod.POST})
    public String logCheck(@PathVariable("user_id") String user_id, @PathVariable("password") String password) {

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

    /**充值
     *
     * @param user_id
     * @param amount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/charge",method = {RequestMethod.POST})
    public Boolean charge(@RequestParam("user_id") String user_id, @RequestParam("amount") Double amount, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("user_id", user_id);
            request.setAttribute("amount", amount);
            request.getRequestDispatcher("/bounty/charge").forward(request,response);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**发消息（目前仅文字）
     *
     * @param receiver_id
     * @param sender_id
     * @param message_type
     * @param message_content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/send",method = {RequestMethod.POST})
    public Boolean send(@RequestParam("sender_id") String sender_id,
                        @RequestParam("receiver_id") String receiver_id,
                        @RequestParam("message_type") Integer message_type,
                        @RequestParam("message_content") String message_content,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        try {
            request.setAttribute("sender_id", sender_id);
            request.setAttribute("receiver_id", receiver_id);
            request.setAttribute("message_type", message_type);
            request.setAttribute("message_content", message_content);
            request.getRequestDispatcher("/message/send").forward(request,response);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**查询自身消息
     *
     * @param user_id
     */
    @ResponseBody
    @RequestMapping(value = "/loadallmessage",method = {RequestMethod.GET})
    public List<Message> loadMessage(@RequestParam("user_id") String user_id) {
            return messageRep.loadAll(user_id);
    }
}
