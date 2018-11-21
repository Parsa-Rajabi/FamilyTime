package com.example.jameskoss.cosc341_project;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainViews extends FragmentActivity {



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

    private void loadDayButton() { //goes to day view when pressed
        Button button = findViewById(R.id.dayButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

    }

    private void loadWeekButton() { //goes to week view when pressed
        Button button = findViewById(R.id.weekButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

    }

    private void loadMonthButton() { //goes to month view when pressed
        Button button = findViewById(R.id.monthButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

    }

    public void changeFragment(View v) {
        Fragment frag;


        if (v == findViewById(R.id.dayButton)) {

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

        if (v == findViewById(R.id.weekButton)) {
            ((GlobalDateVariables) this.getApplication()).switchNextMonthInt(((GlobalDateVariables) this.getApplication()).getSelectedMonth());
            ((GlobalDateVariables) this.getApplication()).switchPrevMonthInt(((GlobalDateVariables) this.getApplication()).getSelectedMonth());
            ((GlobalDateVariables) this.getApplication()).switchDaysInMonth(((GlobalDateVariables) this.getApplication()).getSelectedMonth());
            ((GlobalDateVariables) this.getApplication()).switchDaysInPrevMonth(((GlobalDateVariables) this.getApplication()).getSelectedMonth());
            ((GlobalDateVariables) this.getApplication()).switchSunday(((GlobalDateVariables) this.getApplication()).getSelectedWeekday());
            ((GlobalDateVariables) this.getApplication()).switchSaturday(((GlobalDateVariables) this.getApplication()).getSelectedWeekday());
            ((GlobalDateVariables) this.getApplication()).determineWeek();
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

        if (v == findViewById(R.id.monthButton)) {
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

    public void createEvent(View v) {
        try {
            Intent i = new Intent(this, CreateEvent.class);
            Bundle b = new Bundle();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
            String strDate = ((GlobalDateVariables) this.getApplication()).getSelectedYear() + "/" + ((GlobalDateVariables) this.getApplication()).getSelectedMonth() + "/" + ((GlobalDateVariables) this.getApplication()).getSelectedDate();
            Date currentDate = null;

            try {
                currentDate = sdf2.parse(strDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "We got past the parsa", Toast.LENGTH_SHORT).show();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String dateToPass = sdf.format(currentDate);
            b.putString("date", dateToPass);
            Date today = new Date();
            String strDate1 = sdf2.format(today);
            today = sdf2.parse(strDate1);
            boolean isDayToday = (currentDate.compareTo(today) == 0);
            b.putBoolean("present", isDayToday);
            b.putString("timestamp","-1");
            b.putString("username", "Mothership");
//            b.putString("timestamp", v.getTag(-1).toString()); // TODO: get the correct tag
            i.putExtras(b);
            startActivity(i);
        } catch(ParseException e) {

        }
    }
}

