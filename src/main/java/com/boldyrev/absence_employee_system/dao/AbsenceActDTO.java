package com.boldyrev.absence_employee_system.dao;

import com.boldyrev.absence_employee_system.dao.transport.NewOrExists;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AbsenceActDTO {

    @Null(groups = {NewOrExists.class})
    private Long id;

    @NotBlank(groups = {NewOrExists.class})
    private String reason;

    @NotNull(groups = {NewOrExists.class})
    private LocalDate startDate;

    @NotNull(groups = {NewOrExists.class})
    @Min(value = 1, groups = {NewOrExists.class})
    private Integer duration;

    @NotNull(groups = {NewOrExists.class})
    private Boolean isDiscounted;

    @NotBlank(groups = {NewOrExists.class})
    private String description;

}
