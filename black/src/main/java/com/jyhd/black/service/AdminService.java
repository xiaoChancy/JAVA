package com.jyhd.black.service;

import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface AdminService {

    Result loginPost(String account, String password, HttpSession session);

    Page<ActivityData> activityAll(String userId,Pageable pageable);

    int activitySum();

    int activityPaySum();

    int activityNotPaySum();

    int active(String date);

}
