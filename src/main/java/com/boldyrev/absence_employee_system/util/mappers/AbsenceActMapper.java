package com.boldyrev.absence_employee_system.util.mappers;

import com.boldyrev.absence_employee_system.dao.AbsenceActDTO;
import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {AbsentReason.class})
public interface AbsenceActMapper {

    @Mapping(target = "reason", expression = "java(act.getReason().getName())")
    AbsenceActDTO actToActDTO(AbsenceAct act);

    @Mapping(target = "reason", expression = "java(new AbsentReason(null, act.getReason()))")
    AbsenceAct actDTOToAct(AbsenceActDTO act);
}
