package com.example.jameskoss.cosc341_project;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainViews extends FragmentActivity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dayofweekformat = new SimpleDateFormat("EEEE");
    SimpleDateFormat dayofmonthformat = new SimpleDateFormat("dd");
    SimpleDateFormat monthformat = new SimpleDateFormat("MM");
    SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
    Date currentDate = new Date();
    String dayOfWeek = dayofweekformat.format(currentDate);
    int dayOfMonth = Integer.parseInt(dayofmonthformat.format(currentDate));
    int month = Integer.parseInt(monthformat.format(currentDate));
    int year = Integer.parseInt(yearformat.format(currentDate));
    int selectedMonth = month;
    int selectedDay = dayOfMonth;
    int selectedWeekday = switchDayOfWeek(dayOfWeek);
    int selectedYear = year;
    String currentMonth = switchMonthToString(selectedMonth); //initial month on load;
    String currentDay = Integer.toString(selectedDay); //initial day on load;
    String currentWeekday = switchDayofWeektoString(selectedWeekday); //initial weekday on load;
    String currentYear = Integer.toString(selectedYear); //initial year on load;
    String selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_views);
        addDynamicFragment();
        loadDayButton();
        loadWeekButton();
        loadMonthButton();
        Button daybutton = findViewById(R.id.dayButton);
        daybutton.setTypeface(daybutton.getTypeface(), Typeface.BOLD);


    }

    public void addDynamicFragment() {
        Fragment frag = DayFragment.newInstance(((GlobalDateVariables) this.getApplication()).getSelectedDay(), ((GlobalDateVariables) this.getApplication()).getSelectedWeekday(), ((GlobalDateVariables) this.getApplication()).getSelectedMonth(), ((GlobalDateVariables) this.getApplication()).getSelectedYear());
        getSupportFragmentManager().beginTransaction().add(R.id.FragmentPlacement, frag).commit();
    }

    /*public void nextDayButton() { //goes to day view when pressed
        ImageButton button = findViewById(R.id.nextday);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragment(v);
                //DayFragment fragment = (DayFragment) getSupportFragmentManager().findFragmentById(R.id.Fragment_Day);

            }
        });

    }*/

/*    private void previousDayButton() { //goes to day view when pressed
        ImageButton button = findViewById(R.id.previousday);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineDate(v, selectedMonth);
            }
        });

    }*/

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

    public void setNextVariables(int month, int daysinnextmonth, int dayofweek ){
        if (selectedDay == daysinnextmonth) { //last day of month

            selectedDay = 1; //set day to 1
            if(selectedWeekday==7){
                selectedWeekday=1; //reset day of week
            }else{
                selectedWeekday++; //increase day of week by 1
            }

            if(month==12){//if december
                selectedMonth=1; //reset month
                selectedYear++; //increase year
            }else {
                selectedYear = year;//year stays the same
                selectedMonth++;
            }
            currentMonth = switchMonthToString(selectedMonth); //string month
            currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
            currentDay = Integer.toString(selectedDay); //string of day eg. "1"
            currentYear = Integer.toString(selectedYear); //string year eg. "2018"
        } else {
            selectedDay++;
            // selectedMonth = 1; //month stay the same
            if(selectedWeekday==7){
                selectedWeekday=1; //reset day of week
            }else{
                selectedWeekday++; //increase day of week by 1
            }
            selectedYear = year; //stays the same
            currentMonth = switchMonthToString(selectedMonth); //string month
            currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
            currentDay = Integer.toString(selectedDay); //string of day eg. "31"
            currentYear = Integer.toString(selectedYear); //string year eg. "2018"
            selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        }
    }

    public void setPrevVariables(int month, int daysinprevmonth, int dayofweek ){
        if (selectedDay == 1) { //first day of month
            selectedMonth--; //decrease month by 1
            selectedDay = daysinprevmonth; //set day to max in previous month
            if(selectedWeekday==1){
                selectedWeekday=7; //loop day of week
            }else{
                selectedWeekday--; //decrease day of week by 1
            }
            if(month==1){//if january
                selectedMonth=12; //reset to december
                selectedYear--; //decrease year
            }else {
                selectedYear = year;//year stays the same
                selectedMonth--; //decrease month
            }
            currentMonth = switchMonthToString(selectedMonth); //string month
            currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
            currentDay = Integer.toString(selectedDay); //string of day eg. "1"
            currentYear = Integer.toString(selectedYear); //string year eg. "2018"
        } else {
            selectedDay--;
            // selectedMonth = 1; //month stay the same
            if(selectedWeekday==1){
                selectedWeekday=7; //loop day of week
            }else{
                selectedWeekday--; //decrease day of week by 1
            }
            selectedYear = year; //stays the same
            currentMonth = switchMonthToString(selectedMonth); //string month
            currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
            currentDay = Integer.toString(selectedDay); //string of day eg. "31"
            currentYear = Integer.toString(selectedYear); //string year eg. "2018"
            selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        }
    }

    /*private void determineDate(View v, int mont) {

        if (v == findViewById(R.id.nextday)) {
            switch (mont) {
                case 1:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
                case 2: //this needs work for leap years
                    if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                        setNextVariables(mont, 29, selectedWeekday);
                    } else {
                        setNextVariables(mont, 28, selectedWeekday);
                    }
                case 3:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
                case 4:
                    setNextVariables(mont, 30, selectedWeekday);
                    break;
                case 5:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
                case 6:
                    setNextVariables(mont, 30, selectedWeekday);
                    break;
                case 7:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
                case 8:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
                case 9:
                    setNextVariables(mont, 30, selectedWeekday);
                    break;
                case 10:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
                case 11:
                    setNextVariables(mont, 30, selectedWeekday);
                    break;
                case 12:
                    setNextVariables(mont, 31, selectedWeekday);
                    break;
            }
        }

        if (v == findViewById(R.id.previousday)) {
            switch (mont) {
                case 1:
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 2: //this needs work for leap years
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 3:
                    if (year / 4 == 0 || (year / 100 == 0 && year / 400 == 0)) {
                        setPrevVariables(mont, 29, selectedWeekday);
                        break;
                    } else {
                        setPrevVariables(mont, 28, selectedWeekday);
                        break;
                    }
                case 4:
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 5:
                    setPrevVariables(mont, 30, selectedWeekday);
                    break;
                case 6:
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 7:
                    setPrevVariables(mont, 30, selectedWeekday);
                    break;
                case 8:
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 9:
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 10:
                    setPrevVariables(mont, 30, selectedWeekday);
                    break;
                case 11:
                    setPrevVariables(mont, 31, selectedWeekday);
                    break;
                case 12:
                    setPrevVariables(mont, 30, selectedWeekday);
                    break;
            }
        }
       // TextView cday = findViewById(R.id.currentDay);
        //selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        //cday.setText(selectedDate);
    }*/

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
            ft.commit();
        }
    }

    public int switchDayOfWeek(String d){
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

    public String switchMonthToString(int m){
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

    public String switchDayofWeektoString(int d){
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

        //int weekday = calendar.get(Calendar.DAY_OF_WEEK);

    //int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int monthint = calendar.get(Calendar.MONTH);
        //String month;

    public void setDayViewDay() {
        TextView cday = findViewById(R.id.currentDay);
        selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        cday.setText(selectedDate);
    }
}

