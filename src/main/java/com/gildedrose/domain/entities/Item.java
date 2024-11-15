package com.gildedrose.domain.entities;

import java.util.Objects;

public class Item {

    private static final int MAX_QUALITY = 50;

    private int id;

    private ItemName itemName;

    private GRTimestamp startDate;

    private int sellIn;

    private int quality;

    public ItemName getItemName() {
        return itemName;
    }

    public Item(){}

    public Item(ItemName in, GRTimestamp now, int sellIn, int quality){
        itemName =in;
        startDate = now;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public void changeByOneDay(GRTimestamp now){
        int change = ItemName.getDailyChange(this, now);
        if (itemName==ItemName.INVALID_ITEM){
            itemName = ItemName.NO_SUCH_ITEM;
        }
        if (itemName!=ItemName.SULFURAS){ sellIn = sellIn-1;} // Sulfuras never change sell time
        quality= quality+ change;
        if (itemName==ItemName.CONJURED){
            quality= quality+ change; // degrade twice as fast
        }

        if (quality>MAX_QUALITY){
            quality=MAX_QUALITY;
        }
    }

    public void setItemName(ItemName item) {
        this.itemName = item;
    }

    public GRTimestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(GRTimestamp startDate) {
        this.startDate = startDate;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName=" + itemName +
                ", startDate=" + startDate +
                ", sellIn=" + sellIn +
                ", quality=" + quality +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && sellIn == item.sellIn && quality == item.quality && itemName == item.itemName && Objects.equals(startDate, item.startDate);
    }

    public boolean equalsIgnoreId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return sellIn == item.sellIn && quality == item.quality && itemName == item.itemName && Objects.equals(startDate, item.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, startDate, sellIn, quality);
    }
}
