package com.daesoo.dmotools.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daesoo.dmotools.common.entity.Seal;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.entity.UserPrice;

@Repository
public interface UserPriceRepository extends JpaRepository<UserPrice, Long>{

	List<UserPrice> findAllByUser(User user);

	Optional<UserPrice> findByUserAndSeal(User user, Seal seal);
	
}
