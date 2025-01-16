package com.daesoo.dmotools.common.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.dto.StatType;
import com.daesoo.dmotools.common.entity.Seal;

public interface SealRepository extends JpaRepository<Seal, Long>{

	Page<Seal> findAllByStatType(Pageable pageable, StatType statType);

	List<Seal> findAllByStatType(StatType statType);
	
	List<Seal> findAllByOrderByStatTypeAscIdAsc();
	
	List<Seal> findAllByOrderByStatTypeAsc();

}
