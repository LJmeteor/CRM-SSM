package com.lj.crm.settings.web.controller;

import com.lj.crm.commons.constant.Constant;
import com.lj.crm.commons.domain.ReturnObject;
import com.lj.crm.commons.utils.DataUtils;
import com.lj.crm.settings.domain.User;
import com.lj.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }
    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody
    Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println(loginAct);
        Map<String,Object> map= new HashMap<String, Object>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userService.queryUserByLoginAndPwd(map);
        ReturnObject returnObject=new ReturnObject();
        if(user ==null){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
            returnObject.setMessage("Incorrect account or password!");
        }else {

            String now= DataUtils.formatDateTime(new Date());
            if(now.compareTo(user.getExpireTime())>0){
//                    if now is greater than ExpireTime : refuse to log in
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
                returnObject.setMessage("Account is expired!");
            }else if ("0".equals(user.getLockState())){
//                     State ="0" : user frozen
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
                returnObject.setMessage("Account is banned");
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
//                Ip address check
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAILURE);
                returnObject.setMessage("IP address is invalid!");

            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                session.setAttribute(Constant.SESSION_USER,user);
                // whether save cookie
//                if(isRemPwd.equals("true")){
//                    Cookie c1=new Cookie("loginAct",user.getLoginAct());
//                    c1.setMaxAge(10*24*60*60);
//                    response.addCookie(c1);
//                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
//                    c2.setMaxAge(10*24*60*60);
//                    response.addCookie(c2);
//                }else {
//                    Cookie c1=new Cookie("loginAct","1");
//                    c1.setMaxAge(0);
//                    response.addCookie(c1);
//                    Cookie c2=new Cookie("loginPwd","2");
//                    c2.setMaxAge(0);
//                    response.addCookie(c2);
//                }
            }
        }
        return returnObject;
    }
    @RequestMapping("/settings/qx/user/logout.do")
    public String logOut(HttpServletResponse response,HttpSession session){
           session.invalidate();
           return "redirect:/";

    }
}
