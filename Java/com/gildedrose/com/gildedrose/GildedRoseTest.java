package com.gildedrose;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    @Test
    public void foo() {
        String name;
        int sellIn;
        int quality;

        /**
         * - 每天结束时，系统会降低每种物品的这两个数值
         * [test] SellIn > 0, SellIn-- && Quality-- per day
         */
        name = NameList.DEXTERITY_VEST;
        sellIn = 1;
        quality = 20;
        performOneTest(name, sellIn, quality, sellIn - 1, quality - 1);

        /**
         * - 一旦销售期限过期，品质`Quality`会以双倍速度加速下降
         * [test] SellIn <= 0, Quality-2 per day
         */
        name = NameList.DEXTERITY_VEST;
        sellIn = 0;
        quality = 20;
        performOneTest(name, sellIn, quality, sellIn - 1, quality - 2);

        /**
         * - 物品的品质`Quality`永远不会为负值
         * [test] Quality > 0
         */
        name = NameList.DEXTERITY_VEST;
        sellIn = 0;
        quality = 0;
        performOneTest(name, sellIn, quality, sellIn - 1, 0);

        /**
         * - "Aged Brie"的品质`Quality`会随着时间推移而提高
         * [test] Aged Brie: Quality++ per day
         */
        name = NameList.AGED_BRIE;
        sellIn = 1;
        quality = 0;
        performOneTest(name, sellIn, quality, sellIn - 1, quality + 1);

        /**
         * - 物品的品质`Quality`永远不会超过50
         * [test] Quality <= 50
         */
        name = NameList.AGED_BRIE;
        sellIn = 1;
        quality = 50;
        performOneTest(name, sellIn, quality, sellIn - 1, 50);

        /**
         * - 传奇物品"Sulfuras"永不到期，也不会降低品质`Quality`
         * [test] Sulfuras: SellIn > 0, Quality will not changed
         */
        name = NameList.HAND_OF_RAGNAROS;
        sellIn = 1;
        quality = 40;
        performOneTest(name, sellIn, quality, sellIn, quality);

        /**
         * - "Backstage passes"与aged brie类似，其品质`Quality`会随着时间推移而提高；当还剩10天或更少的时候，品质`Quality`每天提高2；当还剩5天或更少的时候，品质`Quality`每天提高3；但一旦过期，品质就会降为0
         * [test] Backstage passes:
         * SellIn > 10, Quality++ per day;
         * SellIn <= 10, Quality+2 per day;
         * SellIn <= 5, Quality+3 per day;
         * SellIn <= 0, Quality=0
         */
        name = NameList.TAFKAL80ETC_CONCERT;
        // case 1
        sellIn = 11;
        quality = 20;
        performOneTest(name, sellIn, quality, sellIn - 1, quality + 1);
        // case 2
        sellIn = 10;
        quality = 20;
        performOneTest(name, sellIn, quality, sellIn - 1, quality + 2);
        // case 3
        sellIn = 5;
        quality = 20;
        performOneTest(name, sellIn, quality, sellIn - 1, quality + 3);
        // case 4
        sellIn = 0;
        quality = 20;
        performOneTest(name, sellIn, quality, sellIn - 1, 0);
    }

    void performOneTest(String name, int sellIn, int quality, int sellInWanted, int qualityWanted) {
        Item[] items = new Item[]{new Item(name, sellIn, quality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(name, app.items[0].name);
        assertEquals(sellInWanted, app.items[0].sellIn);
        assertEquals(qualityWanted, app.items[0].quality);
    }

}
