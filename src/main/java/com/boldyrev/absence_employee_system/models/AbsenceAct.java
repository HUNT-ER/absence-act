package com.boldyrev.absence_employee_system.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsenceAct {

    private Long id;

    @NotNull
    private AbsentReason reason;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @Min(1)
    private Integer duration;

    @NotNull
    private Boolean isDiscounted;

    @NotBlank
    private String description;
}
