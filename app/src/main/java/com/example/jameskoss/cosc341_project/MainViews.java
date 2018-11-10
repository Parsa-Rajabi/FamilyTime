package com.example.jameskoss.cosc341_project;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainViews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_views);
        loadDayButton();
        loadWeekButton();
        loadMonthButton();

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

    public void changeFragment(View v){
        Fragment frag;

        if(v==findViewById(R.id.dayButton)){
            frag = new DayFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();
        }

        if(v==findViewById(R.id.weekButton)){
            frag = new WeekFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();
        }

        if(v==findViewById(R.id.monthButton)){
            frag = new MonthFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();
        }
    }
}
