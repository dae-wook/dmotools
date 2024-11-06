package com.daesoo.dmotools.common.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.entity.Character;
import com.daesoo.dmotools.common.entity.User;

public interface CharacterRepository extends JpaRepository<Character, Long>{

	Optional<Character> findByUser(User user);

	List<Character> findAllByUser(User user);

}
