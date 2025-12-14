// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};

    public static int[][][] profits = new int[MONTHS][DAYS][COMMS]; //kendime hatırlatma

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        return "DUMMY";
    }

    public static int totalProfitOnDay(int month, int day) {                //buradayım
        if(month<0 || month>MONTHS || day<1 || day>DAYS)
            return -99999;
        int sum = 0;
        for(int i=0; i<COMMS; i++)
            sum += profits[month][day - 1][i];
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) {                       //buradayım
        int topDay = 1;
        if(month < 0 || month >= MONTHS){
            return -1;
        }
        int maxProfit = 0;
        for(int i = 0; i < DAYS; i++){
            int sum = 0;
            for(int j = 0; j < COMMS; j++){
                sum += profits[month][i][j];
            }
            if(i == 0 || sum > maxProfit){                             //This line was the part that MADE me lose my sanity little by little.
                maxProfit = sum;                                       //The first months total income could be negative. In that case sum
                topDay = i+1;                                          //could never be bigger than zero. Therefore, the if statement would
            }                                                          //never be used (The initial value wouldn't change). But the real
        }                                                              //"topDay" could be something else (like -5). So we start the
        //profit counter from the sum of first day.

        return topDay;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}