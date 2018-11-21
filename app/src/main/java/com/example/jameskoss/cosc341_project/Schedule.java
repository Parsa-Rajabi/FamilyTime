package com.example.jameskoss.cosc341_project;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

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
        final float scale = c.getResources().getDisplayMetrics().density;
        for (int i = 0; i < events.size(); i++) {
            Event currentEvent = events.get(0);
            Date start = currentEvent.getStartTime();
            Date end = currentEvent.getEndTime();
            Calendar cs = Calendar.getInstance();
            Calendar ce = Calendar.getInstance();
            Calendar cd = Calendar.getInstance();
            cs.setTime(start);
            ce.setTime(end);
            cd.setTime(day);
            if (!(cd.get(Calendar.DAY_OF_YEAR) < cs.get(Calendar.DAY_OF_YEAR) || cd.get(Calendar.YEAR) < cs.get(Calendar.YEAR) || cd.get(Calendar.DAY_OF_YEAR) > ce.get(Calendar.DAY_OF_YEAR) || cd.get(Calendar.YEAR) > ce.get(Calendar.YEAR))) {
                //this event is today
                Button btn = new Button(c);
                btn.setId(View.generateViewId());
                btn.setEllipsize(TextUtils.TruncateAt.END);
                btn.setText(c.getResources().getString(R.string.event_title_format,currentEvent.getTitle(),currentEvent.getLocation()));
                btn.setBackgroundColor(Color.parseColor(currentEvent.getColour()));
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.setGravity(Gravity.FILL);
                int startIdx = 0;
                int endIdx = 47;
                if (cd.get(Calendar.DAY_OF_YEAR) == cs.get(Calendar.DAY_OF_YEAR) && cd.get(Calendar.YEAR) == cs.get(Calendar.YEAR)) {
                    startIdx = getIndexForTime(start);
                }
                if (cd.get(Calendar.DAY_OF_YEAR) == ce.get(Calendar.DAY_OF_YEAR) && cd.get(Calendar.YEAR) == ce.get(Calendar.YEAR)) {
                    endIdx = getIndexForTime(end);
                }
                int len = endIdx - startIdx + 1;
                int pixels = (int) (5 * scale + 0.5f);
                param.setMargins(pixels,pixels,pixels,pixels);
                param.columnSpec = GridLayout.spec(1,1,1f);
                param.rowSpec = GridLayout.spec(startIdx,len);
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
    public void testStuff(GridLayout gridlayout, Context c, int amt) {
        final float scale = c.getResources().getDisplayMetrics().density;
        for (int i = 0; i < amt; i++) {
            //TODO: Change buttons to TextView so that its possible to more dynamically set its width/height in the gridlayout
            TextView tv = new TextView(c);
            tv.setId(View.generateViewId());
            tv.setText(c.getResources().getString(R.string.event_title_format, "TestStuff"+i, "Test Location "+i));
            tv.setBackgroundColor(Color.parseColor("#88dd88aa"));
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.setGravity(Gravity.FILL);
            int startIdx = (int)Math.floor(Math.random()*40);
            int len = (int)Math.ceil(Math.random()*6);
            int pixels = (int) (5 * scale + 0.5f);
            param.height = GridLayout.LayoutParams.MATCH_PARENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.setMargins(pixels, pixels, pixels, pixels);
            param.columnSpec = GridLayout.spec(1, 1, 1f);
            param.rowSpec = GridLayout.spec(startIdx, len);
            tv.setLayoutParams(param);
            gridlayout.addView(tv);
        }
    }
}
