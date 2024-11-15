package com.gildedrose.controller;

import com.gildedrose.DefaultData;
import com.gildedrose.GildedDataSource;
import com.gildedrose.domain.entities.GRTimestamp;
import com.gildedrose.domain.entities.Item;
import com.gildedrose.domain.repository.GRTimeRepo;
import com.gildedrose.domain.repository.ItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private GRTimeRepo grTimeRepo;

    @Autowired
    private ItemRepo itemRepo;

    // A production example would have a security service and pass Authentication to the API


    /*
     *
     */
    @RequestMapping(path="/reset",method= RequestMethod.POST)
    public void reset(){
        grTimeRepo.set(LocalDate.now());
        itemRepo.deleteAll();
        List<Item> items = DefaultData.getItems(grTimeRepo.get());
        for(Item i : items){
            i.setId(itemRepo.create(i));
        }
    }


    /*
     * getInventory /inventory returns the complete inventory for the GildedRose
     */
    @RequestMapping(path="/inventory")
    public List<Item> getInventory(){
        return itemRepo.getAll();
    }



    /*
     * /day_possed update the inventory for quality for the next day
     */
    @RequestMapping(path="/day_passed",method= RequestMethod.POST)
    public void dayPassed(){
        try {
            GRTimestamp gt = grTimeRepo.get();
            gt.addDay();
            grTimeRepo.set(gt.getDt());
            List<Item> items = itemRepo.getAll();
            for (Item i : items) {
                i.changeByOneDay(gt);
                itemRepo.update(i);
            }
        } catch (Exception e){
            LOGGER.warn("Error updating ",e);
        }
    }
}
