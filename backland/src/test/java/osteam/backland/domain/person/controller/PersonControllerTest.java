package osteam.backland.domain.person.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import osteam.backland.domain.person.controller.request.PersonCreateRequest;
import osteam.backland.domain.person.controller.request.SearchByNameRequest;
import osteam.backland.domain.person.controller.request.SearchByPhoneRequest;
import osteam.backland.domain.person.service.PersonCreateService;
import osteam.backland.domain.person.service.PersonSearchService;
import osteam.backland.domain.person.service.PersonUpdateService;
import osteam.backland.domain.person.service.PersonValidationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonCreateService personCreateService;

    @MockBean
    private PersonUpdateService personUpdateService;

    @MockBean
    private PersonSearchService personSearchService;

    @MockBean
    private PersonValidationService personValidationService;

    @Nested
    @DisplayName("예외")
    class exceptCustom {

        @Test
        void longNamePersonTest() throws Exception {
            String longNamePerson = objectMapper
                    .writeValueAsString(new PersonCreateRequest(
                            "teamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                            "01012341234"
                    ));
            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(longNamePerson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void longPhonePersonTest() throws Exception {
            String longPhonePerson = objectMapper
                    .writeValueAsString(new PersonCreateRequest(
                            "team",
                            "010123412341111111111111111111111"
                    ));
            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(longPhonePerson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shortPhonePersonTest() throws Exception {
            String shortPhonePerson = objectMapper
                    .writeValueAsString(new PersonCreateRequest(
                            "team",
                            "010"
                    ));
            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(shortPhonePerson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void nullPersonTest() throws Exception {
            String nullPerson = objectMapper
                    .writeValueAsString(new PersonCreateRequest(
                            null,
                            null
                    ));
            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(nullPerson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void blankPersonTest() throws Exception {
            String blankPerson = objectMapper
                    .writeValueAsString(new PersonCreateRequest(
                            "",
                            ""
                    ));
            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(blankPerson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void nullInputTest() throws Exception {
            String nullInput = objectMapper
                    .writeValueAsString(null);
            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(nullInput))
                    .andExpect(status().isBadRequest());
        }
    }


    @Test
    void changeNamePersonTest() throws Exception {
        String originName = "sos";
        String phone = "01012341234";
        String changeName = "team";

        String successPerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        originName,
                        phone
                ));

        mock.perform(post("/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(successPerson))
                .andExpect(status().isOk())
                .andExpect(content().string("sos"));

        String changePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        changeName,
                        phone
                ));

        mock.perform(post("/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(changePerson))
                .andExpect(status().isOk())
                .andExpect(content().string("team"));
    }

    @Nested
    @DisplayName("성공")
    class success{
        @Test
        void personCreateTest() throws Exception {
            PersonCreateRequest request = new PersonCreateRequest("John", "01011112222");
            String requestBody = objectMapper.writeValueAsString(request);

            mock.perform(post("/person/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().string("John"));
        }

        @Test
        void getPeopleTest() throws Exception {
            mock.perform(get("/person/search-all"))
                    .andExpect(status().isOk());
        }

        @Test
        void getPeopleByNameTest() throws Exception {
            SearchByNameRequest request = new SearchByNameRequest();
            request.setName("John");
            String requestBody = objectMapper.writeValueAsString(request);

            mock.perform(get("/person/name")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        }

        @Test
        void getPeopleByPhoneTest() throws Exception {
            SearchByPhoneRequest request = new SearchByPhoneRequest();
            request.setPhone("01011112222");
            String requestBody = objectMapper.writeValueAsString(request);

            mock.perform(get("/person/phone")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        }
    }
}
