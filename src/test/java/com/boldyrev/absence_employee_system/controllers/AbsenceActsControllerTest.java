package com.boldyrev.absence_employee_system.controllers;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boldyrev.absence_employee_system.dao.AbsenceActDTO;
import com.boldyrev.absence_employee_system.exceptions.EmptyDataException;
import com.boldyrev.absence_employee_system.exceptions.EntityNotFoundException;
import com.boldyrev.absence_employee_system.services.AbsenceActsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ValidationException;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class AbsenceActsControllerTest {

    private final MockMvc mockMvc;

    @SpyBean
    private final AbsenceActsService actsService;

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper;


    @Autowired
    public AbsenceActsControllerTest(MockMvc mockMvc, AbsenceActsService actsService,
        JdbcTemplate jdbcTemplate) {
        this.mockMvc = mockMvc;
        this.actsService = actsService;
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

    }


    void clearDatabase() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "t_absence_act");
    }

    @Test
    void getAll_ReturnsListActs() throws Exception {

        mockMvc.perform(get("/api/absence-acts"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.body").exists())
            .andExpect(jsonPath("$.body", Matchers.hasSize(7)));

        Mockito.verify(actsService).findAll();
    }

    @Test
    void getAll_ActsListNotFound_ThrowsEmptyDataException() throws Exception {
        clearDatabase();

        mockMvc.perform(get("/api/absence-acts"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", Matchers.equalTo("Acts not found")))
            .andExpect(r -> assertThatExceptionOfType(EmptyDataException.class));

        Mockito.verify(actsService).findAll();
    }

    @Test
    void getById_IdIsExists_ReturnsActDTO() throws Exception {
        final var id = 1;
        mockMvc.perform(get("/api/absence-acts/" + id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.body").exists())
            .andExpect(jsonPath("$.body.id", Matchers.equalTo(1)))
            .andExpect(jsonPath("$.body.reason", Matchers.equalTo("Отпуск")))
            .andExpect(jsonPath("$.body.start_date", Matchers.equalTo("2023-09-04")))
            .andExpect(jsonPath("$.body.duration", Matchers.equalTo(7)))
            .andExpect(jsonPath("$.body.discounted", Matchers.equalTo(true)))
            .andExpect(
                jsonPath("$.body.description", Matchers.equalTo("Ежегодный оплачиваемый отпуск")));

        Mockito.verify(actsService).findById(id);
    }

    @Test
    void getById_IdIsNotExists_ThrowsEntityNotFoundException() throws Exception {
        final var id = 999;
        mockMvc.perform(get("/api/absence-acts/" + id))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", Matchers.equalTo("Act by id = 999 not found")))
            .andExpect(r -> assertThatExceptionOfType(EntityNotFoundException.class));

        Mockito.verify(actsService).findById(id);
    }


    @Test
    void create_AbsenceActDTOIsValid_SavesAuthor() throws Exception {
        AbsenceActDTO act = new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 8, 25), 7, true,
            "Ежегодный отпуск");

        mockMvc.perform(post("/api/absence-acts")
                .content(mapper.writeValueAsString(act))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.body").exists())
            .andExpect(jsonPath("$.body.id").isNumber())
            .andExpect(jsonPath("$.body.reason", Matchers.equalTo("Отпуск")))
            .andExpect(jsonPath("$.body.start_date", Matchers.equalTo("2023-08-25")))
            .andExpect(jsonPath("$.body.duration", Matchers.equalTo(7)))
            .andExpect(jsonPath("$.body.discounted", Matchers.equalTo(true)))
            .andExpect(
                jsonPath("$.body.description", Matchers.equalTo("Ежегодный отпуск")));

        Mockito.verify(actsService).save(Mockito.any());
    }

    @ParameterizedTest
    @MethodSource("getInvalidActs")
    void create_AbsenceActDTOInvalid_ThrowsValidationException(AbsenceActDTO act) throws Exception {
        mockMvc.perform(post("/api/absence-acts")
                .content(mapper.writeValueAsString(act))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(r -> assertThatExceptionOfType(ValidationException.class));

        Mockito.verifyNoInteractions(actsService);
    }


    public static Stream<AbsenceActDTO> getInvalidActs() {
        return Stream.of(
            new AbsenceActDTO(1l, "Отпуск", LocalDate.of(2023, 9, 4), 7, true, "Ежегодный отпуск"),
            new AbsenceActDTO(null, null, LocalDate.of(2023, 9, 4), 7, true, "Ежегодный отпуск"),
            new AbsenceActDTO(null, "     ", LocalDate.of(2023, 9, 4), 7, true, "Ежегодный отпуск"),
            new AbsenceActDTO(null, "Отпуск", null, 7, true, "Ежегодный отпуск"),
            new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 9, 4), null, true,
                "Ежегодный отпуск"),
            new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 9, 4), 0, true,
                "Ежегодный отпуск"),
            new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 9, 4), 7, null,
                "Ежегодный отпуск"),
            new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 9, 4), 7, true, null),
            new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 9, 4), 7, true, "    ")
        );
    }

    @Test
    void updateById_AbsenceActDTOAndIDIsValid_UpdatesAuthor() throws Exception {
        AbsenceActDTO act = new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 8, 25), 7, true,
            "Ежегодный отпуск");
        final var id = 1;

        mockMvc.perform(put("/api/absence-acts/" + id)
                .content(mapper.writeValueAsString(act))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.body").exists())
            .andExpect(jsonPath("$.body.id", Matchers.equalTo(id)))
            .andExpect(jsonPath("$.body.reason", Matchers.equalTo("Отпуск")))
            .andExpect(jsonPath("$.body.start_date", Matchers.equalTo("2023-08-25")))
            .andExpect(jsonPath("$.body.duration", Matchers.equalTo(7)))
            .andExpect(jsonPath("$.body.discounted", Matchers.equalTo(true)))
            .andExpect(
                jsonPath("$.body.description", Matchers.equalTo("Ежегодный отпуск")));
    }

    @Test
    void updateById_AbsenceActDTOIsValidAndIdNotExists_ThrowsEntityNotFoundException()
        throws Exception {
        AbsenceActDTO act = new AbsenceActDTO(null, "Отпуск", LocalDate.of(2023, 8, 25), 7, true,
            "Ежегодный отпуск");
        final var id = 999;

        mockMvc.perform(put("/api/absence-acts/" + id)
                .content(mapper.writeValueAsString(act))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", Matchers.equalTo("Act by id = 999 not found")))
            .andExpect(r -> assertThatExceptionOfType(EntityNotFoundException.class));
    }

    @ParameterizedTest
    @MethodSource("getInvalidActs")
    void updateById_AbsenceActDTOIsInvalid_ThrowsEntityNotFoundException(AbsenceActDTO act)
        throws Exception {
        final var id = 1;
        mockMvc.perform(put("/api/absence-acts/" + id)
                .content(mapper.writeValueAsString(act))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(r -> assertThatExceptionOfType(ValidationException.class));
    }

    @Test
    void deleteById_ReturnsMessage() throws Exception {
        int id = 1;
        mockMvc.perform(delete("/api/absence-acts/" + id))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", Matchers.equalTo("Absence act was delete or not exists")));
    }
}

