package com.example.jameskoss.cosc341_project;
import java.util.Date;

public class Event {
    private long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private long recurringChainId;
    private int frequency;
    private String location;
    private String colour;
    private String note;

    public Event ( long id, String title, Date startTime, Date endTime, long recurringChainId, int frequency, String location, String colour, String note ) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurringChainId = recurringChainId;
        this.frequency = frequency;
        this.location = location;
        this.colour = colour;
        this.note = note;

    }

    public long getId () {
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

    public long getRecurringChainId () {
        return this.recurringChainId;
    }

    public int getFrequency() {
        return this.frequency;
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