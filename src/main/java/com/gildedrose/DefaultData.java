package com.gildedrose;

// Quick and dirty returns the default data for the inventory

import com.gildedrose.domain.entities.GRTimestamp;
import com.gildedrose.domain.entities.Item;
import com.gildedrose.domain.entities.ItemName;

import java.util.LinkedList;
import java.util.List;

public class DefaultData {

    public static List<Item> getItems(GRTimestamp now){
        List<Item> items = new LinkedList<Item>();
        items.add(new Item(ItemName.AGED_BRIE,now,1,1));
        items.add(new Item(ItemName.BACKSTAGE_PASSES,now,-1,2));
        items.add(new Item(ItemName.BACKSTAGE_PASSES,now,9,2));
        items.add(new Item(ItemName.SULFURAS,now,2,2));

        items.add(new Item(ItemName.NORMAL_ITEM,now,-1,55));
        items.add(new Item(ItemName.NORMAL_ITEM,now,2,2));
        items.add(new Item(ItemName.INVALID_ITEM,now,2,2));
        items.add(new Item(ItemName.CONJURED,now,2,2));
        items.add(new Item(ItemName.CONJURED,now,-1,5));
        return items;
    }

    public static List<Item> getItemsDay(GRTimestamp now){
        List<Item> items = new LinkedList<Item>();
        items.add(new Item(ItemName.AGED_BRIE,now,0,2));
        items.add(new Item(ItemName.BACKSTAGE_PASSES,now,-2,0));
        items.add(new Item(ItemName.BACKSTAGE_PASSES,now,8,4));
        items.add(new Item(ItemName.SULFURAS,now,2,2));
        items.add(new Item(ItemName.NORMAL_ITEM,now,-2,50));
        items.add(new Item(ItemName.NORMAL_ITEM,now,1,1));
        items.add(new Item(ItemName.NO_SUCH_ITEM,now,1,2));
        items.add(new Item(ItemName.CONJURED,now,1,0));
        items.add(new Item(ItemName.CONJURED,now,-2,1));
        return items;
    }

}
