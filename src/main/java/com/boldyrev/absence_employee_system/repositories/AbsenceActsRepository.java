package com.boldyrev.absence_employee_system.repositories;

import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;

public interface AbsenceActsRepository {

    Optional<AbsenceAct> findById(long id);

    List<AbsenceAct> findAll();

    AbsenceAct save(AbsenceAct act);

    AbsenceAct update(long id, AbsenceAct act);

    void deleteById(long id);
}
