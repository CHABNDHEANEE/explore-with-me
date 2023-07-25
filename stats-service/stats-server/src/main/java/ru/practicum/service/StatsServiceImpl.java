package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mapper.StatsMapper.STATS_MAPPER;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Transactional
    @Override
    public void postHit(EndpointHit hit) {
        statsRepository.save(STATS_MAPPER.endpointHitToStats(hit));
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return statsRepository.getAllUniqueStats(start, end);
            } else {
                return statsRepository.getAllStats(start, end);
            }
        } else {
            if (unique) {
                return statsRepository.getUniqueStatsByUris(start, end, uris);
            } else {
                return statsRepository.getStatsByUris(start, end, uris);
            }
        }
    }
}
