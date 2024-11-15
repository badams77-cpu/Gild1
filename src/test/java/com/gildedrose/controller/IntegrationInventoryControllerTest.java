package com.gildedrose.controller;

import com.gildedrose.DefaultData;

import com.gildedrose.GildedDataSource;
import com.gildedrose.domain.entities.GRTimestamp;
import com.gildedrose.domain.entities.Item;
import com.gildedrose.domain.repository.GRTimeRepo;
import com.gildedrose.domain.repository.ItemRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={InventoryController.class, GRTimestampController.class, GRTimeRepo.class, ItemRepo.class, GildedDataSource.class})
public class IntegrationInventoryControllerTest {

    @Autowired
    InventoryController ic;

    @Autowired
    GRTimestampController grt;

    @Test
   public void testInventoryController(){
        ic.reset();
        List<Item> items = ic.getInventory();
        GRTimestamp startTime = grt.getTime();
        List<Item> correct = DefaultData.getItems(startTime);
        assertEqualItemsList( correct,items);
        ic.dayPassed();
        List<Item> itemsDayOne = DefaultData.getItemsDay(startTime);
        List<Item> actual = ic.getInventory();
        assertEqualItemsList(itemsDayOne, actual);
    }

    private static void assertEqualItemsList(List<Item> intended, List<Item> actual){
        assertEquals(intended.size(), actual.size());
        for(int i=0; i<actual.size(); i++){
            if ( !intended.get(i).equalsIgnoreId(actual.get(i))){
                System.err.println("Actual Item "+i+" differs from intended item "+i);
                System.err.println("\nActual");
                System.err.println(actual.get(i));
                System.err.println("\nIntended");
                System.err.println(intended.get(i));
                assertTrue(false);
            }
        }
    }

}
