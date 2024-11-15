package com.gildedrose.domain.repository;

import com.gildedrose.domain.entities.GRTimestamp;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GRTimeRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(GRTimeRepo.class);

    private JdbcTemplate jt;
    private NamedParameterJdbcTemplate npjt;


    @Autowired
    public GRTimeRepo(HikariDataSource ds){
        jt = new JdbcTemplate(ds);
        npjt = new NamedParameterJdbcTemplate(ds);
    }

    public int set(LocalDate time){
        GRTimestamp old = get();
        String sql = "UPDATE grtimestamp set grdate = :mydate";
        if (old==null){
            sql = "INSERT INTO grtimestamp (grdate) values (:mydate)";
        }
        Map myMap = new HashMap();
        myMap.put("mydate", java.sql.Date.valueOf(time));
        SqlParameterSource sps = new MapSqlParameterSource(myMap);
        int changes = npjt.update(sql, sps);
        return changes;
    }

    public GRTimestamp get(){
        String sql = "select grdate from grtimestamp;";
        try {
            List<GRTimestamp> out = jt.query(sql, new GRTimestampMapper());
            return out.stream().findFirst().orElse(null);
        } catch (Exception e){
            LOGGER.warn("Exception getting timestamp",  e);
            return null;
        }
    }



    protected class GRTimestampMapper implements RowMapper<GRTimestamp> {

        @Override
        public GRTimestamp mapRow(ResultSet rs, int rowNum) throws SQLException {
            GRTimestamp gr = new GRTimestamp( rs.getDate("grdate"));
            return gr;
        }

    }

}
