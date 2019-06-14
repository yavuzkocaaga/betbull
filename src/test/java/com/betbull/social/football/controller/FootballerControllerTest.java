package com.betbull.social.football.controller;

import com.betbull.social.football.dto.FootBallerCreateRequest;
import com.betbull.social.football.dto.FootBallerUpdateRequest;
import com.betbull.social.football.service.FootballerService;
import com.betbull.social.football.validator.FootballerRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(PowerMockRunner.class)
public class FootballerControllerTest {

    private static final String ADDRESS_POST_ENDPOINT = "/api/v1/footballer";


    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @Mock
    private FootballerService footballerService;

    @Mock
    private FootballerRequestValidator footballerRequestValidator;


    @Before
    public void setUp() {

        when(footballerRequestValidator.supports(any())).thenReturn(true);

        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders
                .standaloneSetup(new FootballerController(footballerService, footballerRequestValidator))
                .build();
    }

    @Test
    public void create() throws Exception {
        LocalDate localDate = LocalDate.now();
        FootBallerCreateRequest footBallerCreateRequest = new FootBallerCreateRequest();
        footBallerCreateRequest.setAge(17);
        footBallerCreateRequest.setDateOfBirth(localDate);
        footBallerCreateRequest.setWeight(74);
        footBallerCreateRequest.setHeight(174);
        footBallerCreateRequest.setShirtNumber(3);
        footBallerCreateRequest.setPosition("Defans");
        footBallerCreateRequest.setExperienceMonth(20);
        footBallerCreateRequest.setFullName("Test Player Name");
        footBallerCreateRequest.setNationality("TR");
        mvc.perform(
                post(ADDRESS_POST_ENDPOINT + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(footBallerCreateRequest)))
                .andExpect(status().is2xxSuccessful());

        //@formatter:on
    }

    @Test
    public void update() throws Exception {
        LocalDate dateOfBirth = LocalDate.now();
        FootBallerUpdateRequest footBallerUpdateRequest = new FootBallerUpdateRequest();
        footBallerUpdateRequest.setAge(30);
        footBallerUpdateRequest.setDateOfBirth(dateOfBirth);
        footBallerUpdateRequest.setWeight(74);
        footBallerUpdateRequest.setHeight(174);
        footBallerUpdateRequest.setShirtNumber(3);
        footBallerUpdateRequest.setPosition("Right Wing");
        footBallerUpdateRequest.setExperienceMonth(40);
        footBallerUpdateRequest.setFullName("Test Player Name");
        footBallerUpdateRequest.setNationality("TR");

        mvc.perform(
                post(ADDRESS_POST_ENDPOINT + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(footBallerUpdateRequest)))
                .andExpect(status().is2xxSuccessful());

        //@formatter:on
    }

    @Test
    public void findById() throws Exception {
        String footballerId = "7";
        mvc.perform(
                get(ADDRESS_POST_ENDPOINT + "/findById", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("footballerId", footballerId))
                .andDo(print())
                .andExpect(status().isOk());

        //@formatter:on
    }


    @Test
    public void getAllFootballer() throws Exception {

        mvc.perform(
                get(ADDRESS_POST_ENDPOINT + "/allFootballers", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void teamFootballersHasContract() throws Exception {
        String teamId = "4";
        String year = "2019";

        mvc.perform(
                get(ADDRESS_POST_ENDPOINT + "/teamFootballersHasContract", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("teamId", teamId)
                        .param("year", year))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
