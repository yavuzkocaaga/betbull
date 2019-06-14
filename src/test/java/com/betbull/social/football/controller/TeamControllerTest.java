package com.betbull.social.football.controller;

import com.betbull.social.football.dto.TeamCreateRequest;
import com.betbull.social.football.dto.TeamDeleteRequest;
import com.betbull.social.football.dto.TeamUpdateRequest;
import com.betbull.social.football.service.FootballerService;
import com.betbull.social.football.service.TeamService;
import com.betbull.social.football.validator.TeamCreateRequestValidator;
import com.betbull.social.football.validator.TeamUpdateRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(PowerMockRunner.class)
public class TeamControllerTest {

    private static final String ADDRESS_POST_ENDPOINT = "/api/v1/team";

    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @Mock
    private TeamService teamService;

    @Mock
    private  FootballerService footballerService;

    @Mock
    private TeamCreateRequestValidator teamCreateRequestValidator;

    @Mock
    private TeamUpdateRequestValidator teamUpdateRequestValidator;


    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders
                .standaloneSetup(new TeamController(teamService,footballerService, teamCreateRequestValidator, teamUpdateRequestValidator))
                .build();
    }

    @Test
    public void create() throws Exception {
        TeamCreateRequest teamCreateRequest = new TeamCreateRequest();
        teamCreateRequest.setName("Test Team");
        teamCreateRequest.setCountryCode("Test CountryCode");
        mvc.perform(
                post(ADDRESS_POST_ENDPOINT + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamCreateRequest)))
                .andExpect(status().isOk());

        //@formatter:on
    }

    @Test
    public void update() throws Exception {
        TeamUpdateRequest updateTeam = new TeamUpdateRequest();
        updateTeam.setTeamId(1L);
        updateTeam.setName("Test Team 2");
        mvc.perform(
                post(ADDRESS_POST_ENDPOINT + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTeam)))
                .andExpect(status().isOk());

        //@formatter:on
    }

    @Test
    public void findByTeamId() throws Exception {
        final String teamId = "1";
        mvc.perform(
                get(ADDRESS_POST_ENDPOINT + "/findByTeamId", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", teamId))
                .andExpect(status().isOk());

        //@formatter:on
    }


    @Test
    public void allTeams() throws Exception {
        mvc.perform(
                get(ADDRESS_POST_ENDPOINT + "/allTeams")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        //@formatter:on
    }

    @Test
    public void delete() throws Exception {
        final TeamDeleteRequest deleteRequest = new TeamDeleteRequest();
        deleteRequest.setTeamId(1L);
        mvc.perform(
                post(ADDRESS_POST_ENDPOINT + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isOk());
        //@formatter:on
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
