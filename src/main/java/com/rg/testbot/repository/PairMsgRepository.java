package com.rg.testbot.repository;

import com.rg.testbot.entity.PairMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PairMsgRepository extends JpaRepository<PairMsg, Long> {
}
