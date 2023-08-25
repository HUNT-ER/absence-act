package com.boldyrev.absence_employee_system;

import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import com.boldyrev.absence_employee_system.repositories.AbsenceActsRepository;
import com.boldyrev.absence_employee_system.repositories.AbsentReasonsRepository;
import com.boldyrev.absence_employee_system.services.AbsenceActsService;
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

    @Autowired
    AbsenceActsService actsService;

    @Test
    void contextLoads() {
    }

    @Test
    void contextLoads2() {
        System.out.println(absenceActsRepository.findById(1));

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
