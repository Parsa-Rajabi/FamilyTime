package com.example.jameskoss.cosc341_project;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GlobalDateVariables extends Application {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");
    SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("dd");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    Date currentDate = new Date();
    String dayOfWeek = dayOfWeekFormat.format(currentDate);
    int dayOfMonth = Integer.parseInt(dayOfMonthFormat.format(currentDate));
    int month = Integer.parseInt(monthFormat.format(currentDate));
    int year = Integer.parseInt(yearFormat.format(currentDate));
    int selectedMonth = month;
    int selectedDay = dayOfMonth;
    int selectedWeekday = switchDayOfWeek(dayOfWeek);
    int selectedYear = year;
    String currentMonth = switchMonthToString(selectedMonth); //initial month on load;
    String currentDay = Integer.toString(selectedDay); //initial day on load;
    String currentWeekday = switchDayofWeektoString(selectedWeekday); //initial weekday on load;
    String currentYear = Integer.toString(selectedYear); //initial year on load;
    String selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
    int currentMonthInt = month;
    int nextMonthInt = setNextMonthInt(currentMonthInt);
    int prevMonthInt = setPrevMonthInt(currentMonthInt);
    int daysInMonth = setDaysInMonths(currentMonthInt);
    int daysInPrevMonth = setDaysInPrevMonths(currentMonthInt);
    int sunday = setSunday(selectedWeekday);
    int saturday = setSaturday(selectedWeekday);
    String currentWeek = determineInitialWeek();

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCurrentMonthInt() {
        return currentMonthInt;
    }

    public void setCurrentMonthInt(int currentMonthInt) {
        this.currentMonthInt = currentMonthInt;
    }

    public int getNextMonthInt() {
        return nextMonthInt;
    }

    public int getPrevMonthInt() {
        return prevMonthInt;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public int getDaysInPrevMonth() {
        return daysInPrevMonth;
    }

    public void setDaysInPrevMonth(int daysInPrevMonth) {
        this.daysInPrevMonth = daysInPrevMonth;
    }

    public int getSunday() {
        return sunday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setCurrentWeek(String currentWeek) {
        this.currentWeek = currentWeek;
    }

    public String getCurrentWeek() {
        return currentWeek;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public int getSelectedWeekday() {
        return selectedWeekday;
    }

    public void setSelectedWeekday(int selectedWeekday) {
        this.selectedWeekday = selectedWeekday;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public String getCurrentWeekday() {
        return currentWeekday;
    }

    public void setCurrentWeekday(String currentWeekday) {
        this.currentWeekday = currentWeekday;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public int switchDayOfWeek(String d) {
        switch (d) {
            case "Sunday":
                return 1;
            case "Monday":
                return 2;
            case "Tuesday":
                return 3;
            case "Wednesday":
                return 4;
            case "Thursday":
                return 5;
            case "Friday":
                return 6;
            case "Saturday":
                return 7;
        }
        return 0;
    }

    public String switchMonthToString(int m) {
        switch (m) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return null;
    }

    public String switchDayofWeektoString(int d) {
        switch (d) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return null;
    }

    public String switchShortMonthToString(int m) {
        switch (m) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return null;
    }

    public int setDaysInMonths(int monthInt) {
        switch (monthInt) {
            case 1:
                return 31;
            case 2:
                if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 01111111;
    }

    public int setDaysInPrevMonths(int monthInt) {
        switch (monthInt) {
            case 1:
                return 31;
            case 2:
                return 31;
            case 3:
                if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
                return 31;
            case 5:
                return 30;
            case 6:
                return 31;
            case 7:
                return 30;
            case 8:
                return 31;
            case 9:
                return 31;
            case 10:
                return 30;
            case 11:
                return 31;
            case 12:
                return 30;
        }
        return 022222222;
    }

    public int setSaturday(int selectedWeekday) {
        switch (selectedWeekday) {
            case 1:
                return selectedDay + 6;
            case 2:
                return selectedDay + 5;
            case 3:
                return selectedDay + 4;
            case 4:
                return selectedDay + 3;
            case 5:
                return selectedDay + 2;
            case 6:
                return selectedDay + 1;
            case 7:
                return selectedDay;
        }
        return 0;
    }

    public int setSunday(int selectedWeekday) {
        switch (selectedWeekday) {
            case 1:
                return selectedDay;
            case 2:
                return selectedDay - 1;
            case 3:
                return selectedDay - 2;
            case 4:
                return selectedDay - 3;
            case 5:
                return selectedDay - 4;
            case 6:
                return selectedDay - 5;
            case 7:
                return selectedDay - 6;
        }
        return 0;
    }

    public String determineInitialWeek() {
        String sat;
        String sun;
        String month1;
        String month2;
        if (saturday > daysInMonth) {
            if (currentMonthInt == 12) {
                nextMonthInt = 1;
                month2 = switchShortMonthToString(nextMonthInt);
                saturday = saturday - daysInMonth;
                year++;
            } else {
                nextMonthInt = currentMonthInt + 1;
                saturday = saturday - daysInMonth;
                month2 = switchShortMonthToString(nextMonthInt);
            }
            sat = month2 + " " + saturday;
        } else {
            sat = "" + saturday;
        }
        if (sunday < 1) {
            if (currentMonthInt == 1) {
                prevMonthInt = 12;
                sunday = sunday + daysInPrevMonth;
                month1 = switchShortMonthToString(prevMonthInt);
                year--;
            } else {
                prevMonthInt = currentMonthInt - 1;
                sunday = sunday + daysInPrevMonth;
                month1 = switchShortMonthToString(prevMonthInt);
            }
            sun = month1 + " " + sunday;
            sat = switchShortMonthToString(currentMonthInt) + " " + saturday;

        } else {
            month1 = switchShortMonthToString(currentMonthInt);
            sun = month1 + " " + sunday;
        }
        return sun + "-" + sat;
    }

    public int setNextMonthInt(int currentMonthInt) {
        if (currentMonthInt == 12) {
            return 1;
        } else {
            return currentMonthInt + 1;
        }
    }

    public int setPrevMonthInt(int currentMonthInt) {
        if (currentMonthInt == 1) {
            return 12;
        } else {
            return currentMonthInt - 1;
        }
    }

}
