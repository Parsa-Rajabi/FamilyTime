package com.example.jameskoss.cosc341_project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;


public class WeekFragment extends Fragment implements View.OnClickListener {

    int selectedMonth;
    int selectedDay;
    int selectedWeekday;
    int selectedYear;

    TextView weekViewWeekText;

    HashMap<String, Boolean> state;
    int nextMonthInt;
    int prevMonthInt;
    int daysInMonth;
    int daysInPrevMonth;
    int sunday;
    int saturday;
    String currentWeek;


    public WeekFragment() {

    }

    public static WeekFragment newInstance(int selectedDay, int selectedWeekday, int selectedMonth,
                                           int selectedYear, int nextMonthInt, int prevMonthInt,
                                           int daysInMonth, int daysInPrevMonth, int sunday, int saturday, String currentWeek) {
        Bundle bundle = new Bundle();
        bundle.putInt("selectedDay", selectedDay);
        bundle.putInt("selectedWeekday", selectedWeekday);
        bundle.putInt("selectedMonth", selectedMonth);
        bundle.putInt("selectedYear", selectedYear);
        bundle.putInt("nextMonthInt", nextMonthInt);
        bundle.putInt("prevMonthInt", prevMonthInt);
        bundle.putInt("daysInMonth", daysInMonth);
        bundle.putInt("daysInPrevMonth", daysInPrevMonth);
        bundle.putInt("sunday", sunday);
        bundle.putInt("saturday", saturday);
        bundle.putString("currentWeek", currentWeek);
        WeekFragment frag = new WeekFragment();
        frag.setArguments(bundle);
        return frag;

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {

            selectedDay = bundle.getInt("selectedDay");
            selectedMonth = bundle.getInt("selectedMonth");
            selectedWeekday = bundle.getInt("selectedWeekday");
            selectedYear = bundle.getInt("selectedYear");
            nextMonthInt = bundle.getInt("nextMonthInt");
            prevMonthInt = bundle.getInt("prevMonthInt");
            daysInMonth = bundle.getInt("daysInMonth");
            daysInPrevMonth = bundle.getInt("daysInPrevMonth");
            sunday = bundle.getInt("sunday");
            saturday = bundle.getInt("saturday");
            currentWeek = bundle.getString("currentWeek");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_week, container, false);
        this.currentWeek = ((GlobalDateVariables) this.getActivity().getApplication()).getCurrentWeek();
        this.selectedMonth = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedMonth();
        this.selectedDay = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedDay();
        this.selectedYear = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedYear();
        this.selectedWeekday = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedWeekday();
        this.nextMonthInt = ((GlobalDateVariables) this.getActivity().getApplication()).getNextMonthInt();
        this.prevMonthInt = ((GlobalDateVariables) this.getActivity().getApplication()).getPrevMonthInt();
        this.daysInMonth = ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInMonth();
        this.daysInPrevMonth = ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInPrevMonth();
        this.sunday = ((GlobalDateVariables) this.getActivity().getApplication()).getSunday();
        this.saturday = ((GlobalDateVariables) this.getActivity().getApplication()).getSaturday();
        this.currentWeek = ((GlobalDateVariables) this.getActivity().getApplication()).getCurrentWeek();
        this.state = readState();

        //Create the big grid view
        GridLayout gridlayout = v.findViewById(R.id.gridweektime);
        String[] times = getResources().getStringArray(R.array.timelist);
        for (int i = 0; i < 49; i++) {
            if (i == 0) {
                TextView tv = new TextView(getContext());
                tv.setId(View.generateViewId());
                tv.setPadding(5,5,5,5);
                tv.setBackgroundColor(getContext().getColor(R.color.white_color));
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.setGravity(Gravity.FILL);
                param.bottomMargin = 4;
                param.columnSpec = GridLayout.spec(0,1,1f);
                param.rowSpec = GridLayout.spec(i,1);
                gridlayout.addView(tv, param);
            } else {
                TextView tv = new TextView(getContext());
                tv.setId(View.generateViewId());
                tv.setText(times[i-1]);
                tv.setPadding(5, 5, 5, 5);
                tv.setBackgroundColor(getContext().getColor(R.color.white_color));
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 1;
                param.topMargin = ((i + 1) % 2) + 1; // oscillate between 2dp and 1dp top margin
                param.setGravity(Gravity.FILL);
                param.columnSpec = GridLayout.spec(0, 1, 1f);
                param.rowSpec = GridLayout.spec(i, 1);
                gridlayout.addView(tv, param);
            }

        }
        gridlayout = v.findViewById(R.id.gridweek);
        String[] weekdays = getResources().getStringArray(R.array.weekdays);
        for (int i = 0; i < 49; i++) {
            for (int j = 0; j < 7; j++) {
                if (i==0) {
                    TextView tv = new TextView(getContext());
                    switch (j) {
                        case 0:
                            tv.setId(R.id.sun);
                            break;
                        case 1:
                            tv.setId(R.id.mon);
                            break;
                        case 2:
                            tv.setId(R.id.tue);
                            break;
                        case 3:
                            tv.setId(R.id.wed);
                            break;
                        case 4:
                            tv.setId(R.id.thu);
                            break;
                        case 5:
                            tv.setId(R.id.fri);
                            break;
                        case 6:
                            tv.setId(R.id.sat);
                            break;
                    }
                    tv.setPadding(5, 5, 5, 5);
                    tv.setBackgroundColor(getContext().getColor(R.color.white_color));
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.bottomMargin = 4;
                    param.setGravity(Gravity.FILL);
                    param.columnSpec = GridLayout.spec(j, 1, 1f);
                    param.rowSpec = GridLayout.spec(i, 1);
                    gridlayout.addView(tv, param);
                } else {
                    TextView tv = new TextView(getContext());
                    tv.setId(View.generateViewId());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setBackgroundColor(getContext().getColor(R.color.white_color));
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.rightMargin = 1;
                    param.topMargin = ((i + 1) % 2) + 1; // oscillate between 2dp and 1dp top margin
                    param.setGravity(Gravity.FILL);
                    param.columnSpec = GridLayout.spec(j, 1, 1f);
                    param.rowSpec = GridLayout.spec(i, 1);
                    gridlayout.addView(tv, param);
                }
            }
        }

        setDaysOfWeekView(v);

        ImageButton nextWeekButton = v.findViewById(R.id.nextweek);
        ImageButton prevWeekButton = v.findViewById(R.id.previousweek);
        weekViewWeekText = v.findViewById(R.id.currentWeek);

        nextWeekButton.setOnClickListener(this);
        prevWeekButton.setOnClickListener(this);
        readBundle(getArguments());
        weekViewWeekText.setText(currentWeek);


        return v;
    }

