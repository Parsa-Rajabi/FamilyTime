package com.example.jameskoss.cosc341_project;

import java.util.Date;

public class ScheduleEvent {
    private int id;
    private String title;
    private Date startDateTime;
    private Date stopDateTime;
    private int recurring; // -1 to or its recurring id;
    private String location;
    private String colour;
    private String notes;
    //TODO flesh out this class with stuff. Maybe getters and setters etc. Can change private attributes to public if you want.
    public ScheduleEvent(int id, String title, String startDateTime, String stopDateTime, int recurring, String location, String colour, String notes) {

    }
    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
}
