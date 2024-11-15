package com.gildedrose.controller;

import com.gildedrose.domain.entities.GRTimestamp;
import com.gildedrose.domain.repository.GRTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins="*")
public class GRTimestampController {

    @Autowired
    private GRTimeRepo grTimeRepo;

    @RequestMapping(path="/get_time")
    public GRTimestamp getTime(){
        return grTimeRepo.get();
    }



    @RequestMapping(path="/set_now")
    public void setTimeNow(){
        grTimeRepo.set(LocalDate.now());
    }
}
