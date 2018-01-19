package com.jyhd.black.service.impl;

import com.jyhd.black.config.WebSecurityConfig;
import com.jyhd.black.dao.ActivityDataDao;
import com.jyhd.black.dao.AdminDao;
import com.jyhd.black.dao.GameDataDao;
import com.jyhd.black.dao.RankingDao;
import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.Admin;
import com.jyhd.black.domain.Result;
import com.jyhd.black.enums.ResultEnum;
import com.jyhd.black.exception.BlackExcption;
import com.jyhd.black.service.AdminService;
import com.jyhd.black.utils.CommonUtil;
import com.jyhd.black.utils.MD5Util;
import com.jyhd.black.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;
    @Autowired
    ActivityDataDao activityDataDao;
    @Autowired
    RankingDao rankingDao;
    @Autowired
    GameDataDao gameDataDao;

    @Override
    public Result loginPost(String userName, String password, HttpSession session) {
        Admin user = adminDao.findByUser(userName);
        if(user == null){
            throw new BlackExcption(ResultEnum.LOGIN_USER);
        }else if(!user.getPassword().equals(MD5Util.md5(password))){
            throw new BlackExcption(ResultEnum.LOGIN_PASSWORD);
        }
        session.setAttribute(WebSecurityConfig.SESSION_KEY,userName);
        return ResultUtil.success(ResultEnum.LOGIN_SUCCESS,userName);
    }

    @Override
    public Page<ActivityData> activityAll(String userId,Pageable pageable) {
        if (userId.equals("")){
            return activityDataDao.findAll(pageable);
        }else{
            return activityDataDao.findByUserId(userId,pageable);
        }
    }

    @Override
    public int activitySum() {
        return rankingDao.countAllBy();
    }

    @Override
    public int activityPaySum() {
        return rankingDao.countByIntegralAfterAndMonth();
    }

    @Override
    public int activityNotPaySum() {
        return rankingDao.countByMonthAndIntegralAfter();
    }

    @Override
    public int active(String date) {
        if (date.equals("date")) {
            return gameDataDao.countByDate(CommonUtil.getDate());
        }
        return gameDataDao.countByDate(date);
    }
}
