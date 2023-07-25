package ru.practicum.ewm.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.dto.EndpointHit;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.mapper.StatsMapper.STATS_MAPPER;

public class StatsMapperTest {
    EndpointHit hit;

    @BeforeEach
    void init() {
        hit = EndpointHit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldMapToStats() {

        Stats stats = STATS_MAPPER.endpointHitToStats(hit);

        assertEquals(stats.getApp(), hit.getApp());
        assertEquals(stats.getUri(), hit.getUri());
        assertEquals(stats.getIp(), hit.getIp());
        assertEquals(stats.getTimestamp(), hit.getTimestamp());
    }
}
