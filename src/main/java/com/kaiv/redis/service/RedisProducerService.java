package com.kaiv.redis.service;

import com.kaiv.redis.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StreamOperations;

@Service
@RequiredArgsConstructor
public class RedisProducerService {

    private static final String STREAM_NAME = "user_stream";

    private final StringRedisTemplate stringRedisTemplate;

    public void publishUserEvent(User user) {

        StreamOperations<String, Object, Object> streamOps = stringRedisTemplate.opsForStream();

        final ObjectRecord<String, User> record2 = StreamRecords.newRecord()
                .ofObject(user)
                .withStreamKey(STREAM_NAME);

        streamOps.add(record2);

    }
}
