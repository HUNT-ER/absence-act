package com.boldyrev.absence_employee_system;

import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import com.boldyrev.absence_employee_system.repositories.AbsenceActsRepository;
import com.boldyrev.absence_employee_system.repositories.AbsentReasonsRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Sql("/db/migration/V1__init_database.sql")
@Transactional
@TestPropertySource("classpath:application-test.properties")
class AbsenceEmployeeSystemApplicationTests {

    @Autowired
    AbsenceActsRepository absenceActsRepository;
    @Autowired
    AbsentReasonsRepository absentReasonsRepository;

    @Test
    void contextLoads() {
        AbsenceAct act = AbsenceAct.builder()
            .reason(new AbsentReason(3, ""))
            .startDate(LocalDate.now())
            .duration(4)
            .isDiscounted(false)
            .description("desc")
            .build();
        System.out.println(absenceActsRepository.save(act));
    }

    @Test
    void contextLoads2() {
        System.out.println(absenceActsRepository.findById(1));

        AbsenceAct act = AbsenceAct.builder()
            .reason(new AbsentReason(3, ""))
            .startDate(LocalDate.now())
            .duration(4)
            .isDiscounted(false)
            .description("desc")
            .build();

        System.out.println(absenceActsRepository.update(1, act));

        System.out.println(absenceActsRepository.findById(1));
    }

    @Test
    void contextLoads3() {
        System.out.println(absentReasonsRepository.findByName("Отпуск"));
        System.out.println(absentReasonsRepository.findByName("Прогул"));
        System.out.println(absentReasonsRepository.findByName("Отгул"));
    }

    @Test
    void contextLoads4() {
        System.out.println(absenceActsRepository.findAll());
        System.out.println(absenceActsRepository.findAll());
    }

}
