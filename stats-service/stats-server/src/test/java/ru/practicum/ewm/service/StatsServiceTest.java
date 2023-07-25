package ru.practicum.ewm.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock
    StatsRepository repository;

    @InjectMocks
    StatsServiceImpl service;

    EndpointHit hit = EndpointHit.builder()
            .app("ewm-main-service")
            .ip("192.163.0.1")
            .uri("/events/1")
            .timestamp(LocalDateTime.now())
            .build();

    ViewStats viewStats = ViewStats.builder()
            .app("ewm-main-service")
            .uri("/events/1")
            .hits(6L)
            .build();

    @Test
    void shouldCreateHit() {
        service.postHit(hit);
        verify(repository, Mockito.times(1))
                .save(any());
    }

    @Test
    void shouldGetAllStatsDistinctIp_whenUrisIsNull_andUniqueIsTrue() {
        viewStats.setUri(null);
        when(repository.getAllUniqueStats(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(viewStats));

        List<ViewStats> list = service.getStats(LocalDateTime.now(), LocalDateTime.now().plusHours(1), null, true);
        assertEquals(list.get(0).getApp(), viewStats.getApp());
        assertEquals(list.get(0).toString(), viewStats.toString());
        verify(repository, Mockito.times(1))
                .getAllUniqueStats(any(), any());
    }

    @Test
    void shouldGetAllStats_whenUrisIsNull_andUniqueIsFalse() {
        viewStats.setUri(null);
        when(repository.getAllStats(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(viewStats));

        List<ViewStats> list = service.getStats(LocalDateTime.now(), LocalDateTime.now().plusHours(1), null, false);
        assertEquals(list.get(0).getApp(), viewStats.getApp());
        assertEquals(list.get(0).toString(), viewStats.toString());
        verify(repository, Mockito.times(1))
                .getAllStats(any(), any());
    }

    @Test
    void shouldGetUniqueStatsByUris_andUniqueIsTrue() {
        when(repository.getUniqueStatsByUris(any(LocalDateTime.class), any(LocalDateTime.class), any()))
                .thenReturn(List.of(viewStats));

        List<ViewStats> list = service.getStats(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), List.of("/events/1"), true);
        assertEquals(list.get(0).getApp(), viewStats.getApp());
        assertEquals(list.get(0).toString(), viewStats.toString());
        verify(repository, Mockito.times(1))
                .getUniqueStatsByUris(any(), any(), any());
    }

    @Test
    void shouldGetStatsByUris_andUniqueIsFalse() {
        when(repository.getStatsByUris(any(LocalDateTime.class), any(LocalDateTime.class), any()))
                .thenReturn(List.of(viewStats));

        List<ViewStats> list = service.getStats(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), List.of("/events/1"), false);
        assertEquals(list.get(0).getApp(), viewStats.getApp());
        assertEquals(list.get(0).toString(), viewStats.toString());
        verify(repository, Mockito.times(1))
                .getStatsByUris(any(), any(), any());
    }
}
