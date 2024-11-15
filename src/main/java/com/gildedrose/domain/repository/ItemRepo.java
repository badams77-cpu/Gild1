package com.gildedrose.domain.repository;

import com.gildedrose.domain.entities.GRTimestamp;
import com.gildedrose.domain.entities.Item;
import com.gildedrose.domain.entities.ItemName;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRepo.class);

    private JdbcTemplate jt;
    private NamedParameterJdbcTemplate npjt;



    @Autowired
    public ItemRepo(HikariDataSource ds){
        jt = new JdbcTemplate(ds);
        npjt = new NamedParameterJdbcTemplate(ds);
    }


    public int deleteAll( ){
        String sql = "DELETE from inventory WHERE 1=1";
        int changes = jt.update(sql);
        return changes;
    }


    public int create( Item item){
        String sql = "INSERT INTO inventory (item_name, start_date, sell_in, quality) values (" +
                ":item_name, :start_date, :sell_in, :quality)";
     //   if (old==null){
     //       sql = "INSERT INFO grtimestamp (grdate) values (:mydate)";
    //    }
        Map myMap = new HashMap();
        myMap.put("item_name", item.getItemName().getName());
        myMap.put("start_date", java.sql.Date.valueOf(item.getStartDate().getDt()));
        myMap.put("sell_in", item.getSellIn());
        myMap.put("quality", item.getQuality());
        SqlParameterSource sps = new MapSqlParameterSource(myMap);
        KeyHolder kh = new GeneratedKeyHolder();
        int changes = npjt.update(sql, sps, kh);
        return kh.getKey()==null ? 0 : kh.getKey().intValue();
    }


    public int update( Item item){
        if (item.getId()==0){
            LOGGER.warn("Trying to update into 0");
        }
        String sql = "UPDATE inventory SET item_name=:item_name, start_date=:start_date," +
                " sell_in=:sell_in, quality=:quality WHERE id=:id;";
        //   if (old==null){
        //       sql = "INSERT INFO grtimestamp (grdate) values (:mydate)";
        //    }
        Map myMap = new HashMap();
        myMap.put("item_name", item.getItemName().getName());
        myMap.put("start_date", java.sql.Date.valueOf(item.getStartDate().getDt()));
        myMap.put("sell_in", item.getSellIn());
        myMap.put("quality", item.getQuality());
        myMap.put("id", item.getId());
        SqlParameterSource sps = new MapSqlParameterSource(myMap);
       // KeyHolder kh = new GeneratedKeyHolder();
        int changes = npjt.update(sql, sps);
        return changes;
    }

    public Optional<Item> get(int id){
        String sql = "select * from inventory where id=?";

        try {
            List<Item> out = jt.query(sql, new Object[]{ id} ,new ItemMapper());
            return out.stream().findFirst();
        } catch (Exception e){
            LOGGER.warn("Exception getting timestamp",  e);
            return null;
        }
    }


    public List<Item> getAll(){
        String sql = "select * from inventory;";
        try {
            List<Item> out = jt.query(sql ,new ItemMapper());
            return out;
        } catch (Exception e){
            LOGGER.warn("Exception getting timestamp",  e);
            return null;
        }
    }


    protected class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item it = new Item();
            it.setId(rs.getInt("id"));
            it.setQuality(rs.getInt("quality"));
            it.setSellIn(rs.getInt("sell_in"));
            it.setItemName( ItemName.fromString( rs.getString("item_name")));
            it.setStartDate( new GRTimestamp(rs.getDate("start_date")));
            return it;
        }

    }

}
