package com.daesoo.dmotools.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	Optional<Client> findByIpAddress(String ipAddress);
}
