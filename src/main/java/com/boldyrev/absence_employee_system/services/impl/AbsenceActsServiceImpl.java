package com.boldyrev.absence_employee_system.services.impl;

import com.boldyrev.absence_employee_system.exceptions.EmptyDataException;
import com.boldyrev.absence_employee_system.exceptions.EntityNotFoundException;
import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.repositories.AbsenceActsRepository;
import com.boldyrev.absence_employee_system.services.AbsenceActsService;
import com.boldyrev.absence_employee_system.services.AbsentReasonsService;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenceActsServiceImpl implements AbsenceActsService {

    private final AbsenceActsRepository actsRepository;
    private final AbsentReasonsService reasonsService;

    @Autowired
    public AbsenceActsServiceImpl(AbsenceActsRepository actsRepository,
        AbsentReasonsService reasonsService) {
        this.actsRepository = actsRepository;
        this.reasonsService = reasonsService;
    }

    @Override
    public AbsenceAct findById(long id) {
        return actsRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Act by id = %d not found".formatted(id)));
    }

    @Override
    public List<AbsenceAct> findAll() {
        List<AbsenceAct> acts = actsRepository.findAll();

        if (acts.isEmpty()) {
            throw new EmptyDataException("Acts not found");
        }

        acts.sort(Comparator.comparingLong(AbsenceAct::getId));

        return acts;
    }

    @Override
    public AbsenceAct save(AbsenceAct act) {
        return actsRepository.save(enrich(act));
    }

    @Override
    public AbsenceAct update(long id, AbsenceAct act) {
        findById(id);

        return actsRepository.update(id, enrich(act));
    }

    @Override
    public void deleteById(long id) {
        actsRepository.deleteById(id);
    }

    private AbsenceAct enrich(AbsenceAct act) {
        if(act.getReason().getId() == null && act.getReason().getName() != null) {
            act.setReason(reasonsService.findByName(act.getReason().getName()));
        }
        return act;
    }
}
