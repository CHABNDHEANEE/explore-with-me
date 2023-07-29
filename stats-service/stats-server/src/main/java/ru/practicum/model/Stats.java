package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hits")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 64)
    private String app;

    @Column(nullable = false, length = 256)
    private String uri;

    @Column(nullable = false, length = 64)
    private String ip;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timestamp;
}
