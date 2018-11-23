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
    public Schedule() {
        events = new ArrayList<Event>();
    }
    public Schedule(String filename, Context ac) {
        this();
        this.createFromFile(filename, ac);
    }
    public ArrayList<Event> getEvents() {
        return events;
    }
    public void createFromFile(String file, Context ac) {
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
                    Event e = new Event(
                            eventData[0],
                            eventData[1],
                            new SimpleDateFormat("MM/dd/yyy hh:mm a").parse(eventData[2]),
                            new SimpleDateFormat("MM/dd/yyy hh:mm a").parse(eventData[3]),
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
    public void printDay(Date day, GridLayout gridlayout, int gridLayoutColIdx, int gridLayoutRowOffset, Context c, Application a) {
        final Context context = c;
        final Application applic = a;
        Log.v("printDay","printDay reached. Day:"+day.toString());
        final float scale = c.getResources().getDisplayMetrics().density;
        Log.e("printDay","Events.size: "+events.size());
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
            Log.e("printDay","Comparing values: {\nCD.D: "+cd.get(Calendar.DAY_OF_YEAR)+"; CD.Y: "+cd.get(Calendar.YEAR)+";\nCS.D: "+cs.get(Calendar.DAY_OF_YEAR)+"; CS.Y: "+cs.get(Calendar.YEAR)+";\nCE.D: "+ce.get(Calendar.DAY_OF_YEAR)+"; CE.Y: "+ce.get(Calendar.YEAR)+";\n}");
            if (!(cd.get(Calendar.DAY_OF_YEAR) < cs.get(Calendar.DAY_OF_YEAR) || cd.get(Calendar.YEAR) < cs.get(Calendar.YEAR) || cd.get(Calendar.DAY_OF_YEAR) > ce.get(Calendar.DAY_OF_YEAR) || cd.get(Calendar.YEAR) > ce.get(Calendar.YEAR))) {
                Log.e("printDay","Event found today: Start("+start.toString()+") End("+end.toString()+")");
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
                        applic.startActivity(i);
                    }
                });
                GradientDrawable gd = (GradientDrawable)btn.getBackground();
                gd.setColor(Color.parseColor(currentEvent.getColour()));
                gd.setAlpha(180);
                gd.setSize(100,10);
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
                int len = endIdx - startIdx;
                int pixels = (int) (5 * scale + 0.5f);
                int sampleHeight = gridlayout.getChildAt(20).getHeight(); //grab a sample textview for the height;
                Log.e("printDay", "sampleHeight: "+sampleHeight);
                if (sampleHeight <= 0) sampleHeight = 67;
                int newHeight = sampleHeight*len - pixels*2 + len/2;
                Log.e("printDay", "newHeight: "+newHeight);
                if (newHeight < 0) newHeight = 0;
                Log.e("printDay", "newHeightFinal: "+newHeight);
                btn.setHeight(newHeight);
                param.setMargins(pixels,pixels,pixels,pixels);
                param.columnSpec = GridLayout.spec(gridLayoutColIdx,1,1f);
                param.rowSpec = GridLayout.spec(startIdx+gridLayoutRowOffset,len);
                Log.e("printDay", "Columns available: "+gridlayout.getColumnCount());
                Log.e("printDay","Row: "+(startIdx+gridLayoutRowOffset)+"; Col: "+gridLayoutColIdx);
                gridlayout.addView(btn, param);
            }
        }
    }

    public void printWeek(Date sunday, GridLayout gridlayout, Context c, Application a) {
        Log.v("printWeek","printWeek reached. Sunday:"+sunday.toString());
        final float scale = c.getResources().getDisplayMetrics().density;
        Log.e("printWeek","Events.size: "+events.size());
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
    public void testStuff(GridLayout gridlayout, Context c, int amt) {
        final float scale = c.getResources().getDisplayMetrics().density;
        int len;
        for (int i = 0; i < amt; i++) {
            Button btn = new Button(c);
            btn.setId(View.generateViewId());
            btn.setText(c.getResources().getString(R.string.event_title_format, "TestStuff"+i, "Test Location "+i));
            btn.setBackgroundResource(R.drawable.eventblob);
            GradientDrawable gd = (GradientDrawable)btn.getBackground();
            gd.setColor(Color.parseColor("#88dd88aa"));
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.setGravity(Gravity.CLIP_HORIZONTAL);
            int startIdx = (int)Math.floor(Math.random()*40);
            len = (int)Math.ceil(Math.random()*6);
            int pixels = (int) (5 * scale + 0.5f);
            btn.setPadding(pixels,pixels,pixels,pixels);
            btn.setMaxHeight((int)(10 * scale + 0.5f));
            param.height = GridLayout.LayoutParams.MATCH_PARENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.setMargins(pixels, pixels, pixels, pixels);
            param.columnSpec = GridLayout.spec(1, 1, 1f);
            param.rowSpec = GridLayout.spec(startIdx, len, 1f);
            btn.setLayoutParams(param);
            gridlayout.addView(btn);
        }
    }
}
