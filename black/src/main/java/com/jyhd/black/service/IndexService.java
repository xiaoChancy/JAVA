package com.jyhd.black.service;

import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.User;
import com.jyhd.black.domain.RankingList;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IndexService {

    void login(String data, HttpSession session);

    void victory(String data,HttpSession session);

    List<User> rankingList();

    RankingList user(String userId, Boolean isOrder);

    void cacheList(List<RankingList> list);

    Integer stage();

    User userRanking(String usrId,Boolean isOrder,int integral);

    ActivityData prize(User user);

    void cleanIntegral();
}
