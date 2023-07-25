package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Builder
public class ViewStats {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private Long hits;

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
