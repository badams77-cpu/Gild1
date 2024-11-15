package com.gildedrose.domain.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class GRTimestamp {

    private LocalDate dt;

    public GRTimestamp(){
        dt = LocalDate.now();
    }

    public LocalDate getDt() {
        return dt;
    }

    public GRTimestamp(java.sql.Date d){
        dt = d==null? null  : d.toLocalDate();
    }

    public void addDay(){
        if (dt!=null){
            dt = dt.plusDays(1);
        }
    }

    public String toString(){
        return dt==null? "null" : dt.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GRTimestamp that = (GRTimestamp) o;
        return Objects.equals(dt, that.dt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dt);
    }
}
