package com.daesoo.dmotools.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.entity.Gatcha;

public interface GatchaRepository extends JpaRepository<Gatcha, Long>{
	
	List<Gatcha> findAllByVisibleTrue();

}
