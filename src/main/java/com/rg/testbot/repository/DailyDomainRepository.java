package com.rg.testbot.repository;

import com.rg.testbot.entity.DailyDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyDomainRepository extends JpaRepository<DailyDomain, Long> {
    @Query("SELECT COUNT(*) FROM DailyDomain")
    int getDomainsNumber();
}
