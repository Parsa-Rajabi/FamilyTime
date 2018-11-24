package com.example.jameskoss.cosc341_project;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
    private ArrayList<Event> events;
    private String superColour;
    private boolean doOverride = false;
    private Schedule() {
        events = new ArrayList<Event>();
    }
    Schedule(String filename, Context ac, int colourOverride) {
        this();
        if (colourOverride < 0) {
            this.doOverride = false;
        } else {
            this.doOverride = true;
            String[] eventColours = ac.getResources().getStringArray(R.array.eventcolorsskeetskeet);
            this.superColour = eventColours[colourOverride%eventColours.length];
        }
        this.createFromFile(filename, ac);
    }
    public ArrayList<Event> getEvents() {
        return events;
    }
    private void createFromFile(String file, Context ac) {
        Log.w("createFromFile","createFromFile reached. Filename: "+file);
        String line;
        try {
            File fi = new File(ac.getFilesDir(), file);
            Log.w("createFromFile","File exists: "+fi.exists());
            BufferedReader br = new BufferedReader(new FileReader(fi));
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                Log.w("createFromFile","Read Line: "+line);
                String[] eventData = line.split(",");
                try {
                    boolean overruled = false;
                    Date skeet1 = null;
                    Date skeet2 = null;
                    if (eventData[0].substring(eventData[0].length()-3,eventData[0].length()-1).equals("-e")) {
                        //this is a recurring event. Here we go.
                        overruled = true;
                        int recursionLevel = Integer.parseInt(eventData[5]);
                        int modifier = 0;
                        int mod2 = 1;
                        int eventOccurence = Integer.parseInt(eventData[0].substring(eventData[0].length()-1));
                        Calendar cs = Calendar.getInstance();
                        Calendar ce = Calendar.getInstance();
                        cs.setTime(new SimpleDateFormat("MM/dd/yyy hh:mm a").parse(eventData[2]));
                        ce.setTime(new SimpleDateFormat("MM/dd/yyy hh:mm a").parse(eventData[3]));
                        switch(recursionLevel) {

                            case 1: //daily
                                modifier = Calendar.DATE;
                                break;
                            case 2: //weekly
                                modifier = Calendar.DATE;
                                mod2 = 7;
                                break;
                            case 3: //monthly
                                modifier = Calendar.MONTH;
                                break;
                            case 4: //annually
                                modifier = Calendar.YEAR;
                                break;
                        }
                        cs.add(modifier,eventOccurence*mod2);
                        ce.add(modifier,eventOccurence*mod2);
                        skeet1 = cs.getTime();
                        skeet2 = ce.getTime();
                    }
                    Event e = new Event(
                            eventData[0],
                            eventData[1],
                            (overruled?skeet1:new SimpleDateFormat("MM/dd/yyy hh:mm a").parse(eventData[2])),
                            (overruled?skeet2:new SimpleDateFormat("MM/dd/yyy hh:mm a").parse(eventData[3])),
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
                    Log.e("createFromFile","Zoinks Scoob, we got a problem!");
                }
            }
            Log.w("createFromFile", "Read "+count+" lines.");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    void printDay(Date day, GridLayout gridlayout, int gridLayoutColIdx, int gridLayoutRowOffset, Context c, Application a) {
        final Context context = c;
        final Application applic = a;
        final float scale = c.getResources().getDisplayMetrics().density;
        for (int i = 0; i < events.size(); i++) {
            final Event currentEvent = events.get(i);
            Date start = currentEvent.getStartTime();
            Date end = currentEvent.getEndTime();
            Calendar cs = Calendar.getInstance();
            Calendar ce = Calendar.getInstance();
            Calendar cd = Calendar.getInstance();
            cs.setTime(start);
            ce.setTime(end);
            cd.setTime(day);
            if (!(cd.get(Calendar.DAY_OF_YEAR) < cs.get(Calendar.DAY_OF_YEAR) || cd.get(Calendar.YEAR) < cs.get(Calendar.YEAR) || cd.get(Calendar.DAY_OF_YEAR) > ce.get(Calendar.DAY_OF_YEAR) || cd.get(Calendar.YEAR) > ce.get(Calendar.YEAR))) {
                Log.w("printDay","Event found today: Start("+start.toString()+") End("+end.toString()+")");
                //this event is today
                Button btn = new Button(c);
                btn.setId(View.generateViewId());
                btn.setEllipsize(TextUtils.TruncateAt.END);
                btn.setText(c.getResources().getString(R.string.event_title_format,currentEvent.getTitle(),(currentEvent.getLocation().equals("rFTls3RIjMEOue603GBj")?"":"\n"+currentEvent.getLocation())));
                btn.setBackgroundResource(R.drawable.eventblob);
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Code here executes on main thread after user presses button
                        Intent i = new Intent(context, CreateEvent.class);
                        Bundle b = new Bundle();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String selectedDay = "";
                        if (((GlobalDateVariables) applic).getSelectedDay() < 10) {
                            selectedDay = "0" + ((GlobalDateVariables) applic).getSelectedDay();
                        }
                        String strDate = ((GlobalDateVariables) applic).getSelectedMonth() + "/" + selectedDay + "/" + ((GlobalDateVariables) applic).getSelectedYear();
                        Date todayDate = new Date();

                        String dateToPass = sdf.format(todayDate);

                        String timestamp = currentEvent.getId();
                        b.putString("date", dateToPass);
                        b.putBoolean("present", false);
                        b.putString("timestamp",timestamp);
                        b.putString("username", "Mothership");
                        i.putExtras(b);
//                        applic.startActivity(i);
                        context.startActivity(i);       //RACHELLE CHANGED THIS LINE
                    }
                });
                GradientDrawable gd = (GradientDrawable)btn.getBackground();
                gd.setColor(Color.parseColor(((this.doOverride)?this.superColour:currentEvent.getColour())));
                gd.setAlpha(180);
                int startIdx = 0;
                int endIdx = 47;
                if (cd.get(Calendar.DAY_OF_YEAR) == cs.get(Calendar.DAY_OF_YEAR) && cd.get(Calendar.YEAR) == cs.get(Calendar.YEAR)) {
                    startIdx = getIndexForTime(start);
                }
                if (cd.get(Calendar.DAY_OF_YEAR) == ce.get(Calendar.DAY_OF_YEAR) && cd.get(Calendar.YEAR) == ce.get(Calendar.YEAR)) {
                    endIdx = getIndexForTime(end);
                }
                int len = endIdx - startIdx;
                int pixels = (int) (5 * scale + 0.5f);
                int sampleHeight = gridlayout.getChildAt(20).getMeasuredHeight(); //grab a sample textview for the height;
                if (sampleHeight <= 0) sampleHeight = 67;
                int newHeight = sampleHeight*len - pixels*2 + len/2;
                if (newHeight < 0) newHeight = 0;
                btn.setHeight(newHeight);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.setGravity(Gravity.FILL);
                param.setMargins(pixels,pixels,pixels,pixels);
                param.columnSpec = GridLayout.spec(gridLayoutColIdx,1,1f);
                param.rowSpec = GridLayout.spec(startIdx+gridLayoutRowOffset,len);
                gridlayout.addView(btn, param);
            }
        }
    }

    void printWeek(Date sunday, GridLayout gridlayout, Context c, Application a) {
        Log.w("printWeek","printWeek reached. Sunday:"+sunday.toString());
        Log.w("printWeek","Events.size: "+events.size());
        for (int i = 0; i < 7; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sunday);
            cal.add(Calendar.DATE, i);
            this.printDay(cal.getTime(),gridlayout,i,1,c,a);
            Log.w("printWeek","Date printed: "+cal.getTime().toString());
            Log.w("printWeek","Colidx: "+(i));
        }
    }

    private int getIndexForTime(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return (hour*2) + (minute/30);
        //NOTE: Returns an int from 0 to 47 inclusive based on passed date
    }
}
