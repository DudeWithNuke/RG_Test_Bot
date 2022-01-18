package com.rg.testbot.service;

import com.rg.testbot.entity.DailyDomain;
import com.rg.testbot.repository.DailyDomainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyDomainService {
    private final DailyDomainRepository rep;

    DailyDomainService(DailyDomainRepository rep) {
        this.rep = rep;
    }

    public int getDomainsNumber() {
        return rep.getDomainsNumber();
    }

    public void saveAll(List<DailyDomain> dailyDomains) {
        rep.saveAll(dailyDomains);
    }

    public void deleteAll() {
        rep.deleteAll();
    }
}
