package com.rg.testbot.repository;

import com.rg.testbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByChatId(Long chatId);
    User getByChatId(Long chatId);
}