// Main.java — Students version

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    public static int[][][] profits = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            Scanner reader = null;
            try {
                String fileName = months[m] + ".txt";
                reader = new Scanner(Paths.get("Data_Files", fileName));        // Getting the file path.
                while (reader.hasNextLine()) {
                    String line = reader.nextLine().trim();

                    if (line.isEmpty()) continue;
                    String[] x = line.split(",");

                    if (x.length != 3) continue;
                    String commStr = x[1].trim();

                    int day, profit;
                    try {
                        day = Integer.parseInt(x[0].trim());
                        profit = Integer.parseInt(x[2].trim());
                    } catch (
                            NumberFormatException ex) {                            // Check in order not to get "NumberFormatException"
                        // Day/profit is not a valid integer, skip this line.
                        continue;
                    }

                    int dayIndex = day - 1;
                    if (dayIndex < 0 || dayIndex >= DAYS) continue;

                    int commIndex = -1;
                    for (int i = 0; i < COMMS; i++) {
                        if (commodities[i].equals(commStr)) {
                            commIndex = i;
                            break;
                        }
                    }
                    if (commIndex == -1) continue;

                    profits[m][dayIndex][commIndex] = profit;

                }
            } catch (IOException e) {
                continue;
            } finally {
                if (reader != null) reader.close();
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {   // Returns the most profitable commodity in the given month and its total profit. ("Commodity totalProfit")

        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        int bestCommodityIndex = -1;
        int bestTotal = 0;

        for (int c = 0; c < COMMS; c++) {

            int total = 0;

            for (int d = 0; d < DAYS; d++) {
                total += profits[month][d][c];
            }
            if (bestCommodityIndex == -1 || total > bestTotal) {        // Best on first commodity or comparing totals
                bestTotal = total;
                bestCommodityIndex = c;
            }
        }

        return commodities[bestCommodityIndex] + " " + bestTotal;
    }

    public static int totalProfitOnDay(int month, int day) {           //Returns the total profit for the specified month and day
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;          //Invalid input
        }
        int sum = 0;
        for (int i = 0; i < COMMS; i++) {
            sum += profits[month][day - 1][i];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {  // Returns the yearly total profit for a commodity within range.


        if (commodity == null) return -99999;
        if (from < 1 || from > DAYS || to < 1 || to > DAYS) return -99999;
        if (from > to) return -99999;     //Invalid input


        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1)
            return -99999;  //Invalid commodity


        int sum = 0;

        int start = from - 1;
        int end = to - 1;
        //Looping through all months and through days in range
        for (int m = 0; m < MONTHS; m++) {
            for (int d = start; d <= end; d++) {

                sum += profits[m][d][commIndex];
            }
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {   // Returns the day (1-28) with the highest total profit.

        int topDay = 1;
        if (month < 0 || month >= MONTHS) {
            return -1;              //Invalid month
        }
        int maxProfit = 0;
        for (int i = 0; i < DAYS; i++) {
            int sum = 0;
            for (int j = 0; j < COMMS; j++) {
                sum += profits[month][i][j];
            }
            if (i == 0 || sum > maxProfit) {
                maxProfit = sum;
                topDay = i + 1;
            }
        }


        return topDay;
    }

    public static String bestMonthForCommodity(String comm) {       // Returns the month name with the highest total profit for commodity.
        int commIndex = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }

        if (commIndex == -1) return "INVALID_COMMODITY";            //Invalid input
        int bestMonth = 0;
        int bestProfit = 0;

        for (int m = 0; m < MONTHS; m++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profits[m][d][commIndex];        //Summing daily profits
            }

            if (m == 0 || sum > bestProfit) {
                bestProfit = sum;
                bestMonth = m;
            }
        }
        return months[bestMonth];       //Returns name
    }


    public static int consecutiveLossDays(String comm) {         // Finds the maximum number of consecutive loss days.

        int commIndex = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) return -1;                         //Invalid input

        int currentStreak = 0;
        int longestStreak = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][commIndex] < 0) {
                    currentStreak++;
                } else {
                    currentStreak = 0;
                }

                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                }

            }
        }
        return longestStreak;
    }


    public static int daysAboveThreshold(String comm, int threshold) {      // Returns the number of days when profit > threshold.
        int commIndex = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return -1;      //Invalid input
        }

        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][commIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }


    public static int biggestDailySwing(int month) {    // Returns biggest absolute difference between two consecutive day's total profit
        if (month < 0 || month >= MONTHS) {
            return -99999;              // Invalid month
        }
        int maxDiff = 0;


        for (int d = 0; d < DAYS - 1; d++) {            //Stopping at d < DAYS-1 because we are accessing d+1.


            int totalToday = 0;
            int totalTomorrow = 0;

            for (int c = 0; c < COMMS; c++) {
                totalToday += profits[month][d][c];
                totalTomorrow += profits[month][d + 1][c];
            }
            int diff = totalTomorrow - totalToday;
            if (diff < 0) {
                diff = -diff;                           //Daily swing is defined as |totalTomorrow - totalToday|.
            }
            if (diff > maxDiff) {
                maxDiff = diff;
            }

        }

        return maxDiff;
    }


    public static String compareTwoCommodities(String c1, String c2) { // Compares the yearly total profits of two commodities and returns the result
        int c1Index = -1, c2Index = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) c1Index = i;
            if (commodities[i].equals(c2)) c2Index = i;
        }
        if (c1Index == -1 || c2Index == -1) {
            return "INVALID_COMMODITY";         //Invalid input
        }
        int sum1 = 0;
        int sum2 = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                sum1 += profits[m][d][c1Index];
                sum2 += profits[m][d][c2Index];
            }
        }
        if (sum1 == sum2) {
            return "Equal";
        }
        if (sum1 > sum2) {
            return c1 + " is better by " + (sum1 - sum2);
        } else {
            return c2 + " is better by " + (sum2 - sum1);
        }

    }


    public static String bestWeekOfMonth(int month) {       // Returns which week (1-4) has the highest profit for the selected month.
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";              //Invalid month
        }

        int bestWeek = 1;
        int bestSum = 0;

        for (int d = 0; d < 7; d++) {                   // I wasn't sure if we were allowed to use Integer.MIN_VALUE. So I decided not to take my chances.
            for (int c = 0; c < COMMS; c++) {           //  Getting the first week as a starting point just in case a negative number doesn't break the code.
                bestSum += profits[month][d][c];
            }
        }
        for (int week = 1; week < 4; week++) {

            int initialDayIndex = week * 7;
            int weekSum = 0;

            for (int d = initialDayIndex; d < initialDayIndex + 7; d++) {
                for (int c = 0; c < COMMS; c++) {
                    weekSum += profits[month][d][c];
                }

            }
            if (weekSum > bestSum) {
                bestSum = weekSum;
                bestWeek = week + 1;
            }
        }
        return "Week " + bestWeek;
    }


    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");


    }
}