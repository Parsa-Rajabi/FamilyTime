package com.example.jameskoss.cosc341_project;

import java.util.Date;
import java.sql.Timestamp;

public class Event {
    String id;
    String title;
    Date startTime;
    Date endTime;
    String recurringChainId;
    int frequency;
    int repetitions;
    String location;
    String colour;
    String note;

    public Event ( String id, String title, Date startTime, Date endTime, String recurringChainId,
                   int frequency, int repetitions, String location, String colour, String note ) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurringChainId = recurringChainId;
        this.repetitions = repetitions;
        this.frequency = frequency;
        this.location = location;
        this.colour = colour;
        this.note = note;

    }

    public String getId () {
        return this.id;
    }

    public String getTitle () {
        return this.title;
    }

    public Date getStartTime () {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public String getRecurringChainId () {
        return this.recurringChainId;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public int getRepetitions() {
        return this.repetitions;
    }

    public String getLocation() {
        return this.location;
    }

    public String getColour() {
        return this.colour;
    }

    public String getNote() {
        return this.note;
    }



}
