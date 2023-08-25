package com.boldyrev.absence_employee_system.services;

import com.boldyrev.absence_employee_system.models.AbsentReason;

public interface AbsentReasonsService {

    AbsentReason findByName(String name);
}
