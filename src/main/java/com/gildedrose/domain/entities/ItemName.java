package com.gildedrose.domain.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum ItemName {

    AGED_BRIE("Aged brie", 1),
    BACKSTAGE_PASSES("Backstage Passes", 0),
    SULFURAS("Sulfuras", 0),
    CONJURED("Conjured", -1),
    NORMAL_ITEM("Normal Item",-1),
    INVALID_ITEM("Invalid Item",0),
    NO_SUCH_ITEM("No Such Item",0);


    private String name;
    private int qualityChangePerDay;

    ItemName(String s, int changePerDay) {
        name = s;
        qualityChangePerDay = changePerDay;
    }

    public static ItemName fromString(String s){
        for(ItemName it : values()){
            if (s.equalsIgnoreCase(it.getName())){
                return it;
            }
        }
        return INVALID_ITEM;
    }

    public static int getDailyChange(Item it, GRTimestamp now){
       if (it.getItemName()==null){
           return 0;
       }
       ItemName itn = it.getItemName();
       if (itn!=BACKSTAGE_PASSES || it.getStartDate()==null){
           return it.getSellIn()<1 ? 2*itn.qualityChangePerDay : itn.getQualityChangePerDay(); // Twice as fast if passed sell by
       }
       // Special Handling for Backstage pass quality
       long days = ChronoUnit.DAYS.between(LocalDate.now(),it.getStartDate().getDt().plusDays(it.getSellIn()));
       if (days< 0L){ return -it.getQuality(); } // Reduce quality to zero
       if (days<5L){ return 3; }
       if (days<10L){ return 2; }
       return 0;
    }

    public String getName() {
        return name;
    }


    public int getQualityChangePerDay() {
        return qualityChangePerDay;
    }

}
