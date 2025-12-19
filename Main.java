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
        for (int m = 0; m < MONTHS; m++) {
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
        return "DUMMY";
    }

    public static int totalProfitOnDay(int month, int day) {                //buradayım
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS)
            return -99999;
        int sum = 0;
        for (int i = 0; i < COMMS; i++)
            sum += profits[month][day - 1][i];
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) {                       //buradayım
        int topDay = 1;
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        int maxProfit = 0;
        for (int i = 0; i < DAYS; i++) {
            int sum = 0;
            for (int j = 0; j < COMMS; j++) {
                sum += profits[month][i][j];
            }
            if (i == 0 || sum > maxProfit) {                             //This line was the part that MADE me lose my sanity little by little.
                maxProfit = sum;                                       //The first months total income could be negative. In that case sum
                topDay = i + 1;                                          //could never be bigger than zero. Therefore, the if statement would
            }                                                          //never be used (The initial value wouldn't change). But the real
        }                                                              //"topDay" could be something else (like -5). So we start the
        //profit counter from the sum of first day.

        return topDay;
    }

    //Buradayım
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