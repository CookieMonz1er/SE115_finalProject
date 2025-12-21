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

    public static int[][][] profits = new int[MONTHS][DAYS][COMMS]; //kendime hatırlatma

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {              //Finished no problems
            Scanner reader = null;
            try {
                String fileName = months[m] + ".txt";
                reader = new Scanner(Paths.get("Data_Files", fileName));        // Getting the file path.
                while (reader.hasNextLine()) {
                    String line = reader.nextLine().trim();                         // Trimming to help us not get any errors.

                    if (line.isEmpty()) continue;
                    String[] x = line.split(",");

                    if (x.length != 3) continue;
                    String commStr = x[1].trim();                                   // The string array caused by the "line.split(",")"

                    int day, profit;
                    try {
                        day = Integer.parseInt(x[0].trim());                        // String to integer.
                        profit = Integer.parseInt(x[2].trim());                     // Cross-check in order not to get "NumberFormatException"
                    } catch (NumberFormatException ex) {
                        // If the input is not in the expected form, the line is ignored to prevent any errors that may occur.
                        continue; // If data cannot be parsed, skip this line.
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
                continue;   // In any type of error that may occur (like file not opening) we would not want the program to shut down.
            } finally {
                if (reader != null) reader.close();
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {

        if (month < 0 || month >= MONTHS) {                             // Check
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

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;          //Invalid input
        }
        int sum = 0;
        for (int i = 0; i < COMMS; i++) {
            sum += profits[month][day - 1][i];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {

        if (commodity == null) return -99999;
        if (from < 1 || from > DAYS || to < 1 || to > DAYS) return -99999;
        if (from > to) return -99999;     //Invalid day


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

    public static int bestDayOfMonth(int month) {
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

    public static String bestMonthForCommodity(String comm) {
        int commIndex = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }

        if (commIndex == -1) return "INVALID_COMMODITY";
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


    public static int consecutiveLossDays(String comm) {
        int commIndex = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) return -1;

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


    public static int daysAboveThreshold(String comm, int threshold) {
        int commIndex = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return -1;
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


    public static int biggestDailySwing(int month) {
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