package com.example.jameskoss.cosc341_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;


public class MonthFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_month, container, false);

        CalendarView calendarView = v.findViewById(R.id.myCal);
        int day = ((GlobalDateVariables) getActivity().getApplication()).getSelectedDay();
        int month = ((GlobalDateVariables) getActivity().getApplication()).getSelectedMonth() - 1;
        int year = ((GlobalDateVariables) getActivity().getApplication()).getSelectedYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();

        calendarView.setDate(milliTime, true, true);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView cv, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                ((GlobalDateVariables) getActivity().getApplication()).setSelectedDay(dayOfMonth);
                ((GlobalDateVariables) getActivity().getApplication()).setSelectedMonth(month + 1);
                ((GlobalDateVariables) getActivity().getApplication()).setSelectedYear(year);
                ((GlobalDateVariables) getActivity().getApplication()).setSelectedWeekday(dayOfWeek);
                String currentMonth = switchShortMonthToString(((GlobalDateVariables) getActivity().getApplication()).getSelectedMonth()); //string month
                String currentWeekday = switchDayofWeektoString(((GlobalDateVariables) getActivity().getApplication()).getSelectedWeekday()); //returns string of weekday
                String currentDay = Integer.toString(((GlobalDateVariables) getActivity().getApplication()).getSelectedDay()); //string of day eg. "1"
                String currentYear = Integer.toString(((GlobalDateVariables) getActivity().getApplication()).getSelectedYear()); //string year eg. "2018"
                String selectedDate = currentWeekday + ", " + currentMonth + " " + currentDay;
                ((GlobalDateVariables) getActivity().getApplication()).setCurrentMonth(currentMonth);
                ((GlobalDateVariables) getActivity().getApplication()).setCurrentWeekday(currentWeekday);
                ((GlobalDateVariables) getActivity().getApplication()).setCurrentDay(currentDay);
                ((GlobalDateVariables) getActivity().getApplication()).setCurrentYear(currentYear);
                ((GlobalDateVariables) getActivity().getApplication()).setSelectedDate(selectedDate);
                String selectedDateInt = dayOfWeek + " " + dayOfMonth + " " + (month + 1) + " " + year;
                Toast.makeText(getActivity().getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment
        return v;
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

}
