package com.daesoo.dmotools.common.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.common.entity.Timer;

public interface TimerRepository extends JpaRepository<Timer, Long>{
	
	List<Timer> findAllByStartAtBefore(LocalDateTime dateTime);

	List<Timer> findAllByServer(ServerType server);

}