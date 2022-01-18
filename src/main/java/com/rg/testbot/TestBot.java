package com.rg.testbot;

import com.rg.testbot.entity.PairMsg;
import com.rg.testbot.entity.User;
import com.rg.testbot.service.PairMsgService;
import com.rg.testbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class TestBot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestBot.class);

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    private final UserService userService;
    private final PairMsgService pairMsgService;

    TestBot(UserService userService, PairMsgService pairMsgService){
        this.userService = userService;
        this.pairMsgService = pairMsgService;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @PostConstruct
    public void start() {
        LOGGER.info("username: {}, token: {}", username, token);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text;
        Message incomingMessage = update.getMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (incomingMessage.getText().equals("/start")) {
                text = "Hello there!";

                User newUser = new User(
                        incomingMessage.getChatId(),
                        incomingMessage.getChat().getUserName());
                userService.create(newUser);
            } else {
                text = incomingMessage.getText();

                PairMsg pairMsg = new PairMsg(
                        userService.getByChatId(incomingMessage.getChatId()),
                        incomingMessage.getText(), text);
                pairMsgService.create(pairMsg);

                userService.update(incomingMessage.getChatId());
            }
        } else text = "Sorry, I can only reply to text messages!";

        sendMessage(update, text);
    }

    private void sendMessage(Update update, String text) {
        SendMessage response = new SendMessage();
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        response.setChatId(String.valueOf(chatId));
        response.setText(text);

        try {
            execute(response);
            LOGGER.info("Sent message \"{}\" to {}", text, chatId);
        } catch (TelegramApiException e) {
            LOGGER.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

    public void sendDomains(User[] users, String domainsInfo) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(domainsInfo);

        try {
            for (User u : users) {
                sendMessage.setChatId(u.getChatId().toString());
                execute(sendMessage);
                LOGGER.info("Sent domains");
            }
        } catch (TelegramApiException e) {
            LOGGER.error("Failed to send domains!");
        }
    }
}