package com.boldyrev.absence_employee_system.services.impl;

import com.boldyrev.absence_employee_system.exceptions.EntityNotFoundException;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import com.boldyrev.absence_employee_system.repositories.AbsentReasonsRepository;
import com.boldyrev.absence_employee_system.services.AbsentReasonsService;
import org.springframework.stereotype.Service;

@Service
public class AbsentReasonsServiceImpl implements AbsentReasonsService {

    private final AbsentReasonsRepository reasonsRepository;

    public AbsentReasonsServiceImpl(AbsentReasonsRepository reasonsRepository) {
        this.reasonsRepository = reasonsRepository;
    }

    @Override
    public AbsentReason findByName(String name) {
        return reasonsRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(
            "Absent reason with name = '%s' not found".formatted(name)));
    }

}
