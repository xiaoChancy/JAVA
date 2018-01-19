package com.jyhd.black.dao;

import com.jyhd.black.domain.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameDataDao extends JpaRepository<GameData, Long> {

    @Query(value = "select count(distinct(user_id)) from game_datas where date_stamp=?1",nativeQuery = true)
    int countByDate(String date);

}
