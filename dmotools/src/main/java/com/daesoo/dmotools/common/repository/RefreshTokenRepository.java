package com.daesoo.dmotools.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String>{

}
