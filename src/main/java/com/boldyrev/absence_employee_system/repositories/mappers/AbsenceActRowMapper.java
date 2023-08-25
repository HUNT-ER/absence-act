package com.boldyrev.absence_employee_system.repositories.mappers;

import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AbsenceActRowMapper implements RowMapper<AbsenceAct> {

    @Override
    public AbsenceAct mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AbsenceAct.builder()
            .id(rs.getLong("id"))
            .reason(new AbsentReason(rs.getInt("reason_id"), rs.getString("name")))
            .startDate(rs.getDate("start_date").toLocalDate())
            .duration(rs.getInt("duration"))
            .isDiscounted(rs.getBoolean("discounted"))
            .description(rs.getString("description"))
            .build();
    }
}
