package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.event.enums.State;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(Long userId, Pageable page);

    List<Event> findAllByIdIn(List<Long> eventId);

    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE " +
            "(" +
            ":text IS NULL " +
            "OR LOWER(e.description) LIKE CONCAT('%', ?1, '%') " +
            "OR LOWER(e.annotation) LIKE CONCAT('%', ?1, '%')" +
            ")" +
            "AND (?2 IS NULL OR e.state IN (?2)) " +
            "AND (?3 IS NULL OR e.category.id IN (?3)) " +
            "AND (?4 IS NULL OR e.paid = ?4) " +
            "AND (CAST(?5 AS date) IS NULL OR e.eventDate >= ?5) " +
            "AND (CAST(?6 AS date) IS NULL OR e.eventDate <= ?6) " +
            "order by e.eventDate")
    List<Event> findByParamsOrderByDate(
            String text,
            List<State> states,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE (?1 IS NULL OR e.initiator.id IN (?1)) " +
            "AND (?2 IS NULL OR e.state IN (?2)) " +
            "AND (?3 IS NULL OR e.category.id IN (?3)) " +
            "AND (CAST(?4 AS date) IS NULL OR e.eventDate >= ?4) " +
            "AND (CAST(?5 AS date) IS NULL OR e.eventDate <= ?5) " +
            "order by e.eventDate")
    List<Event> findByParams(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable);

    List<Event> findAllByCategoryId(Long catId);
}
