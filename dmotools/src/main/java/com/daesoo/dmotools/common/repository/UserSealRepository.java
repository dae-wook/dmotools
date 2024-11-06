package com.daesoo.dmotools.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daesoo.dmotools.common.entity.Character;
import com.daesoo.dmotools.common.entity.Seal;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.entity.UserSeal;

@Repository
public interface UserSealRepository extends JpaRepository<UserSeal, Long>{

	List<UserSeal> findAllByUser(User user);

	Optional<UserSeal> findByUserAndSeal(User user, Seal seal);
	Optional<UserSeal> findByUserAndSealAndCharacter(User user, Seal seal, Character character);

	List<UserSeal> findAllByCharacterIsNull();

	List<UserSeal> findAllByUserAndCharacter(User user, Character character);
	
	List<UserSeal> findAllByCharacter(Character character);
	
}
