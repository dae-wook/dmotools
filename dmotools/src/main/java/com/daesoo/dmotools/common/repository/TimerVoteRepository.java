package com.daesoo.dmotools.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daesoo.dmotools.common.entity.Client;
import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.common.entity.TimerVote;

public interface TimerVoteRepository extends JpaRepository<TimerVote, Long>{

	Optional<TimerVote> findByTimerAndClient(Timer timer, Client client);
}
