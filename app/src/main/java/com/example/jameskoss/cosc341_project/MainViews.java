package com.example.jameskoss.cosc341_project;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainViews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_views);
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
