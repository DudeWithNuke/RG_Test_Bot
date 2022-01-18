package com.rg.testbot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    private Long chatId;

    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime lastMessageAt;

    public User() {
    }

    public User(Long chatId, String username) {
        this.chatId = chatId;
        this.username = username;
        this.lastMessageAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);;
    }

    public Long getChatId(){
        return chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }
}