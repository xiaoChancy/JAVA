package com.jyhd.black.dao;

import com.jyhd.black.domain.RankingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingDao extends JpaRepository<RankingList, Long> {

    RankingList findByUserId(String userId);

    @Query(value = "SELECT * FROM ranking_list WHERE month=1 AND integral>0 ORDER BY integral DESC limit 0,20",nativeQuery = true)
    List<RankingList> findByRankingList();

    @Query(value = "SELECT COUNT(*) FROM ranking_list WHERE integral>?1",nativeQuery = true)
    int findByRank(int rank);

    int countAllBy();

    @Query(value = "SELECT COUNT(*) FROM ranking_list WHERE integral>0 AND month=1 ",nativeQuery = true)
    int countByIntegralAfterAndMonth();

    @Query(value = "SELECT COUNT(*) FROM ranking_list WHERE integral>0 AND month=0 ",nativeQuery = true)
    int countByMonthAndIntegralAfter();

    @Modifying
    @Query(value = "UPDATE  ranking_list SET integral=0 WHERE integral>0",nativeQuery = true)
    int timingCleanIntegral();
}
