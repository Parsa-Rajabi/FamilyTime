package com.example.jameskoss.cosc341_project;

import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
    private ArrayList<Event> events;
    public Schedule() {
        events = new ArrayList<Event>();
    }
    public Schedule(String filename) {
        this();
        this.createFromFile(filename);
    }
    public ArrayList<Event> getEvents() {
        return events;
    }
    public void createFromFile(String file) {
        String line;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(",");
                try {
                    Event e = new Event(
                            eventData[0],
                            eventData[1],
                            new SimpleDateFormat().parse(eventData[2]),
                            new SimpleDateFormat().parse(eventData[3]),
                            eventData[4],
                            Integer.parseInt(eventData[5]),
                            Integer.parseInt(eventData[6]),
                            eventData[7],
                            eventData[8],
                            eventData[9]
                    );
                    this.events.add(e);
                } catch(ParseException e) {
                    //TODO: Maybe do something here, if the file it read was written in correctly
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void printDay(Date day, GridLayout gridlayout, Context c) {
        for (int i = 0; i < events.size(); i++) {
            Event currentEvent = events.get(0);
            Date start = currentEvent.getStartTime();
            Date end = currentEvent.getEndTime();
            //TODO Potentially do the multi day detect here.
            if (!(day.before(start) || day.after(end))) { //TODO: Potentially use Calendar class to check that this event is (at the least partially) today
                //this event is today
                Button btn = new Button(c);
                btn.setId(View.generateViewId());
                btn.setText(c.getResources().getString(R.string.event_title_format,currentEvent.getTitle(),currentEvent.getLocation()));
                btn.setPadding(5,5,5,5);
                btn.setBackgroundColor(c.getResources().getColor(R.color.white_color));
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.setGravity(Gravity.FILL);
                int st = getIndexForTime(start);
                int en = getIndexForTime(end)-st;
                //TODO: figure out how to detect days that span multiple days long
                param.columnSpec = GridLayout.spec(0,1,1f);
                param.rowSpec = GridLayout.spec(i,1);
                gridlayout.addView(btn, param);
            }
        }
    }

    public void printWeek(Date sunday, GridLayout gridlayout, Context c) {

    }

    private int getIndexForTime(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return (hour*2) + (minute/30);
        //NOTE: Returns an int from 0 to 47 inclusive based on passed date
    }

    public void generateTestSchedule() {
        try {
            this.events = new ArrayList<Event>();
            this.events.add(new Event(
                    "12345678",
                    "COSC 341",
                    new SimpleDateFormat().parse("2018/11/20 03:00:00"),
                    new SimpleDateFormat().parse("2018/11/20 06:00:00"),
                    "-1",
                    Integer.parseInt("0"),
                    Integer.parseInt("0"),
                    "Art Room 341",
                    "#ff00ff",
                    "Test Note"
            ));
            this.events.add(new Event(
                    "12345679",
                    "COSC 400",
                    new SimpleDateFormat().parse("2018/11/22 08:00:00"),
                    new SimpleDateFormat().parse("2018/11/22 09:00:00"),
                    "-1",
                    Integer.parseInt("0"),
                    Integer.parseInt("0"),
                    "Art Room 400",
                    "#006869",
                    "Test Note"
            ));
            this.events.add(new Event(
                    "12345680",
                    "COSC 150",
                    new SimpleDateFormat().parse("2018/11/21 10:00:00"),
                    new SimpleDateFormat().parse("2018/11/21 10:30:00"),
                    "-1",
                    Integer.parseInt("0"),
                    Integer.parseInt("0"),
                    "Art Room 150",
                    "#66eeaa",
                    "Test Note"
            ));
        } catch (ParseException e) {

        }
    }
}
