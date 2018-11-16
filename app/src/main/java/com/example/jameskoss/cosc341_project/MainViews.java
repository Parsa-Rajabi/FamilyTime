package com.example.jameskoss.cosc341_project;


import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainViews extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dayofweekformat = new SimpleDateFormat("EE");
    SimpleDateFormat dayofmonthformat = new SimpleDateFormat("dd");
    SimpleDateFormat monthformat = new SimpleDateFormat("MM");
    SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
    Date selectedDate = new Date();
    String dayOfWeek = dayofweekformat.format(selectedDate);
    int dayOfMonth = Integer.parseInt(dayofmonthformat.format(selectedDate));
    int month = Integer.parseInt(monthformat.format(selectedDate));
    int year = Integer.parseInt(yearformat.format(selectedDate));
    String currentMonth;
    String currentDay;
    String currentWeekday;
    String currentYear;
    String theDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_views);
        loadDayButton();
        loadWeekButton();
        loadMonthButton();
        setDayViewDay();
        nextDayButton();
        previousDayButton();

    }

    private void nextDayButton() { //goes to day view when pressed
        ImageButton button = findViewById(R.id.nextday);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

    }

    private void previousDayButton() { //goes to day view when pressed
        ImageButton button = findViewById(R.id.previousday);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineDate(v);
            }
        });

    }

    private void loadDayButton(){ //goes to day view when pressed
        Button button = findViewById(R.id.dayButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeFragment(v);
            }
        });

    }

    private void loadWeekButton(){ //goes to week view when pressed
        Button button = findViewById(R.id.weekButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeFragment(v);
            }
        });

    }

    private void loadMonthButton(){ //goes to month view when pressed
        Button button = findViewById(R.id.monthButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeFragment(v);
            }
        });

    }

    private void determineDate(View v) {
        Toast.makeText(getApplicationContext(), ""+month, Toast.LENGTH_SHORT).show();


        if (v == findViewById(R.id.nextday)) {
            switch (month) {
                case 1:
                    if (dayOfMonth == 31) {
                        currentMonth = "February";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "January";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 2: //this needs work for leap years
                    if (year / 4 == 0 || (year / 100 == 0 && year / 400 == 0)) {
                        if (dayOfMonth == 29) {
                            currentMonth = "March";
                            currentWeekday = switchNextDayofWeek(dayOfWeek);
                            currentYear = Integer.toString(year);
                            currentDay = "1";
                        } else {
                            dayOfMonth++;
                            currentMonth = "February";
                            currentWeekday = switchNextDayofWeek(dayOfWeek);
                            currentDay = Integer.toString(dayOfMonth);
                            currentYear = Integer.toString(year);
                        }
                    } else {
                        if (dayOfMonth == 28) {
                            currentMonth = "March";
                            currentWeekday = switchNextDayofWeek(dayOfWeek);
                            currentYear = Integer.toString(year);
                            currentDay = "1";
                        } else {
                            dayOfMonth++;
                            currentMonth = "February";
                            currentWeekday = switchNextDayofWeek(dayOfWeek);
                            currentDay = Integer.toString(dayOfMonth);
                            currentYear = Integer.toString(year);
                        }
                    }
                case 3:
                    if (dayOfMonth == 31) {
                        currentMonth = "April";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "March";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 4:
                    if (dayOfMonth == 30) {
                        currentMonth = "May";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "April";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 5:
                    if (dayOfMonth == 31) {
                        currentMonth = "June";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "May";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 6:
                    if (dayOfMonth == 30) {
                        currentMonth = "July";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "June";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 7:
                    if (dayOfMonth == 31) {
                        currentMonth = "August";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "July";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 8:
                    if (dayOfMonth == 31) {
                        currentMonth = "September";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "August";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 9:
                    if (dayOfMonth == 30) {
                        currentMonth = "October";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "September";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 10:
                    if (dayOfMonth == 31) {
                        currentMonth = "November";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "October";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 11:
                    if (dayOfMonth == 30) {
                        currentMonth = "December";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth++;
                        currentMonth = "November";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 12:
                    if (dayOfMonth == 31) {
                        currentMonth = "January";
                        currentDay = "1";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year++);
                    } else {
                        dayOfMonth++;
                        currentMonth = "December";
                        currentWeekday = switchNextDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
            }
        }

        if (v == findViewById(R.id.previousday)) {
            switch (month) {
                case 1:
                    if (dayOfMonth == 1) {
                        currentMonth = "December";
                        currentDay = "31";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year--);
                    } else {
                        dayOfMonth--;
                        currentMonth = "January";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 2: //this needs work for leap years
                    if (dayOfMonth == 1) {
                        currentMonth = "January";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                        currentDay = "31";
                    } else {
                        dayOfMonth--;
                        currentMonth = "February";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 3:
                    if (year / 4 == 0 || (year / 100 == 0 && year / 400 == 0)) {
                        if (dayOfMonth == 1) {
                            currentMonth = "February";
                            currentDay = "29";
                            currentWeekday = switchPrevDayofWeek(dayOfWeek);
                            currentYear = Integer.toString(year);
                        } else {
                            dayOfMonth--;
                            currentMonth = "March";
                            currentWeekday = switchPrevDayofWeek(dayOfWeek);
                            currentDay = Integer.toString(dayOfMonth);
                            currentYear = Integer.toString(year);
                        }
                    } else {
                        if (dayOfMonth == 1) {
                            currentMonth = "February";
                            currentWeekday = switchPrevDayofWeek(dayOfWeek);
                            currentYear = Integer.toString(year);
                            currentDay = "28";
                        } else {
                            dayOfMonth--;
                            currentMonth = "March";
                            currentWeekday = switchPrevDayofWeek(dayOfWeek);
                            currentDay = Integer.toString(dayOfMonth);
                            currentYear = Integer.toString(year);
                        }
                    }
                case 4:
                    if (dayOfMonth == 1) {
                        currentMonth = "March";
                        currentDay = "31";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "April";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 5:
                    if (dayOfMonth == 1) {
                        currentMonth = "April";
                        currentDay = "30";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "May";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 6:
                    if (dayOfMonth == 1) {
                        currentMonth = "May";
                        currentDay = "31";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "June";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 7:
                    if (dayOfMonth == 1) {
                        currentMonth = "June";
                        currentDay = "30";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "July";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 8:
                    if (dayOfMonth == 1) {
                        currentMonth = "July";
                        currentDay = "31";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "August";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 9:
                    if (dayOfMonth == 1) {
                        currentMonth = "August";
                        currentDay = "31";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "September";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 10:
                    if (dayOfMonth == 1) {
                        currentMonth = "September";
                        currentDay = "30";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "October";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 11:
                    if (dayOfMonth == 1) {
                        currentMonth = "October";
                        currentDay = "31";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year);
                    } else {
                        dayOfMonth--;
                        currentMonth = "November";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
                case 12:
                    if (dayOfMonth == 1) {
                        currentMonth = "November";
                        currentDay = "30";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentYear = Integer.toString(year++);
                    } else {
                        dayOfMonth--;
                        currentMonth = "December";
                        currentWeekday = switchPrevDayofWeek(dayOfWeek);
                        currentDay = Integer.toString(dayOfMonth);
                        currentYear = Integer.toString(year);
                    }
            }
        }
        TextView cday = findViewById(R.id.currentDay);
        theDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        cday.setText(theDate);
    }


    public void changeFragment(View v){
        Fragment frag;


        if(v==findViewById(R.id.dayButton)){
            frag = new DayFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlacement, frag);
            Button daybutton = findViewById(R.id.dayButton);
            daybutton.setTypeface(daybutton.getTypeface(), Typeface.BOLD);
            Button weekbutton = findViewById(R.id.weekButton);
            weekbutton.setTypeface(null, Typeface.NORMAL);
            Button monthbutton = findViewById(R.id.monthButton);
            monthbutton.setTypeface(null, Typeface.NORMAL);
            ft.commit();
        }

        if(v==findViewById(R.id.weekButton)){
            frag = new WeekFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlacement, frag);
            Button daybutton = findViewById(R.id.dayButton);
            daybutton.setTypeface(null, Typeface.NORMAL);
            Button weekbutton = findViewById(R.id.weekButton);
            weekbutton.setTypeface(weekbutton.getTypeface(), Typeface.BOLD);
            Button monthbutton = findViewById(R.id.monthButton);
            monthbutton.setTypeface(null, Typeface.NORMAL);
            ft.commit();
        }

        if(v==findViewById(R.id.monthButton)){
            frag = new MonthFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlacement, frag);
            Button daybutton = findViewById(R.id.dayButton);
            daybutton.setTypeface(null, Typeface.NORMAL);
            Button weekbutton = findViewById(R.id.weekButton);
            weekbutton.setTypeface(null, Typeface.NORMAL);
            Button monthbutton = findViewById(R.id.monthButton);
            monthbutton.setTypeface(monthbutton.getTypeface(), Typeface.BOLD);
            CalendarView calendarView=findViewById(R.id.calendarView1);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    Toast.makeText(getApplicationContext(), ""+year, Toast.LENGTH_SHORT).show();

                }
            });
            ft.commit();
        }
    }

    public String switchNextDayofWeek(String d){
        switch (d) {
            case "Sun":
                return "Mon";
            case "Mon":
                return "Tue";
            case "Tue":
                return "Wed";
            case "Wed":
                return "Thur";
            case "Thur":
                return "Fri";
            case "Fri":
                return "Sat";
            case "Sat":
                return "Sun";
        }
        return null;
    }

    public String switchPrevDayofWeek(String d){
        switch (d) {
            case "Sun":
                return "Sat";
            case "Mon":
                return "Sun";
            case "Tue":
                return "Mon";
            case "Wed":
                return "Tue";
            case "Thur":
                return "Wed";
            case "Fri":
                return "Thur";
            case "Sat":
                return "Fri";
        }
        return null;
    }

    public void setDayViewDay(){

        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        TextView cday = findViewById(R.id.currentDay);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int monthint = calendar.get(Calendar.MONTH);
        String month;


        switch (monthint) {
            case Calendar.JANUARY:
                month = "January";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                //daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                break;
            case Calendar.FEBRUARY:
                month = "February";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.MARCH:
                month = "March";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.APRIL:
                month = "April";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.MAY:
                month = "May";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.JUNE:
                month = "June";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.JULY:
                month = "July";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.AUGUST:
                month = "August";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.SEPTEMBER:
                month = "September";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.OCTOBER:
                month = "October";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.NOVEMBER:
                month = "November";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;
            case Calendar.DECEMBER:
                month = "December";
                switch (weekday) {
                    case Calendar.SUNDAY:
                        cday.setText("Sunday, " + month + " " + day);
                        break;
                    case Calendar.MONDAY:
                        cday.setText("Monday, " + month + " " + day);
                        break;
                    case Calendar.TUESDAY:
                        cday.setText("Tuesday, " + month + " " + day);
                        break;
                    case Calendar.WEDNESDAY:
                        cday.setText("Wednesday, " + month + " " + day);
                        break;
                    case Calendar.THURSDAY:
                        cday.setText("Thursday, " + month + " " + day);
                        break;
                    case Calendar.FRIDAY:
                        cday.setText("Friday, " + month + " " + day);
                        break;
                    case Calendar.SATURDAY:
                        cday.setText("Saturday, " + month + " " + day);
                        break;

                }
                break;

        }

    }
}
