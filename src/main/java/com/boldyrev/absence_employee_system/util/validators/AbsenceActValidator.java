package com.boldyrev.absence_employee_system.util.validators;

import com.boldyrev.absence_employee_system.dao.AbsenceActDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AbsenceActValidator extends CustomValidator {

    protected AbsenceActValidator() {
        super("Absence act");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AbsenceActDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(getErrors(errors));
        }
    }
}
