package com.jyhd.black.controller;

import com.jyhd.black.config.WebSecurityConfig;
import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.Result;
import com.jyhd.black.service.AdminService;
import com.jyhd.black.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin/")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping(value = "login")
    public String login(){
        return "admin/login";
    }

    @PostMapping(value = "loginPost")
    @ResponseBody
    public Result postLogin(@RequestParam("userName") String userName , @RequestParam("password") String password , HttpSession session){
        return adminService.loginPost(userName,password,session);
    }

    @GetMapping(value = "logOut")
    public String logOut(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/admin/login";
    }

    @GetMapping(value = "index")
    public String index(
            @SessionAttribute(WebSecurityConfig.SESSION_KEY) String userName,
            @RequestParam(defaultValue = "") String userId ,
            @RequestParam(defaultValue = "0") Integer page ,
            Model model){

        Integer size = 2;
        Sort sort = new Sort(Sort.Direction.DESC,"time");
        Pageable pageable = new PageRequest(page,size,sort);
        Page<ActivityData> pageActivity = adminService.activityAll(userId,pageable);

        model.addAttribute("page",page);
        model.addAttribute("size",pageActivity.getTotalPages()); //总页数 pageActivity.getTotalElements() 总条数
        model.addAttribute("userName",userName);
        model.addAttribute("activityAll",pageActivity);
        return "admin/index";
    }

    @GetMapping(value = "sum")
    public String sum(Model model){

        model.addAttribute("adminService",adminService.activitySum());
        model.addAttribute("activityPaySum",adminService.activityPaySum());
        model.addAttribute("activityNotPaySum",adminService.activityNotPaySum());
        return "admin/sum";
    }

    @GetMapping(value = "active")
    public String active(@RequestParam(value = "date" , defaultValue = "date") String date , Model model){
        model.addAttribute("date",date.equals("date") ? CommonUtil.getDate() : date);
        model.addAttribute("active",adminService.active(date));
        return "admin/active";
    }


}
