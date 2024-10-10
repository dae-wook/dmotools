package com.daesoo.dmotools.common.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.entity.Gatcha;
import com.daesoo.dmotools.gatcha.dto.GatchaResponseDto;

public interface GatchaRepository extends JpaRepository<Gatcha, Long>{
	
	List<Gatcha> findAllByVisibleTrue();

	List<Gatcha> findAllByVisibleTrueAndType(String type);

}
