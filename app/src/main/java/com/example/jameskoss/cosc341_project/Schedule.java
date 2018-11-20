package com.example.jameskoss.cosc341_project;

import android.content.Context;
import android.text.Layout;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void printDay(Date day, View v, Context c) {

    }

    public void printWeek(Date sunday, View v, Context c) {

    }
}
