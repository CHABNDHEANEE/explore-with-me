package ru.practicum.ewm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.practicum.controller.StatsController;
import ru.practicum.dto.EndpointHit;
import ru.practicum.service.StatsServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
public class StatsControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    @MockBean
    StatsServiceImpl statsService;

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
    void shouldSaveHitAndGetStatusOk() throws Exception {
        mvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hit))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotSaveHitWhenAppIsBlank() throws Exception {
        hit.setApp("");
        mvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hit))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldNotSaveHitWhenUriIsBlank() throws Exception {
        hit.setUri("");
        mvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hit))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldNotSaveHitWhenIpIsBlank() throws Exception {
        hit.setIp("");
        mvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hit))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldNotSaveHitWhenTimestampIsNull() throws Exception {
        hit.setTimestamp(null);
        mvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hit))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldGetStatsWhenCorrectParam() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("start", "2022-09-06 11:00:23");
        params.add("end", "2022-09-06 12:00:23");
        mvc.perform(get("/stats").params(params))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldNotGetStatsWhenParamIsNotCorrect() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("start", null);
        params.add("end", null);
        mvc.perform(get("/stats").params(params))
                .andExpect(status().is4xxClientError());
    }
}
