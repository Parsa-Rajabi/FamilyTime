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
    public ScheduleEvent(int id, String title, String startDateTime, String stopDateTime, int recurring, String location, String colour, String notes) {

    }
}
