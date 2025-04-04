package com.kaiv.redis;

import com.kaiv.redis.model.User;
import com.kaiv.redis.service.RedisProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SpringBootApplication
public class RedisProducer implements CommandLineRunner {

    private final RedisProducerService userService;
    private static final Logger LOG = LoggerFactory.getLogger(RedisProducer.class);

    public RedisProducer(RedisProducerService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisProducer.class, args);
    }

    @Override
    public void run(String... args) {

        // test 2
        for (int i = 0; i <= 999999; i++) {
            try {
                Thread.sleep(2000);
                User currentUser = new User(String.valueOf(i), "Ivan", 37 + i);

                userService.publishUserEvent(currentUser);
                LOG.info("Info published to stream: " + currentUser);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
