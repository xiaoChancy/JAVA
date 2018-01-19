package com.jyhd.black.dao;

import com.jyhd.black.domain.ActivityData;
import com.jyhd.black.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ActivityDataDao extends JpaRepository<ActivityData, Long> {

    @Query(value = "SELECT * FROM activity_data WHERE user_id=?1 AND stage=?2",nativeQuery = true)
    ActivityData findByUserIdAndStage(String userId, int stage);

    Page<ActivityData> findByUserId(String userId,Pageable pageable);
}
