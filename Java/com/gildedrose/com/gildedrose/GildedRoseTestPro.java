package com.gildedrose;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GildedRoseTestPro {

    private final ArrayList<TestCase> mTestCaseList = new ArrayList<>();

    {
        // normal case
        mTestCaseList.add(new NormalCase(NameList.DEXTERITY_VEST, 1, 20));
        mTestCaseList.add(new NormalCase(NameList.ELIXIR_OF_MONGOOSE, 1, 20));
        mTestCaseList.add(new NormalCase(NameList.MANA_CAKE, 1, 20));

        mTestCaseList.add(new NormalCase(NameList.DEXTERITY_VEST, 0, 20));
        mTestCaseList.add(new NormalCase(NameList.ELIXIR_OF_MONGOOSE, 0, 20));
        mTestCaseList.add(new NormalCase(NameList.MANA_CAKE, 0, 20));

        mTestCaseList.add(new NormalCase(NameList.DEXTERITY_VEST, 0, 0));
        mTestCaseList.add(new NormalCase(NameList.ELIXIR_OF_MONGOOSE, 0, 0));
        mTestCaseList.add(new NormalCase(NameList.MANA_CAKE, 0, 0));

        // aged brie case
        mTestCaseList.add(new AgedBrieCase(NameList.AGED_BRIE, 1, 0));
        mTestCaseList.add(new AgedBrieCase(NameList.AGED_BRIE, 1, 50));

        // hand of ragnaros case
        mTestCaseList.add(new HandOfRagnarosCase(NameList.HAND_OF_RAGNAROS, 1, 40));

        // hand of ragnaros case
        mTestCaseList.add(new Tafkal80etcConcertCase(NameList.TAFKAL80ETC_CONCERT, 11, 20));
        mTestCaseList.add(new Tafkal80etcConcertCase(NameList.TAFKAL80ETC_CONCERT, 10, 20));
        mTestCaseList.add(new Tafkal80etcConcertCase(NameList.TAFKAL80ETC_CONCERT, 5, 20));
        mTestCaseList.add(new Tafkal80etcConcertCase(NameList.TAFKAL80ETC_CONCERT, 0, 20));
    }
    
    @Test
    public void foo() {
        for (TestCase item : mTestCaseList) {
            item.startTesting();
        }
    }

    abstract static class TestCase {

        String testName;
        int testSellIn;
        int testQuality;

        public TestCase(String testName, int testSellIn, int testQuality) {
            this.testName = testName;
            this.testSellIn = testSellIn;
            this.testQuality = testQuality;
        }

        public void startTesting() {
            StringBuilder sb = new StringBuilder();
            sb.append("class: ");
            sb.append(getClass().getSimpleName());
            sb.append(", name: ");
            sb.append(testName);
            sb.append(": ");
            sb.append(", sellIn: ");
            sb.append(testSellIn);
            sb.append(", qualityIn: ");
            sb.append(testQuality);
            System.out.println(sb.toString());
            testing(testName, testSellIn, testQuality);
        }

        abstract void testing(String name, int sellIn, int quality);
    }

    class NormalCase extends TestCase {

        public NormalCase(String testName, int testSellIn, int testQuality) {
            super(testName, testSellIn, testQuality);
        }

        @Override
        void testing(String name, int sellIn, int quality) {
            int sellInWanted;
            int qualityWanted;

            if (sellIn > 0) {
                /**
                 * - 每天结束时，系统会降低每种物品的这两个数值
                 * [test] SellIn > 0, SellIn-- && Quality-- per day
                 */
                sellInWanted = sellIn - 1;
                qualityWanted = quality - 1;
            } else {
                /**
                 * - 一旦销售期限过期，品质`Quality`会以双倍速度加速下降
                 * [test] SellIn <= 0, Quality-2 per day
                 */
                sellInWanted = sellIn - 1;
                qualityWanted = quality - 2;
            }

            /**
             * - 物品的品质`Quality`永远不会为负值
             * [test] Quality > 0
             */
            if (qualityWanted <= 0) {
                qualityWanted = 0;
            }

            performOneTest(name, sellIn, quality, sellInWanted, qualityWanted);
        }
    }

    class AgedBrieCase extends TestCase {

        public AgedBrieCase(String testName, int testSellIn, int testQuality) {
            super(testName, testSellIn, testQuality);
        }

        @Override
        void testing(String name, int sellIn, int quality) {
            int sellInWanted;
            int qualityWanted;

            if (!NameList.AGED_BRIE.equals(name)) {
                System.out.println("Wrong usage!");
            }

            /**
             * - "Aged Brie"的品质`Quality`会随着时间推移而提高
             * [test] Aged Brie: Quality++ per day
             */
            sellInWanted = sellIn - 1;
            qualityWanted = quality + 1;

            /**
             * - 物品的品质`Quality`永远不会超过50
             * [test] Quality <= 50
             */
            if (qualityWanted > 50) {
                qualityWanted = 50;
            }

            performOneTest(name, sellIn, quality, sellInWanted, qualityWanted);
        }
    }

    class HandOfRagnarosCase extends TestCase {

        public HandOfRagnarosCase(String testName, int testSellIn, int testQuality) {
            super(testName, testSellIn, testQuality);
        }

        @Override
        void testing(String name, int sellIn, int quality) {
            int sellInWanted;
            int qualityWanted;

            if (!NameList.HAND_OF_RAGNAROS.equals(name)) {
                System.out.println("Wrong usage!");
            }

            /**
             * - 传奇物品"Sulfuras"永不到期，也不会降低品质`Quality`
             * [test] Sulfuras: SellIn > 0, Quality will not changed
             */
            sellInWanted = sellIn;
            qualityWanted = quality;
            performOneTest(name, sellIn, quality, sellInWanted, qualityWanted);
        }
    }

    class Tafkal80etcConcertCase extends TestCase {

        public Tafkal80etcConcertCase(String testName, int testSellIn, int testQuality) {
            super(testName, testSellIn, testQuality);
        }

        @Override
        void testing(String name, int sellIn, int quality) {
            int sellInWanted;
            int qualityWanted;

            if (!NameList.TAFKAL80ETC_CONCERT.equals(name)) {
                System.out.println("Wrong usage!");
            }

            /**
             * - "Backstage passes"与aged brie类似，其品质`Quality`会随着时间推移而提高；当还剩10天或更少的时候，品质`Quality`每天提高2；当还剩5天或更少的时候，品质`Quality`每天提高3；但一旦过期，品质就会降为0
             * [test] Backstage passes:
             * SellIn > 10, Quality++ per day;
             * SellIn <= 10, Quality+2 per day;
             * SellIn <= 5, Quality+3 per day;
             * SellIn <= 0, Quality=0
             */
            sellInWanted = sellIn - 1;
            if (sellIn > 10) {
                qualityWanted = quality + 1;
            } else if (sellIn > 5) {
                qualityWanted = quality + 2;
            } else if (sellIn > 0) {
                qualityWanted = quality + 3;
            } else {
                qualityWanted = 0;
            }

            performOneTest(name, sellIn, quality, sellInWanted, qualityWanted);
        }
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
