package com.boldyrev.absence_employee_system.dao;

import com.boldyrev.absence_employee_system.dao.transport.NewOrExists;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceActDTO {

    @Null(groups = {NewOrExists.class})
    private Long id;

    @NotBlank(groups = {NewOrExists.class})
    private String reason;

    @NotNull(groups = {NewOrExists.class})
    @JsonProperty("start_date")
    private LocalDate startDate;

    @NotNull(groups = {NewOrExists.class})
    @Min(value = 1, groups = {NewOrExists.class})
    private Integer duration;

    @NotNull(groups = {NewOrExists.class})
    @JsonProperty("discounted")
    private Boolean isDiscounted;

    @NotBlank(groups = {NewOrExists.class})
    private String description;

}
