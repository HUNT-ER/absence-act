package com.boldyrev.absence_employee_system.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.boldyrev.absence_employee_system.dao.AbsenceActDTO;
import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import com.boldyrev.absence_employee_system.util.mappers.AbsenceActMapper;
import com.boldyrev.absence_employee_system.util.mappers.AbsenceActMapperImpl;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class AbsenceActMapperTest {

    private final AbsenceActMapper mapper = new AbsenceActMapperImpl();

    @Test
    void shouldCorrectConvertActToDTO() {
        AbsenceAct act = new AbsenceAct(null, new AbsentReason(1, "ќтпуск"), LocalDate.of(2023, 8, 25), 7, true,
            "≈жегодный отпуск");
        AbsenceActDTO actDTO = mapper.actToActDTO(act);

        assertThat(actDTO.getId()).isNull();
        assertThat(act.getReason().getName()).isEqualTo(actDTO.getReason());
        assertThat(act.getStartDate()).isEqualTo(actDTO.getStartDate());
        assertThat(act.getDuration()).isEqualTo(actDTO.getDuration());
        assertThat(act.getIsDiscounted()).isEqualTo(actDTO.getIsDiscounted());
        assertThat(act.getDescription()).isEqualTo(actDTO.getDescription());
    }

    @Test
    void shouldCorrectConvertDTOToAct() {
        AbsenceActDTO actDTO = new AbsenceActDTO(null, "ќтпуск", LocalDate.of(2023, 8, 25), 7, true,
            "≈жегодный отпуск");
        AbsenceAct act = mapper.actDTOToAct(actDTO);

        assertThat(act.getId()).isNull();
        assertThat(act.getReason().getId()).isNull();
        assertThat(act.getReason().getName()).isEqualTo(actDTO.getReason());
        assertThat(act.getStartDate()).isEqualTo(actDTO.getStartDate());
        assertThat(act.getDuration()).isEqualTo(actDTO.getDuration());
        assertThat(act.getIsDiscounted()).isEqualTo(actDTO.getIsDiscounted());
        assertThat(act.getDescription()).isEqualTo(actDTO.getDescription());
    }

}
