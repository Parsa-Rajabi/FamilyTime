package com.example.jameskoss.cosc341_project;

import android.app.Application;

import java.text.ParseException;
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
    int nextMonthInt;
    int prevMonthInt;
    int daysInMonth;
    int daysInPrevMonth;
    int sunday;
    int saturday;
    String currentWeek;


    public int getNextMonthInt() {
        return nextMonthInt;
    }

    public void switchNextMonthInt(int currentMonthInt) {
        if (currentMonthInt == 12) {
            this.nextMonthInt = 1;
        } else {
            this.nextMonthInt = currentMonthInt + 1;
        }
    }

    public void setNextMonthInt(int nextMonthInt) {
        this.nextMonthInt = nextMonthInt;
    }

    public int getPrevMonthInt() {
        return prevMonthInt;
    }

    public void switchPrevMonthInt(int currentMonthInt) {
        if (currentMonthInt == 1) {
            this.prevMonthInt = 12;
        } else {
            this.prevMonthInt = currentMonthInt - 1;
        }
    }

    public void setPrevMonthInt(int prevMonthInt) {
        this.prevMonthInt = prevMonthInt;
    }

    public int getDaysInPrevMonth() {
        return daysInPrevMonth;
    }

    public void switchDaysInPrevMonth(int monthInt) {
        switch (monthInt) {
            case 1:
                this.daysInPrevMonth = 31;
            case 2:
                this.daysInPrevMonth = 31;
            case 3:
                if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                    this.daysInPrevMonth = 29;
                } else {
                    this.daysInPrevMonth = 28;
                }
            case 4:
                this.daysInPrevMonth = 31;
            case 5:
                this.daysInPrevMonth = 30;
            case 6:
                this.daysInPrevMonth = 31;
            case 7:
                this.daysInPrevMonth = 30;
            case 8:
                this.daysInPrevMonth = 31;
            case 9:
                this.daysInPrevMonth = 31;
            case 10:
                this.daysInPrevMonth = 30;
            case 11:
                this.daysInPrevMonth = 31;
            case 12:
                this.daysInPrevMonth = 30;
        }
    }

    public void setDaysInPrevMonth(int daysInPrevMonth) {
        this.daysInPrevMonth = daysInPrevMonth;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public void switchDaysInMonth(int monthInt) {
        switch (monthInt) {
            case 1:
                this.daysInMonth = 31;
            case 2:
                if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                    this.daysInMonth = 29;
                } else {
                    this.daysInMonth = 28;
                }
            case 3:
                this.daysInMonth = 31;
            case 4:
                this.daysInMonth = 30;
            case 5:
                this.daysInMonth = 31;
            case 6:
                this.daysInMonth = 30;
            case 7:
                this.daysInMonth = 31;
            case 8:
                this.daysInMonth = 31;
            case 9:
                this.daysInMonth = 30;
            case 10:
                this.daysInMonth = 31;
            case 11:
                this.daysInMonth = 30;
            case 12:
                this.daysInMonth = 31;
        }
    }

    public void setDaysInMonth(int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public int getSunday() {
        return sunday;
    }

    public void switchSunday(int selectedWeekday) {
        switch (selectedWeekday) {
            case 1:
                this.sunday = selectedDay;
            case 2:
                this.sunday = selectedDay - 1;
            case 3:
                this.sunday = selectedDay - 2;
            case 4:
                this.sunday = selectedDay - 3;
            case 5:
                this.sunday = selectedDay - 4;
            case 6:
                this.sunday = selectedDay - 5;
            case 7:
                this.sunday = selectedDay - 6;
        }
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void switchSaturday(int selectedWeekday) {
        switch (selectedWeekday) {
            case 1:
                this.saturday = selectedDay + 6;
            case 2:
                this.saturday = selectedDay + 5;
            case 3:
                this.saturday = selectedDay + 4;
            case 4:
                this.saturday = selectedDay + 3;
            case 5:
                this.saturday = selectedDay + 2;
            case 6:
                this.saturday = selectedDay + 1;
            case 7:
                this.saturday = selectedDay;
        }
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public String getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(String currentWeek) {
        this.currentWeek = currentWeek;
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

    public void determineWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");

        String strDate = this.selectedMonth + "-" + this.sunday + "-" + this.selectedYear;
        Date currentDate = null;
        try {
            currentDate = sdf2.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
/*
        int nextSundayDay = c.get(Calendar.DATE);
        int nextSundayMonth = c.get(Calendar.MONTH);
        int nextSundayYear = c.get(Calendar.YEAR);

        c.set(nextSundayYear, nextSundayMonth, nextSundayDay);

        Date nextSundayDate = c.getTime();

        c.setTime(currentDate);*/

        int nextSaturdayDay = c.get(Calendar.DATE);
        int nextSaturdayMonth = c.get(Calendar.MONTH);
        int nextSaturdayYear = c.get(Calendar.YEAR);

        c.set(nextSaturdayYear, nextSaturdayMonth, nextSaturdayDay);
        c.add(Calendar.DAY_OF_MONTH, 6);

        Date nextSaturdayDate = c.getTime();

        String nextSaturdayStr = sdf.format(nextSaturdayDate);
        String currentSundayStr = sdf.format(currentDate);

        String label = currentSundayStr + " - " + nextSaturdayStr;

        this.currentWeek = label;

    }

    public void determineInitialWeek() {
        String sat;
        String sun;
        String month1;
        String month2;
        if (saturday > daysInMonth) {
            if (selectedMonth == 12) {
                nextMonthInt = 1;
                month2 = switchShortMonthToString(nextMonthInt);
                saturday = saturday - daysInMonth;
                year++;
            } else {
                nextMonthInt = selectedMonth + 1;
                saturday = saturday - daysInMonth;
                month2 = switchShortMonthToString(nextMonthInt);
            }
            sat = month2 + " " + saturday;
        } else {
            sat = "" + saturday;
        }
        if (sunday < 1) {
            if (selectedMonth == 1) {
                prevMonthInt = 12;
                sunday = sunday + daysInPrevMonth;
                month1 = switchShortMonthToString(prevMonthInt);
                year--;
            } else {
                prevMonthInt = selectedMonth - 1;
                sunday = sunday + daysInPrevMonth;
                month1 = switchShortMonthToString(prevMonthInt);
            }
            sun = month1 + " " + sunday;
            sat = switchShortMonthToString(selectedMonth) + " " + saturday;

        } else {
            month1 = switchShortMonthToString(selectedMonth);
            sun = month1 + " " + sunday;
        }
        this.currentWeek = sun + "-" + sat;
    }


}
