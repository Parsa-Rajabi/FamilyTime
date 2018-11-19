package com.example.jameskoss.cosc341_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class WeekFragment extends Fragment implements View.OnClickListener {

    int selectedMonth;
    int selectedDay;
    int selectedWeekday;
    int selectedYear;

    TextView weekViewWeekText;

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
                nextWeekDate(selectedMonth, sunday, selectedYear);
                frag = new WeekJumpFragment();
                replaceFragment(frag, v.getId());
                break;
            case R.id.previousweek:
                prevWeekDate(selectedMonth, sunday, selectedYear);
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
    }

 /*   public void determineNextWeek() {

        sunday = selectedDay;

        if (selectedDay > daysInMonth) {

            if (selectedMonth == 12) {
                selectedMonth = 1;
                selectedYear += 1;
            } else {
                selectedMonth += 1;
            }
            selectedDay -= daysInMonth;
            saturday -= daysInMonth;
        }
        setNewVariables();

        setCurrent();
    }*/



    /*public void determinePrevWeek() {

        selectedDay -= 7;

        saturday -=7;
        sunday -=7;

        if (selectedDay < 1) {
            if (selectedMonth == 1) {
                selectedMonth = 12;
                selectedYear -= 1;
            } else {
                selectedMonth -= 1;
            }
            selectedDay += daysInPrevMonth;
            sunday += daysInPrevMonth;
        }
        setNewVariables();

        setCurrent();
    }*/

    public void nextWeekDate(int month, int sun, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");

  /*      if (sun< 1) {
            if (month == 1) {
                month = 12;
                year -= 1;
            } else {
                month -= 1;
            }
            sun += daysInPrevMonth;
            daysInMonth = daysInPrevMonth;
            switchDaysInPrevMonth(month);
        }*/

        String strDate = month + "-" + sun + "-" + year;
        Date currentDate = null;
        try {
            currentDate = sdf2.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        int sunDay = c.get(Calendar.DATE);
        int sunMonth = c.get(Calendar.MONTH);
        int sunYear = c.get(Calendar.YEAR);

        c.set(sunYear, sunMonth, sunDay);

        c.add(Calendar.DAY_OF_MONTH, 7);

        selectedDay = c.get(Calendar.DAY_OF_MONTH);
        selectedMonth = c.get(Calendar.MONTH) + 1;
        selectedYear = c.get(Calendar.YEAR);

        Date nextSundayDate = c.getTime();

        c.setTime(nextSundayDate);

        c.add(Calendar.DAY_OF_MONTH, 6);

        Date nextSaturdayDate = c.getTime();

        String nextSaturdayStr = sdf.format(nextSaturdayDate);
        String nextSundayStr = sdf.format(nextSundayDate);

        String label = nextSundayStr + " - " + nextSaturdayStr;

        currentWeek = label;
        setCurrent();
        Log.w("testnext", "" + ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedDay());

    }

    public void prevWeekDate(int month, int sun, int year) {


        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");
/*        if (sun< 1) {
            if (month == 1) {
                month = 12;
                year -= 1;
            } else {
                month -= 1;
            }
            sun += daysInPrevMonth;
            daysInMonth = daysInPrevMonth;
            switchDaysInPrevMonth(month);
        }*/
        String strDate = month + "-" + sun + "-" + year;
        Date currentDate = null;
        try {
            currentDate = sdf2.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        c.add(Calendar.DAY_OF_MONTH, -7);

        selectedDay = c.get(Calendar.DATE);
        selectedMonth = c.get(Calendar.MONTH);
        selectedYear = c.get(Calendar.YEAR);

        Date nextSundayDate = c.getTime();

        c.setTime(nextSundayDate);

        c.add(Calendar.DAY_OF_MONTH, 6);

        Date nextSaturdayDate = c.getTime();

        String nextSaturdayStr = sdf.format(nextSaturdayDate);
        String nextSundayStr = sdf.format(nextSundayDate);

        String label = nextSundayStr + " - " + nextSaturdayStr;

        currentWeek = label;
        setCurrent();
        Log.w("testback", "" + ((GlobalDateVariables) this.getActivity().getApplication()).getSelectedDay());

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

    /*public void setNewVariables(){
        String sat;
        String sun;
        String month1;
        String month2;
        if (saturday > daysInMonth) {
            if (selectedMonth == 12) {
                nextMonthInt = 1;
                month2 = switchShortMonthToString(nextMonthInt);
                saturday = saturday - daysInMonth;
                selectedYear++;
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
            if (selectedMonth== 1) {
                prevMonthInt = 12;
                sunday = sunday + daysInPrevMonth;
                month1 = switchShortMonthToString(prevMonthInt);
                selectedYear--;
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
        currentWeek = sun + "-" + sat;
    }*/

    /*public void setVariables() {
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInPrevMonth(setDaysInPrevMonths(selectedMonth));
        ((GlobalDateVariables) this.getActivity().getApplication()).setDaysInMonth(setDaysInMonths(selectedMonth));

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
*/
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
