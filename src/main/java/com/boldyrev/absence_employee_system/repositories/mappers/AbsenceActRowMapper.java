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
        return new AbsenceAct(rs.getLong("id"),
            new AbsentReason(rs.getInt("reason_id"), rs.getString("name")),
            rs.getDate("start_date").toLocalDate(),
            rs.getInt("duration"),
            rs.getBoolean("discounted"), rs.getString("description"));
    }
}
