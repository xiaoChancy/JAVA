package com.jyhd.black.controller;

import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.User;
import com.jyhd.black.domain.RankingList;
import com.jyhd.black.service.IndexService;
import com.jyhd.black.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/index/")
public class IndexController {

    @Autowired
    public IndexService indexService;
    private HttpClient client;

    /**
     * 登录签名验证
     * @param response
     * @param request
     * @param session
     * @throws IOException
     */
    @GetMapping(value = "login")
    public void login(HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException
    {
        String data = request.getQueryString();
        indexService.login(data,session);
        response.sendRedirect("index");
    }

    /**
     * 首页显示
     * @param session
     * @param model
     * @return
     */
    @GetMapping(value = "index")
    public String index(HttpSession session, Model model) {
        String userId = (String)session.getAttribute("UserID");
        String strOrder = (String)session.getAttribute("isOrder");
        String returnURL = (String)session.getAttribute("ReturnURL");
        Boolean isOrder =  Boolean.valueOf(strOrder).booleanValue();

        RankingList user = indexService.user(userId,isOrder);

        List<User> rankingList = indexService.rankingList();
        User userRanking = indexService.userRanking(userId,isOrder,user.getIntegral());
        Integer stage = indexService.stage();
        model.addAttribute("rankingList",rankingList);
        model.addAttribute("userRanking",userRanking);
        model.addAttribute("stage",stage);
        model.addAttribute("theAwardTime", CommonUtil.theAwardTime());
        model.addAttribute("reward", CommonUtil.userRanking(userRanking.getRanking()));
        model.addAttribute("returnURL", returnURL);

        return "index/index";
    }

    /**
     * 胜局记录
     * @param request
     * @param session
     */
    @GetMapping(value = "victory")
    @ResponseBody
    public void victory(HttpServletRequest request,HttpSession session,HttpServletResponse response)
    {
        String data = request.getQueryString();
        indexService.victory(data,session);

    }

    @GetMapping(value = "ranking/{ranking}/integer/{integer}")
    public String prize(HttpSession session,User user,Model model){
        String userId = (String)session.getAttribute("UserID");
        user.setUserId(userId);
        ActivityData activityData = indexService.prize(user);
        model.addAttribute("activity",activityData);
        return "index/prize";
    }

    /**
     * 奖品介绍
     * @return
     */
    @GetMapping(value = "introduce")
    public String introduce(){
        return "index/introduce";
    }

    /**
     * 活动规则
     * @return
     */
    @GetMapping(value = "rule")
    public String rule(){
        return "index/rule";
    }


    /**
     * 定时清理积分
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void cleanIntegral(){
        indexService.cleanIntegral();
    }


    @GetMapping(value = "test")
    @ResponseBody
    public void test(HttpServletRequest request,HttpSession session){
//        String data = request.getQueryString();
//        Map<String,String> map = CommonUtil.decodeParams(data,session,false);
//        Long date = System.currentTimeMillis();
//        String stringSing = MD5Util.md5(map.get("UserID")+map.get("ReturnURL")+map.get("isOrder")+date+"it is client who fuck this piece of shit");
//        System.out.println(date);
//        System.out.println(stringSing);

    }
}