    @Override
    public void onResume() {
        GridLayout gridlayout = getActivity().findViewById(R.id.gridweek);

        int numChilds = gridlayout.getChildCount();
        boolean doBreak = false;
        int childIdx = 0;
        while (!doBreak) {
            View v = gridlayout.getChildAt(childIdx);
            if(v instanceof Button){
                gridlayout.removeView(v);
            } else {
                childIdx++;
            }
            if (childIdx == numChilds) doBreak = true;
        }

        this.state = readState();
        Log.e("WeekFragment.onResume","state: "+this.state.toString());

        File[] files = getScheduleFiles();
        Log.e("WeekFragment.onResume","Files found: "+files.length);
        int i = 0;
        int numDisplayed = 0;
        for (File f: files) {
            String key = f.getName().substring(0,f.getName().length()-4);
            if (this.state.containsKey(key)) {
                if (this.state.get(key)) numDisplayed++;
            }
        }
        for (File f: files) {
            String key = f.getName().substring(0,f.getName().length()-4);
            Log.e("WeekFragment.onResume","File found: "+f+"; Cut to: "+key);
            if (this.state == null) this.state = new HashMap<String, Boolean>();
            if (!this.state.containsKey(key)) {
                if (key.equals("Mothership")) {
                    this.state.put(key,true);
                } else {
                    this.state.put(key,false);
                }
            }
            if (this.state.get(key)) {
                Schedule s = new Schedule(f.getName(), this.getActivity().getApplicationContext(), (numDisplayed>1?i:-1));
                // s.createFromFile("data.txt"); // pass in the user schedule file, or put this file string in the constructor as its only argument
                Calendar c = Calendar.getInstance();
                c.set(this.selectedYear,this.selectedMonth-1,this.sunday,0,0,0);
                s.printWeek(c.getTime(), gridlayout, getContext(), this.getActivity().getApplication());
            }
            i++;
        }

        super.onResume();
    }

