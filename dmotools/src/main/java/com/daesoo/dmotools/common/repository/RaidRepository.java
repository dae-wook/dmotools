package com.daesoo.dmotools.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.common.entity.Raid;

public interface RaidRepository extends JpaRepository<Raid, Long>{
	
//    @Query("SELECT DISTINCT r FROM raids r LEFT JOIN r.timers t ON t.server = :serverType")
//    List<Raid> findByTimerServerTypeOrNoTimers(@Param("serverType") ServerType serverType);
    
	@Query("SELECT DISTINCT r FROM raids r LEFT JOIN FETCH r.timers t WHERE t.server = :serverType OR t IS NULL")
	List<Raid> findByTimerServerTypeOrNoTimers(@Param("serverType") ServerType serverType);
}
