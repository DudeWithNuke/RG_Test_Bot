package com.rg.testbot.job;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rg.testbot.TestBot;
import com.rg.testbot.entity.DailyDomain;
import com.rg.testbot.entity.User;
import com.rg.testbot.service.DailyDomainService;
import com.rg.testbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DailyDomainJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(DailyDomainJob.class);

    @Value("${backorder.url}")
    private String backorderURL;

    private final TestBot testBot;
    private final DailyDomainService dailyDomainService;
    private final UserService userService;

    public DailyDomainJob(TestBot testBot, DailyDomainService dailyDomainService, UserService userService) {
        this.testBot = testBot;
        this.dailyDomainService = dailyDomainService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 54 2 * * *")
    public void findNewArticles() throws Exception {
        LOGGER.info("Start a backorder reading...");

        dailyDomainService.deleteAll();

        String jsonString = readUrl(backorderURL);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        DailyDomain[] dailyDomains = gson.fromJson(jsonString, DailyDomain[].class);
        dailyDomainService.saveAll(Arrays.asList(dailyDomains));

        LOGGER.info("Backorder reading is done!");

        User[] users = userService.getAll().toArray(new User[0]);
        String domainInfo = LocalDate.now() + ". Собрано " + dailyDomainService.getDomainsNumber() + " доменов";
        testBot.sendDomains(users, domainInfo);
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) buffer.append(chars, 0, read);

            return buffer.toString();
        }
        catch (IOException e) {
            LOGGER.error("Failed to read backorder file!");
            return null;
        }
        finally {
            if (reader != null) reader.close();
        }
    }
}