    @Override
    public void onClick(View v) {

        Fragment frag = null;
        switch (v.getId()) {
            case R.id.nextweek:
                determineNextWeek();
                frag = new WeekJumpFragment();
                replaceFragment(frag, v.getId());
                break;
            case R.id.previousweek:
                determinePrevWeek();
                frag = new WeekJumpFragment();
                replaceFragment(frag, v.getId());
                break;
        }
    }

    public void replaceFragment(Fragment frag, int id) {
        if (R.id.nextweek == id) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            frag = WeekFragment.newInstance(selectedDay, selectedWeekday, selectedMonth, selectedYear, nextMonthInt, prevMonthInt, daysInMonth, daysInPrevMonth, sunday, saturday, currentWeek);
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();

        }
        if (R.id.previousweek == id) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            frag = WeekFragment.newInstance(selectedDay, selectedWeekday, selectedMonth, selectedYear, nextMonthInt, prevMonthInt, daysInMonth, daysInPrevMonth, sunday, saturday, currentWeek);
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();

        }
    }

    public void setCurrent() {

        Log.w("testyear", "" + this.selectedYear);
        Log.w("testweekday", "" + this.selectedWeekday);
        Log.w("testday", "" + this.selectedDay);
        Log.w("testmonth", "" + this.selectedMonth);
        Log.w("testsunday", "" + this.sunday);
        Log.w("testnextMonthInt", "" + this.nextMonthInt);
        Log.w("testprevMonthInt", "" + this.prevMonthInt);
        Log.w("testdaysinMonth", "" + this.daysInMonth);
        Log.w("testdaysinprevmonth", "" + this.daysInPrevMonth);
        Log.w("testsaturday", "" + this.saturday);
        Log.w("testcurrentWeek", "" + this.currentWeek);

        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(selectedYear);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(selectedWeekday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(selectedMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(selectedDay);
        ((GlobalDateVariables) this.getActivity().getApplication()).setNextMonthInt(nextMonthInt);
        ((GlobalDateVariables) this.getActivity().getApplication()).setPrevMonthInt(prevMonthInt);
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInMonth(daysInMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInPrevMonth(daysInPrevMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSunday(sunday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSaturday(saturday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentWeek(currentWeek);
        String currentMonth = switchShortMonthToString(selectedMonth); //string month
        String currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
        String currentDay = Integer.toString(selectedDay); //string of day eg. "1"
        String currentYear = Integer.toString(selectedYear); //string year eg. "2018"
        String selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentMonth(currentMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentWeekday(currentWeekday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentDay(currentDay);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentYear(currentYear);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDate(selectedDate);
        Log.w("thisIsRun", "" + selectedDate);
    }

    public void determineNextWeek() {

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

        c.add(Calendar.DAY_OF_MONTH, 7);
        int nextSundayDay = c.get(Calendar.DATE);
        int nextSundayMonth = c.get(Calendar.MONTH) + 1;
        int nextSundayYear = c.get(Calendar.YEAR);
        this.selectedMonth = nextSundayMonth;
        this.selectedDay = nextSundayDay;
        this.sunday = nextSundayDay;
        this.selectedYear = nextSundayYear;
        this.selectedWeekday = 1;
        Date nextSundayDate = c.getTime();

        c.add(Calendar.DAY_OF_MONTH, 6);

        int nextSaturdayDay = c.get(Calendar.DATE);
        this.saturday = nextSaturdayDay;
        int nextSaturdayMonth = c.get(Calendar.MONTH) + 1;
        int nextSaturdayYear = c.get(Calendar.YEAR);

        Date nextSaturdayDate = c.getTime();

        String nextSaturdayStr = sdf.format(nextSaturdayDate);
        String currentSundayStr = sdf.format(nextSundayDate);

        String label = currentSundayStr + " - " + nextSaturdayStr;

        this.currentWeek = label;

        setCurrent();
    }

    public void setDaysOfWeekView(View v) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");

        TextView sun = v.findViewById(R.id.sun);
        TextView mon = v.findViewById(R.id.mon);
        TextView tue = v.findViewById(R.id.tue);
        TextView wed = v.findViewById(R.id.wed);
        TextView thu = v.findViewById(R.id.thu);
        TextView fri = v.findViewById(R.id.fri);
        TextView sat = v.findViewById(R.id.sat);

        String weekDate = this.selectedMonth + "-" + this.sunday + "-" + this.selectedYear;

        Date currentDate = null;
        try {
            currentDate = sdf2.parse(weekDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        Date sundayDay = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date mondayDay = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tuesdayDay = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date wednesdayDay = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date thursdayDay = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date fridayDay = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date saturdayDay = c.getTime();


        String sunday = sdf.format(sundayDay);
        String monday = sdf.format(mondayDay);
        String tuesday = sdf.format(tuesdayDay);
        String wednesday = sdf.format(wednesdayDay);
        String thursday = sdf.format(thursdayDay);
        String friday = sdf.format(fridayDay);
        String saturday = sdf.format(saturdayDay);

        sun.setText(sunday);
        mon.setText(monday);
        tue.setText(tuesday);
        wed.setText(wednesday);
        thu.setText(thursday);
        fri.setText(friday);
        sat.setText(saturday);
    }

    public void determinePrevWeek() {
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

        c.add(Calendar.DAY_OF_MONTH, -7);
        int prevSundayDay = c.get(Calendar.DATE);
        int prevSundayMonth = c.get(Calendar.MONTH) + 1;
        int prevSundayYear = c.get(Calendar.YEAR);
        this.selectedMonth = prevSundayMonth;
        this.selectedDay = prevSundayDay;
        this.sunday = prevSundayDay;
        this.selectedYear = prevSundayYear;
        this.selectedWeekday = 1;

        Date prevSundayDate = c.getTime();


        c.add(Calendar.DAY_OF_MONTH, 6);

        Date prevSaturdayDate = c.getTime();

        String nextSaturdayStr = sdf.format(prevSaturdayDate);
        String nextSundayStr = sdf.format(prevSundayDate);

        String label = nextSundayStr + " - " + nextSaturdayStr;

        currentWeek = label;
        setCurrent();
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

    public HashMap<String, Boolean> readState() {
        File f = new File(getActivity().getApplication().getFilesDir(), "statedata");
        Log.e("WeekFragment.readState","File exists: "+f.exists());
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Log.e("WeekFragment.readState","Hashmap Found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return (HashMap<String, Boolean>)ois.readObject();
            } catch (Exception e) {
                return new HashMap<>();
            }
        } else {
            return new HashMap<>();
        }
    }

    private File[] getScheduleFiles() {
        File fileDir = getActivity().getApplication().getFilesDir();
        File[] listOfFiles = fileDir.listFiles();
        ArrayList<File> al = new ArrayList<>();
        for (File f: listOfFiles) {
            if (f.getName().endsWith(".txt")) {
                al.add(f);
            }
        }
        File[] files = new File[al.size()];
        return al.toArray(files);
    }
}
