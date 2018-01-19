package com.jyhd.black.utils;

import com.jyhd.black.dao.RedisDao;
import com.jyhd.black.enums.ResultEnum;
import com.jyhd.black.exception.BlackExcption;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共工具
 */
public class CommonUtil {

    /**
     * 比较时间
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static Boolean isDate(String DATE1, String DATE2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 是否为星期六和星期天
     * @return
     */
    public static Boolean isWeek() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(week == 6 || week == 0){
            return true;
        }
        return false;
    }

    /**
     * 解析url
     * @param data
     * @param session
     * @param isSession
     * @return
     */
    public static Map<String,String> decodeParams(String data, HttpSession session, Boolean isSession){
        String[] info = data.split("&");
        Map<String,String> map = new HashMap<>();
        for(int i = 0;i<info.length; i++)
        {
            String arr_str[] = info[i].split("=");
            if(arr_str[1] == null)
                break;
            map.put(arr_str[0],arr_str[1]);
            if(isSession)
                session.setAttribute(arr_str[0],arr_str[1]);
        }
        return map;
    }

    /**
     * 时间戳
     * @param redisDao
     * @param time_stamp
     */
    public static void timeStamp(RedisDao<String> redisDao, String time_stamp)
    {
        Object timeStamp = redisDao.get("time_stamp");
        if(timeStamp != null){
            if(Long.parseLong((String) timeStamp) < Long.parseLong(time_stamp)){
                redisDao.set("time_stamp",time_stamp);
            }else{
                throw new BlackExcption(ResultEnum.GAME_REPEAT);
            }
        }else{
            redisDao.set("time_stamp",time_stamp);
        }
    }

    /**
     *返回星期【0-6】 0是星期日
     * @return
     */
    public static int week(){
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_WEEK)-1;
    }

    /**
     * 判断是否在领奖期间
     * @return
     */
    public static Boolean theAwardTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-");
        String startDate = simpleDateFormat.format(new Date()) + "15";
        String endDate = simpleDateFormat.format(new Date()) + "19";
        String date = getDate();

        if(isDate(date,startDate) && isDate(endDate,date) || isDate(date , "2018-03-01"))
        {
            return true;
        }
        return false;
    }

    public static String getDate(){
        String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return stringDate;
    }

    /**
     * 判断排名段位
     * @return
     */
    public static int userRanking(int rankingNumber){
        int number = 0;
        if(rankingNumber > 100 && rankingNumber <= 500){
            number = 4;
        }else if(rankingNumber > 50 && rankingNumber <= 100){
            number = 3;
        }else if(rankingNumber > 20 && rankingNumber <= 50){
            number = 2;
        }else if(rankingNumber >= 1 && rankingNumber <= 20){
            number = 1;
        }
        return number;
    }



}
