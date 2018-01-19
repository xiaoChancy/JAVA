package com.jyhd.black.dao;

import com.jyhd.black.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminDao extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT * FROM admin WHERE user=?1",nativeQuery = true)
    Admin findByUser(String user);


}
