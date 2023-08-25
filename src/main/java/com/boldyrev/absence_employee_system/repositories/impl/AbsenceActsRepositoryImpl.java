package com.boldyrev.absence_employee_system.repositories.impl;

import com.boldyrev.absence_employee_system.exceptions.IncorrectSQLResultException;
import com.boldyrev.absence_employee_system.models.AbsenceAct;
import com.boldyrev.absence_employee_system.repositories.AbsenceActsRepository;
import com.boldyrev.absence_employee_system.repositories.mappers.AbsenceActRowMapper;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class AbsenceActsRepositoryImpl implements AbsenceActsRepository {

    private final AbsenceActRowMapper mapper;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AbsenceActsRepositoryImpl(AbsenceActRowMapper mapper, JdbcTemplate jdbcTemplate) {
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<AbsenceAct> findById(long id) {
        final var SQL = "SELECT a.*, r.name FROM t_absence_act a INNER JOIN t_absent_reason r ON r.id = a.reason_id WHERE a.id = ?;";
        List<AbsenceAct> act = jdbcTemplate.query(SQL, mapper, id);

        if (act.isEmpty()) {
            return Optional.empty();
        } else if (act.size() == 1) {
            return Optional.of(act.get(0));
        } else {
            log.error("Query '{}' should returns zero or one object", SQL);

            throw new IncorrectSQLResultException(
                "Fail to reject SQL query '%s'. Result list has more then one value".formatted(
                    SQL));
        }
    }

    @Override
    public List<AbsenceAct> findAll() {
        final var SQL = "SELECT a.*, r.name FROM t_absence_act a INNER JOIN t_absent_reason r ON r.id = a.reason_id;";

        return jdbcTemplate.query(SQL, mapper);
    }

    @Override
    public AbsenceAct save(AbsenceAct act) {
        final var SQL = "INSERT INTO t_absence_act(reason_id, start_date, duration, discounted, description) VALUES(?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
            ps.setInt(1, act.getReason().getId());
            ps.setDate(2, Date.valueOf(act.getStartDate()));
            ps.setInt(3, act.getDuration());
            ps.setBoolean(4, act.getIsDiscounted());
            ps.setString(5, act.getDescription());

            return ps;
        }, keyHolder);

        act.setId(keyHolder.getKey().longValue());

        return act;
    }

    @Override
    public AbsenceAct update(long id, AbsenceAct act) {
        final var SQL = "UPDATE t_absence_act SET reason_id = ?, start_date = ?, duration = ?, discounted = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(SQL, act.getReason().getId(), Date.valueOf(act.getStartDate()),
            act.getDuration(), act.getIsDiscounted(), act.getDescription(), id);

        act.setId(id);

        return act;
    }

    @Override
    public void deleteById(long id) {
        final var SQL = "DELETE FROM t_absence_act WHERE id = ?";

        jdbcTemplate.update(SQL, id);
    }
}
