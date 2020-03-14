package com.gildedrose;

public class TexttestFixture {
    /**
     * - `SellIn`值，表示我们要在多少天之前把物品卖出去，即销售期
     * - `Quality`值，表示物品的品质
     *
     * Rules:
     * - 每天结束时，系统会降低每种物品的这两个数值
     * [test] SellIn > 0, SellIn-- && Quality-- per day
     *
     * - 一旦销售期限过期，品质`Quality`会以双倍速度加速下降
     * [test] SellIn <= 0, Quality-2 per day
     *
     * - 物品的品质`Quality`永远不会为负值
     * [test] Quality > 0
     *
     * - "Aged Brie"的品质`Quality`会随着时间推移而提高
     * [test] Aged Brie: Quality++ per day
     *
     * - 物品的品质`Quality`永远不会超过50
     * [test] Quality <= 50
     *
     * - 传奇物品"Sulfuras"永不到期，也不会降低品质`Quality`
     * [test] Sulfuras: SellIn > 0, Quality will not changed
     *
     * - "Backstage passes"与aged brie类似，其品质`Quality`会随着时间推移而提高；当还剩10天或更少的时候，品质`Quality`每天提高2；当还剩5天或更少的时候，品质`Quality`每天提高3；但一旦过期，品质就会降为0
     * [test] Backstage passes:
     * SellIn > 10, Quality++ per day;
     * SellIn <= 10, Quality+2 per day;
     * SellIn <= 5, Quality+3 per day;
     * SellIn <= 0, Quality=0
     */

    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        Item[] items = new Item[] {
                new Item(NameList.DEXTERITY_VEST, 10, 20), // 背心
                new Item(NameList.AGED_BRIE, 2, 0), // 奶酪
                new Item(NameList.ELIXIR_OF_MONGOOSE, 5, 7), // 脊椎药物
                new Item(NameList.HAND_OF_RAGNAROS, 0, 80), // 萨弗拉斯服，拉格纳罗斯之手
                new Item(NameList.HAND_OF_RAGNAROS, -1, 80),
                new Item(NameList.TAFKAL80ETC_CONCERT, 15, 20), // 摇滚音乐会见面会
                new Item(NameList.TAFKAL80ETC_CONCERT, 10, 49), //
                new Item(NameList.TAFKAL80ETC_CONCERT, 5, 49), //
                // this conjured item does not work properly yet
                new Item(NameList.MANA_CAKE, 3, 6) };

        GildedRose app = new GildedRose(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
