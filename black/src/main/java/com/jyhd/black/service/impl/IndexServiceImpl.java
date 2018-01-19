package com.jyhd.black.service.impl;

import com.jyhd.black.dao.ActivityDataDao;
import com.jyhd.black.dao.RankingDao;
import com.jyhd.black.dao.RedisDao;
import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.GameData;
import com.jyhd.black.domain.User;
import com.jyhd.black.domain.RankingList;
import com.jyhd.black.enums.ResultEnum;
import com.jyhd.black.exception.BlackExcption;
import com.jyhd.black.service.IndexService;
import com.jyhd.black.utils.CommonUtil;
import com.jyhd.black.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IndexServiceImpl implements IndexService{

    //日志
    private final static Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);
    private final static String KEY = "it is client who fuck this piece of shit";

    @Autowired
    private RedisDao<String> redisDao;
    @Autowired
    private RankingDao rankingDao;
    @Autowired
    private ActivityDataDao activityDataDao;

    @Override
    public void login(String data, HttpSession session) {
        Map<String,String> map = CommonUtil.decodeParams(data,session,true);
        String stringSing = MD5Util.md5(map.get("UserID")+map.get("ReturnURL")+map.get("isOrder")+map.get("time")+KEY);
        if(!map.get("sign").equals(stringSing))
        {
            logger.info("sign={}","登录签名失败");
            throw new BlackExcption(ResultEnum.LOGIN_SIGN_ERROR);
        }
    }

    @Override
    public List<User> rankingList() {
        List<User> rankingList = new ArrayList<>();
        User user;

        List<String> ranking_list = redisDao.hgetAll("ranking_list");
        if(ranking_list.isEmpty()){
            List<RankingList> list = rankingDao.findByRankingList();
            cacheList(list);
        }
        List<String> ranking_val = redisDao.hgetAll("ranking_list");
        Set<String> ranking_key = redisDao.hgetKeys("ranking_list");

        int i = 0;
        for ( Iterator it = ranking_key.iterator();it.hasNext();i++){
            user = new User();
            String[] strArr = ranking_val.get(i).split("%");

            String getUserId = (String) it.next();
            int length = getUserId.length();
            String subUserId = getUserId.substring(0,5)+"****"+ getUserId.substring(length - 5,length);
            user.setUserId(subUserId);
            user.setRanking(Integer.parseInt(strArr[0]));
            user.setInteger(Integer.parseInt(strArr[1]));
            rankingList.add(user);
        }
        return rankingList;
    }

    @Override
    public User userRanking(String usrId,Boolean isOrder,int integral) {
        int month = isOrder ? 1 : 0;
        String user = redisDao.hget("ranking_list",usrId);
        if(user == null){
            int ranking = rankingDao.findByRank(integral);
            return new User(usrId,ranking,integral,month);
        }else{
            String[] strArr = user.split("%");
            return new User(usrId,Integer.parseInt(strArr[0]),Integer.parseInt(strArr[1]),month);
        }
    }

    @Override
    public RankingList user(String usrId, Boolean isOrder) {
        RankingList usr = rankingDao.findByUserId(usrId);
        int month = isOrder ? 1 : 0;
        if(usr == null){
            return new RankingList(usrId,0,(short) month);
        }
        return usr;
    }

    @Override
    public Integer stage(){
        String stage = redisDao.get("stage");
        if(stage.equals("")){
            stage = "1";
        }
        return Integer.parseInt(stage);
    }

    @Override
    public void victory(String data, HttpSession session) {
        Map<String,String> map = CommonUtil.decodeParams(data,session,false);
        String stringSing = MD5Util.md5(map.get("UserID")+map.get("gameName")+map.get("isOrder")+map.get("time")+KEY);
        if(!map.get("sign").equals(stringSing))
        {
            logger.info("sign={}","胜局签名失败");
            throw new BlackExcption(ResultEnum.GAME_SIGN_ERROR);
        }
        if(CommonUtil.theAwardTime()) { return; }
//        CommonUtil.timeStamp(redisDao,map.get("time"));

        int integral = getIntegral(map.get("gameName"),map.get("UserID"));

        RankingList rest =  rankingDao.findByUserId(map.get("UserID"));
        if(rest == null){
            RankingList user = new RankingList(map.get("UserID"),integral, Short.parseShort(map.get("isOrder")));
            rankingDao.save(user);
        } else {
            RankingList user = new RankingList(map.get("UserID"), rest.getIntegral() + integral, Short.parseShort(map.get("isOrder")));
            rankingDao.save(user);
        }
        GameData gameData = new GameData(CommonUtil.getDate(),map.get("UserID"),map.get("gameName"),integral);
        redisDao.rpush("gameData",gameData);
        updateGameData();
    }

    @Override
    public void cacheList(List<RankingList> list) {
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++){
                RankingList rankingList = list.get(i);
                redisDao.hput("ranking_list", rankingList.getUserId(),(i+1) + "%" + rankingList.getIntegral(),60);
            }
        }
    }

    @Override
    public ActivityData prize(User user) {
        if(user.getRanking() > 500 || user.getRanking() <= 0){
            throw new BlackExcption(ResultEnum.NOT_ON_THE_LIST);
        }
        Integer stage = stage();
        ActivityData userActivity = activityDataDao.findByUserIdAndStage(user.getUserId(),stage);
        if(userActivity == null){
            int reward = CommonUtil.userRanking(user.getRanking());
            ActivityData activityData = new ActivityData();
            activityData.setUserId(user.getUserId());
            activityData.setTime(CommonUtil.getDate());
            activityData.setReward(reward);
            activityData.setIntegral(user.getInteger());
            activityData.setRanking(user.getRanking());
            activityData.setStage(stage);
            System.out.println(activityData);
            activityDataDao.save(activityData);
            return activityData;
        }
        userActivity.setStage(stage);
        return userActivity;
    }

    @Override
    public void cleanIntegral() {
        if(CommonUtil.isDate("2018-03-01",CommonUtil.getDate())){
//            redisDao.incr("stage",1L);
            rankingDao.timingCleanIntegral();
        }
    }

    private int getIntegral(String gameName, String userId) {
        int week = CommonUtil.week();
        int multiple = week == 0 || week == 6 ? 2 : 1;
        int integer;
        if(gameName.equals("hbmj")|| gameName.equals("mahjong") || gameName.equals("gbmj"))
        {
            integer = 10 * multiple;
        }
        else if(gameName.equals("jungle") || gameName.equals("gobang") || gameName.equals("blackwhite"))
        {
            integer = 5 * multiple;
        }
        else if(gameName.equals("dou_di_zhu") || gameName.equals("ludo"))
        {
            integer = 8 * multiple;
        }
        else
        {
            logger.info("userID={}",userId);
            throw new BlackExcption(ResultEnum.GAME_NAME_REPEAT);
        }
        return integer;
    }

    private void updateGameData(){
        Long gameDataNum = redisDao.llen("gameData");
        if(gameDataNum > 1) {
//            StringBuffer sqlValues = new StringBuffer();
//            for (int i = 0; i < gameDataNum; i++) {
//                Object gameData = redisDao.lpop("gameData");
//                if (gameData instanceof GameData) {
//                    GameData data = (GameData) gameData;
//                    sqlValues.append("('" + data.getDate() + "','" + data.getUserId() + "','" + data.getGameName() + "'," + data.getInteral() + "),");
//                }
//            }
        }
    }
}
