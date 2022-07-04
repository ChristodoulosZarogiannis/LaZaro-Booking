package date;

/**
 * @author Lazaros Gogos
 * @version 2021.11.20
 */
public class Date {
    // Convention ! The Date is calculated at a day level
    // and the day is calculated starting from the year 2021.
    // Hence, 1.1.2021 is considered day 1.
    // Is it day 1 or day 0 ?
    // Answer: irrelevant. The linear number of days is used in hashsets.

    /**
     * Array is needed for the calculation of the day, based on a date
     * Taken from https://en.wikipedia.org/wiki/Determination_of_the_day_of_the_week#Sakamoto's_methods
     */
    private static final int[] t = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
    /**
     * Used to calculate any given day, and get a single integer.
     */
    private static final int[] daysPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /*
     * Taken from https://en.wikipedia.org/wiki/Determination_of_the_day_of_the_week#Sakamoto's_methods
     *
     * @param y The year
     * @param m The month
     * @param d The day
     * @return Day of week, where 0 = Sunday, 1 = Monday and so on

    private static int getDayOfWeek(int y, int m, int d) {
        if (m < 3)
            y -= 1;
        return (y + y / 4 - y / 100 + y / 400 + t[m - 1] + d) % 7;
    }
     */
    /* @formatter:off
     * the total days, starting from 01.01.0000
     * up until 12.12.2020 are 738171
     * calculated with the following python3 script
     * calculated once, used many times in determining
     * the given day linearly as a number

            year = 2021
            #Objective: get all days up until 12.12.2020
            days = 0
            daysPerMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
            for i in range(year):
                leapYear = False
                if i % 4 == 0:
                    leapYear = True
                    if i % 100 == 0 and i % 400 != 0:
                        leapyear = False
                if leapYear:
                    days += 1
                for j in daysPerMonth:
                    days += j
            print(days)

     * @formatter:on
     */
    // 737805

    /**
     * Calculate the day count (considering time linearly, where day 0 is 01.01.2021), given the YYYY/MM/DD.
     *
     * @param y The year
     * @param m The month
     * @param d The day
     * @return A single integer representing the day count, where day 0 is considered 1.1.2021, used to keep track
     * of whether the property is booked.
     */
    private static int calculateDay(int y, int m, int d) {
        // Guards for out-of-bounds values
        if (y < 2021 ||
                m < 1 || m > 12 ||
                d < 1 || d > 31) {
            return 0;
        }

        int DAYS_UP_TO_12_12_2020 = 738171;
        int n = DAYS_UP_TO_12_12_2020; // initialize with the already calculated number

        // first, count all the days up until before the given year y
        for (int i = 2020; i < y; i++) {
            if (isLeapYear(i))
                n++;
            for (int j : daysPerMonth)
                n += j;
        }
        m--;
        // alright, now that that's calculated, count days up until given month m
        // but is y a leap year? if so, add one to the day count
        if (isLeapYear(y))
            n += 1;
        for (int i = 0; i < m; i++)
            n += daysPerMonth[i];

        // and now add the final days left
        n += d;

        // subtract the initial days (from 01.01.0000 until 12.12.2019
        // this makes the counting start at 01.01.2021
        n -= DAYS_UP_TO_12_12_2020;
        //done
        return n;
    }

    /**
     * Calculate the day count (considering time linearly, where day 0 is 01.01.2021), given the YYYY/MM/DD.
     *
     * @param y The year
     * @param m The month
     * @param d The day
     * @return A single integer representing the day count, where day 0 is considered 1.1.2021, used to keep track
     * of whether the property is booked.
     */
    public static int getDay(int y, int m, int d) {
        return calculateDay(y, m, d);
    }

/*

    @Deprecated
    public static int[] convertToDate(int n) {
        return new int[]{1, 2, 3};
    }
*/

    /**
     * Simple script to calculate whether any given year is a leap year
     *
     * @param y The year
     * @return Whether the given year <b>y</b> is a leap year
     */
    private static boolean isLeapYear(int y) {
        boolean leapYear = false;
        if (y % 4 == 0) {
            leapYear = true;
            if (y % 100 == 0 && y % 400 != 0)
                leapYear = false;
        }
        return leapYear;
    }

    /**
     * Given a year and a month, get the number of days the given month has
     *
     * @param y the year
     * @param m the month
     * @return the number of days this month has
     */
    public static int getTotalDaysOfMonth(int y, int m) {
        m--; // transform from 1-12 month counting to 0-11, for array usage
        if (m == 1 && isLeapYear(y)) {
            return daysPerMonth[m] + 1;
        }
        return daysPerMonth[m];
    }
}
