package com.rg.testbot.service;

import com.rg.testbot.repository.PairMsgRepository;
import org.springframework.stereotype.Service;

@Service
public class PairMsgService {
    private final PairMsgRepository rep;

    PairMsgService(PairMsgRepository rep) {
        this.rep = rep;
    }

    public void create(com.rg.testbot.entity.PairMsg message) {
        rep.save(message);
    }
}