package com.example.jameskoss.cosc341_project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Schedule {
    private ArrayList<ScheduleEvent> events;
    public Schedule() {
        events = new ArrayList<ScheduleEvent>();
    }
    public Schedule(String filename) {
        this();
        this.createFromFile(filename);
    }
    public ArrayList<ScheduleEvent> getEvents() {
        return events;
    }
    public void createFromFile(String file) {
        String data = "";
        String line;
        int counter = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(",");
                ScheduleEvent e = new ScheduleEvent(Integer.parseInt(
                        eventData[0]),
                        eventData[1],
                        eventData[2],
                        eventData[3],
                        Integer.parseInt(eventData[4]),
                        eventData[5],
                        eventData[6],
                        eventData[7]
                    );
                this.events.add(e);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
