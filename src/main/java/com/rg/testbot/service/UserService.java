package com.rg.testbot.service;

import com.rg.testbot.entity.User;
import com.rg.testbot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository rep;

    UserService(UserRepository rep){
        this.rep = rep;
    }

    public List<User> getAll() {
        return rep.findAll();
    }

    public User getByChatId(Long chatId) {
        return rep.getByChatId(chatId);
    }

    public void create(User user) {
        if(rep.existsByChatId(user.getChatId()))
            LOGGER.info("Cannot save @{}! This user is already exist!", user.getUsername());
        else {
            rep.save(user);
            LOGGER.info("Save new user @{}", user.getUsername());
        }
    }

    public void update(Long chatId){
        LocalDateTime lastMsgDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        if(!rep.existsByChatId(chatId)) {
            LOGGER.info("User @{} not found!", chatId);
        }
        else{
            User updatedUser = rep.getByChatId(chatId);
            updatedUser.setLastMessageAt(lastMsgDateTime);
            rep.save(updatedUser);
        }
    }
}