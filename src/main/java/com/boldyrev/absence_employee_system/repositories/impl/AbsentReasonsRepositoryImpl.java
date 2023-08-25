package com.boldyrev.absence_employee_system.repositories.impl;

import com.boldyrev.absence_employee_system.exceptions.IncorrectSQLResultException;
import com.boldyrev.absence_employee_system.models.AbsentReason;
import com.boldyrev.absence_employee_system.repositories.AbsentReasonsRepository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class AbsentReasonsRepositoryImpl implements AbsentReasonsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AbsentReasonsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<AbsentReason> findByName(String name) {
        final var SQL = "SELECT id, name FROM t_absent_reason WHERE UPPER(name) = ?";

        log.debug(name.toUpperCase());

        List<AbsentReason> reason = jdbcTemplate.query(SQL,
            new BeanPropertyRowMapper<>(AbsentReason.class), name.toUpperCase());

        log.debug(reason.toString());

        if (reason.isEmpty()) {
            return Optional.empty();
        } else if (reason.size() == 1) {
            return Optional.of(reason.get(0));
        } else {
            log.error("Query '{}' should returns zero or one object", SQL);

            throw new IncorrectSQLResultException(
                "Fail to reject SQL query '%s'. Result list has more then one value".formatted(
                    SQL));
        }
    }
}
