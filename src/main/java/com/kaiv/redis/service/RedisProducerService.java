package com.kaiv.redis.service;

import com.kaiv.redis.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StreamOperations;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisProducerService {

    private static final String STREAM_NAME = "user_stream";

    //private final RedisTemplate<String, Object> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    public void publishUserEvent(User user) {

     //   StreamOperations<String, Object, Object> streamOps = redisTemplate.opsForStream();

        StreamOperations<String, Object, Object> streamOps = stringRedisTemplate.opsForStream();

        Map<String, String> event = new HashMap<>();
        event.put("id", user.getId());
        event.put("name", user.getName());
        event.put("age", String.valueOf(user.getAge()));

        final ObjectRecord<String, User> record2 = StreamRecords.newRecord()
                .ofObject(user)
                .withStreamKey(STREAM_NAME);

       // streamOps.add(record2);

        streamOps.add(STREAM_NAME, event);

    }

}
