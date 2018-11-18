package com.example.jameskoss.cosc341_project;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class WeekJumpFragment extends Fragment {

    public WeekJumpFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle data = getArguments();
        int containerId = data.getInt("containerID");
        String tag = data.getString("tag");
        Fragment f = new WeekFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        f.setArguments(data);
        ft.replace(containerId, f, tag);
        ft.addToBackStack(null);
        ft.commit();

    }


}