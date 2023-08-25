package com.boldyrev.absence_employee_system.repositories;

import com.boldyrev.absence_employee_system.models.AbsentReason;
import java.util.Optional;

public interface AbsentReasonsRepository {

    Optional<AbsentReason> findByName(String name);
}
