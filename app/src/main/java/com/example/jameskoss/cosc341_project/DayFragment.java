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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class DayFragment extends Fragment implements View.OnClickListener {

    int selectedMonth;
    int selectedDay;
    int selectedWeekday;
    int selectedYear;
    String currentMonth;
    String currentDay;
    String currentWeekday;
    String currentYear; //initial year on load;
    String selectedDate;
    TextView dayViewDayText;
    HashMap<String, Boolean> state;


    public DayFragment() {
    }

    public static DayFragment newInstance(int selectedDay, int selectedWeekday, int selectedMonth, int selectedYear) {
        Bundle bundle = new Bundle();
        bundle.putInt("selectedDay", selectedDay);
        bundle.putInt("selectedWeekday", selectedWeekday);
        bundle.putInt("selectedMonth", selectedMonth);
        bundle.putInt("selectedYear", selectedYear);
        DayFragment frag = new DayFragment();
        frag.setArguments(bundle);
        return frag;

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            selectedDay = bundle.getInt("selectedDay");
            selectedMonth = bundle.getInt("selectedMonth");
            selectedWeekday = bundle.getInt("selectedWeekday");
            selectedYear = bundle.getInt("selectedYear");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day, container, false);
        this.selectedDate = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedDate();
        this.selectedMonth = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedMonth();
        this.selectedDay = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedDay();
        this.selectedYear = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedYear();
        this.selectedWeekday = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedWeekday();

        //Create the big grid view
        GridLayout gridlayout = v.findViewById(R.id.gridday);
        String[] times = getResources().getStringArray(R.array.timelist);
        for (int i = 0; i < 48; i++) {
            TextView tv = new TextView(getContext());
            tv.setId(View.generateViewId());
            tv.setText(times[i]);
            tv.setPadding(5,5,5,5);
            tv.setBackgroundColor(getContext().getColor(R.color.white_color));
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 1;
            param.topMargin = ((i+1)%2)+1; // oscillate between 2dp and 1dp top margin
            param.setGravity(Gravity.FILL);
            param.columnSpec = GridLayout.spec(0,1,1f);
            param.rowSpec = GridLayout.spec(i,1);
            gridlayout.addView(tv, param);

            tv = new TextView(getContext());
            tv.setId(View.generateViewId());
            tv.setPadding(5,5,5,5);
            tv.setBackgroundColor(getContext().getColor(R.color.white_color));
            param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.topMargin = ((i+1)%2)+1; // oscillate between 2dp and 1dp top margin
            param.setGravity(Gravity.FILL);
            param.columnSpec = GridLayout.spec(1,1,10f);
            param.rowSpec = GridLayout.spec(i,1);
            gridlayout.addView(tv, param);
        }



        ImageButton nextDayButton = v.findViewById(R.id.nextday);
        ImageButton prevDayButton = v.findViewById(R.id.previousday);
        dayViewDayText = v.findViewById(R.id.currentDay);

        nextDayButton.setOnClickListener(this);
        prevDayButton.setOnClickListener(this);
        readBundle(getArguments());
        dayViewDayText.setText(selectedDate);


        return v;
    }

    @Override
    public void onResume() {
        GridLayout gridlayout = getActivity().findViewById(R.id.gridday);

        for(int i = 0; i < gridlayout.getChildCount(); i++){
            View v = gridlayout.getChildAt(i);
            if(v instanceof Button){
                gridlayout.removeViewAt(i);
            }

        }

        this.state = readState();
        Log.e("DayView.onResume","Hashmap: "+this.state.toString());

        this.state = readState();
        Log.e("DayFragment.onResume","state: "+this.state.toString());

        File[] files = getScheduleFiles();
        Log.e("DayFragment.onResume","Files found: "+files.length);
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
            Log.e("DayFragment.onResume","File found: "+f+"; Cut to: "+key);
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
                c.set(this.selectedYear,this.selectedMonth-1,this.selectedDay,0,0,0);
                s.printDay(c.getTime(), gridlayout, 1, 0, getContext(), this.getActivity().getApplication());
            }
            i++;
        }

        super.onResume();
    }


    @Override
    public void onClick(View v) {
        Fragment frag = null;
        switch (v.getId()) {
            case R.id.nextday:
                determineNextDate(selectedMonth);
                frag = new JumpFragment();
                replaceFragment(frag, v.getId());
                break;
            case R.id.previousday:
                determinePrevDate(selectedMonth);
                frag = new JumpFragment();
                replaceFragment(frag, v.getId());
                break;
        }
    }

    public void replaceFragment(Fragment frag, int id) {
        if (R.id.nextday == id) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            frag = DayFragment.newInstance(selectedDay, selectedWeekday, selectedMonth, selectedYear);
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();

        }
        if (R.id.previousday == id) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            frag = DayFragment.newInstance(selectedDay, selectedWeekday, selectedMonth, selectedYear);
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();

        }
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

    public void setCurrent() {
        selectedDay = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedDay();
        selectedWeekday = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedWeekday();
        selectedMonth = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedMonth();
        selectedYear = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedYear();
        currentMonth = switchShortMonthToString(selectedMonth); //string month
        currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
        currentDay = Integer.toString(selectedDay); //string of day eg. "1"
        currentYear = Integer.toString(selectedYear); //string year eg. "2018"
        selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentMonth(currentMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentWeekday(currentWeekday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentDay(currentDay);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentYear(currentYear);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDate(selectedDate);
    }

    public void setNextVariables(int month, int daysinmonth, int dayofweek, int day, int year) {
        if (day == daysinmonth) { //last day of month
            ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(1); //set day to 1
            if (dayofweek == 7) {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(1); //reset day of week
            } else {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(dayofweek + 1); //increase day of week by 1
            }
            if (month == 12) {//if december
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(1); //reset month
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedYear(year + 1);//increase year
            } else {
                //selectedYear = year;//year stays the same
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(month + 1);
            }
        } else {
            ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(day + 1);
            // selectedMonth = 1; //month stay the same
            if (dayofweek == 7) {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(1);  //reset day of week
            } else {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(dayofweek + 1); //increase day of week by 1
            }
            //selectedYear = year; //stays the same
        }
        setCurrent();
    }

    public void setPrevVariables(int month, int daysinprevmonth, int dayofweek, int day, int year) {
        if (day == 1) { //first day of month
            ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(daysinprevmonth); //set day to max in previous month
            if (dayofweek == 1) {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(7);//loop day of week
            } else {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(dayofweek - 1); //decrease day of week by 1
            }
            if (month == 1) {//if january
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(12); //reset to december
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedYear(year - 1); //decrease year
            } else {
                //selectedYear = year;//year stays the same
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(month - 1); //decrease month
            }
        } else {
            ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(day - 1);
            // selectedMonth = 1; //month stay the same
            if (dayofweek == 1) {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(7); //loop day of week
            } else {
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedWeekday(dayofweek - 1);//decrease day of week by 1
            }
            //selectedYear = year; //stays the same
        }
        setCurrent();
    }

    private void determineNextDate(int mont) {
        switch (mont) {
            case 1:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 2: //this needs work for leap years
                if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                    setNextVariables(mont, 29, selectedWeekday, selectedDay, selectedYear);
                } else {
                    setNextVariables(mont, 28, selectedWeekday, selectedDay, selectedYear);
                }
            case 3:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 4:
                setNextVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 5:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 6:
                setNextVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 7:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 8:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 9:
                setNextVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 10:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 11:
                setNextVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 12:
                setNextVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
        }
    }

    public void determinePrevDate(int mont) {

        switch (mont) {
            case 1:
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 2: //this needs work for leap years
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 3:
                if (selectedYear / 4 == 0 || (selectedYear / 100 == 0 && selectedYear / 400 == 0)) {
                    setPrevVariables(mont, 29, selectedWeekday, selectedDay, selectedYear);
                    break;
                } else {
                    setPrevVariables(mont, 28, selectedWeekday, selectedDay, selectedYear);
                    break;
                }
            case 4:
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 5:
                setPrevVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 6:
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 7:
                setPrevVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 8:
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 9:
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 10:
                setPrevVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
            case 11:
                setPrevVariables(mont, 31, selectedWeekday, selectedDay, selectedYear);
                break;
            case 12:
                setPrevVariables(mont, 30, selectedWeekday, selectedDay, selectedYear);
                break;
        }
    }

    public HashMap<String, Boolean> readState() {
        File f = new File(getActivity().getApplication().getFilesDir(), "statedata");
        Log.e("DayFragment.readState","File exists: "+f.exists());
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Log.e("DayFragment.readState","Hashmap Found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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