package com.boldyrev.absence_employee_system.services;

import com.boldyrev.absence_employee_system.models.AbsenceAct;
import java.util.List;
import java.util.Optional;

public interface AbsenceActsService {

    AbsenceAct findById(long id);

    List<AbsenceAct> findAll();

    AbsenceAct save(AbsenceAct act);

    AbsenceAct update(long id, AbsenceAct act);

    void deleteById(long id);
}
