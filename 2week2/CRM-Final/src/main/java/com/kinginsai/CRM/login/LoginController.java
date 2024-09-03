package com.kinginsai.CRM.login;

import com.kinginsai.CRM.entity.Users;
import com.kinginsai.CRM.entity.Usersdata;
import com.kinginsai.CRM.entity.Usersinfo;
import com.kinginsai.CRM.intercepertor.Session;
import com.kinginsai.CRM.utils.MD5util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersinfoRepository usersinfoRepository;

    @Autowired
    private UsersdataRepository usersdataRepository;

    public static Map<String, Session> sessionMap = new HashMap<String, Session>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/registry")
    public ModelAndView registry(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registry");
        return mv;
    }

    @PostMapping("/verifyRegistry")
    public ModelAndView verifyRegistry(
        @RequestParam(value = "usersName", required = true) String usersName,
        @RequestParam(value = "usersPassword", required = true) String usersPassword,
        @RequestParam(value = "usersInfo", required = true) String usersInfo
    ){
        List<Users> users = usersRepository.findByUsersNameIs(usersName);
        if(users != null && users.size() > 0) {
            System.out.println("用户名重复");
        } else {
            Users users1 = new Users();
            users1.setUsersName(usersName);
            users1.setUsersPassword(MD5util.code(usersPassword));
            usersRepository.save(users1);

            Usersinfo info = new Usersinfo();
            info.setUsersName(usersName);
            info.setUsersInfo(usersInfo);
            usersinfoRepository.save(info);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/login");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("usersName");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "usersName") String usersName){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        List<Usersdata> usersdatas = usersdataRepository.findByUsersName(usersName);
        mv.addObject("usersdatas", usersdatas);
        return mv;
    }

    @PostMapping("/verify")
    public ModelAndView verify(@RequestParam(value = "usersName", required = true) String usersName,
                         @RequestParam(value = "usersPassword", required = true) String usersPassword,
                         HttpServletRequest request){
        log.info("用户登陆：{}", usersName);
        List<Users> users = this.usersRepository.findByUsersNameIs(usersName);
        if(users != null && users.size() > 0) {
            String passwordMD5 = MD5util.code(usersPassword);
            if(passwordMD5.equals(users.get(0).getUsersPassword())){
                Session localSession = new Session();
                localSession.setCreateTime(new Date());
                localSession.setMaxInactiveInterval(30*60);
                localSession.setSessionId(UUID.randomUUID().toString());
                localSession.setUserName(usersName);
                LoginController.sessionMap.put(localSession.getSessionId(), localSession);

                HttpSession session = request.getSession();
                session.setAttribute("sessionId", localSession.getSessionId());
                session.setAttribute("usersName", usersName);
                session.setMaxInactiveInterval(30*60);
                log.info("    用户登陆成功：{}", usersName);
                return new ModelAndView("redirect:/index?usersName="+usersName);
            }
        }
        return new ModelAndView("redirect:/login");
    }

}
