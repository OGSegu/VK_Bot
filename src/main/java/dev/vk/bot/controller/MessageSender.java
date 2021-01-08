package dev.vk.bot.controller;

import dev.vk.bot.config.Config;
import dev.vk.bot.config.MessageAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Slf4j
@Component
public class MessageSender {

    public static final String WELCOME_MSG = "Привет, с помощью этого бота, ты можешь поиграть в \"Своя Игра\". Для того чтобы начать, добавь бота в беседу";
    public static final String PONG_MSG = "Pong!";
    public static final String NOCOMMAND_MSG = "Такой команды не существует! Введи /помощь";


    private final Random random = new Random();

    @Autowired
    private Config config;

    @Autowired
    private MessageAPI messageAPI;

    @Autowired
    private RestTemplate restTemplate;

    public void sendMessage(int peer_id, String msg) {
        String apiRequest = String.format(messageAPI.getSendMessageAPI(),
                peer_id,
                random.nextInt(),
                msg,
                config.getGroupId(),
                config.getToken(),
                config.getVersion()
        );
        String response = restTemplate.getForObject(apiRequest, String.class);
        log.info("Message sent. Received: " + response);
    }

    @Bean
    public MessageSender getMessageSender() {
        return new MessageSender();
    }
}
