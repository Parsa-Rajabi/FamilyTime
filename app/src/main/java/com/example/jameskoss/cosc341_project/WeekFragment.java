package com.example.jameskoss.cosc341_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class WeekFragment extends Fragment implements View.OnClickListener {

    int selectedMonth;
    int selectedDay;
    int selectedWeekday;
    int selectedYear;
    String currentMonth;
    String currentDay;
    String currentWeekday;
    String currentYear;
    String currentWeek;
    String selectedDate;
    TextView weekViewWeekText;

    String month1;
    String month2;
    int nextMonthInt;
    int prevMonthInt;
    int daysInMonth;
    int daysInPrevMonth;
    int sunday;
    int saturday;
    String sat;
    String sun;

    public WeekFragment() {

    }

    public static WeekFragment newInstance(int selectedDay, int selectedWeekday, int selectedMonth, int selectedYear) {
        Bundle bundle = new Bundle();
        bundle.putInt("selectedDay", selectedDay);
        bundle.putInt("selectedWeekday", selectedWeekday);
        bundle.putInt("selectedMonth", selectedMonth);
        bundle.putInt("selectedYear", selectedYear);
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

        ImageButton nextButton = v.findViewById(R.id.nextweek);
        ImageButton prevButton = v.findViewById(R.id.previousweek);
        weekViewWeekText = v.findViewById(R.id.currentWeek);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        readBundle(getArguments());
        weekViewWeekText.setText(currentWeek);


        return v;
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
            frag = WeekFragment.newInstance(selectedDay, selectedWeekday, selectedMonth, selectedYear);
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();

        }
        if (R.id.previousweek == id) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            frag = WeekFragment.newInstance(selectedDay, selectedWeekday, selectedMonth, selectedYear);
            ft.replace(R.id.FragmentPlacement, frag);
            ft.commit();

        }
    }

    /*public void setCurrent() {

        selectedWeekday = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedWeekday();
        selectedYear = ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedYear();
        currentMonth = switchMonthToString(selectedMonth); //string month
        currentWeekday = switchDayofWeektoString(selectedWeekday); //returns string of weekday
        currentDay = Integer.toString(selectedDay); //string of day eg. "1"
        currentYear = Integer.toString(selectedYear); //string year eg. "2018"
        selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(selectedMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDay(selectedDay);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentWeek(currentWeek);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentMonth(currentMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentWeekday(currentWeekday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentDay(currentDay);
        ((GlobalDateVariables) this.getActivity().getApplication()).setCurrentYear(currentYear);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedDate(selectedDate);
    }*/

    public void determineNextWeek() {

        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInMonth(selectedMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInPrevMonth(selectedMonth);
        selectedDay += 7;

        if (selectedDay > ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInMonth()) {
            selectedDay -= ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInMonth();
            if (selectedMonth == 12) {
                selectedMonth = 1;
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedYear(selectedYear + 1);
            } else {
                selectedMonth += 1;
            }
        }
        setVariables();

        //setCurrent();
    }

    public void determinePrevWeek() {
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInMonths(selectedMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInPrevMonths(selectedMonth);
        selectedDay -= 7;
        if (selectedDay < 1) {
            selectedDay += ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInPrevMonth();
            if (selectedMonth == 1) {
                selectedMonth = 12;
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedYear(selectedYear - 1);
            } else {
                selectedMonth -= 1;
            }
        }
        setVariables();
        ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedMonth(selectedMonth);
        //setCurrent();
    }

    public void setVariables() {
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInPrevMonths(selectedMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInMonths(selectedMonth);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSunday(selectedWeekday);
        ((GlobalDateVariables) this.getActivity().getApplication()).setSaturday(selectedWeekday);
        saturday = ((GlobalDateVariables) this.getActivity().getApplication()).getSaturday();
        sunday = ((GlobalDateVariables) this.getActivity().getApplication()).getSunday();
        if (saturday > ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInMonth()) {
            if (selectedMonth == 12) {
                ((GlobalDateVariables) this.getActivity().getApplication()).setNextMonthInt(1);
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedYear(selectedYear + 1);
            } else {
                ((GlobalDateVariables) this.getActivity().getApplication()).setNextMonthInt(selectedMonth + 1);
            }
            month2 = switchShortMonthToString(((GlobalDateVariables) this.getActivity().getApplication()).getNextMonthInt());

            saturday -= ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInMonth();
            sat = month2 + " " + saturday;
        } else {
            sat = "" + saturday;
        }
        if (sunday < 1) {
            if (selectedMonth == 1) {
                ((GlobalDateVariables) this.getActivity().getApplication()).setPrevMonthInt(12);
                ((GlobalDateVariables) this.getActivity().getApplication()).setSelectedYear(selectedYear - 1);
            } else {
                ((GlobalDateVariables) this.getActivity().getApplication()).setPrevMonthInt(selectedMonth - 1);
            }
            month1 = switchShortMonthToString(((GlobalDateVariables) this.getActivity().getApplication()).getPrevMonthInt());

            sunday += ((GlobalDateVariables) this.getActivity().getApplication()).getDaysInPrevMonth();

            sun = month1 + " " + sunday;
            sat = switchShortMonthToString(selectedMonth) + " " + saturday;

        } else {
            sun = switchShortMonthToString(selectedMonth) + " " + sunday;
        }
        currentWeek = sun + "-" + sat;
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
}
