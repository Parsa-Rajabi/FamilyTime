package com.example.jameskoss.cosc341_project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.sql.Array;
import java.sql.Timestamp;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateEvent extends AppCompatActivity implements RecurringDialog.RecurringDialogListener {

    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private int frequency;
    private int repetitions;
    private String colour;
    private String username;
    private ArrayList<String> updatedEvent;
    private String eventTimestamp;   //will come in bundle

    private Date startDateObject;
    private Date endDate;

    private Date date;
    private boolean present;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        // TODO: set up receiving intent and bundle
        Intent calendarViewIntent = getIntent();
        Bundle calendarViewBundle = calendarViewIntent.getExtras();
        String date = calendarViewBundle.getString("date");
        present = calendarViewBundle.getBoolean("present");
        eventTimestamp = calendarViewBundle.getString("timestamp");        //if we are gonna edit an event, will equal -1 if we are creating new event
//        eventTimestamp = "-1";
//        date = new GregorianCalendar(2018,10,11).getTime(); //purely for testing purposes
//        username = "test";
        username = calendarViewBundle.getString("username");     //TESTING
        try {
            this.date = dateFormat.parse(date);
        }
        catch(ParseException e) {
            sendToast("cannot create date object");
        }

        //sets initial colour to button
        this.colour = "#ffff00";
        Button colourBtn = findViewById(R.id.event_colourButton);
        colourBtn.setBackgroundColor(Color.parseColor("#ffff00"));

        // TODO: add title to app
        getSupportActionBar().setTitle("FamilyTime");

        populateSpinners();

        setInitialDates();

        //allows user to change dates
        onAllDayCheck();
        onStartDateClick();
        onEndDateClick();

        onTimeInputChange();        //automatically update and suggest times

        onColourButtonClick();      //open colour class

        onRecurringEventClick();    //open dialog

        onConfirmButtonClick();

        if ( eventTimestamp.contains("e")) {
            updateFile(eventTimestamp);
            populateFieldsOnEditEvent(updatedEvent);
        }
        else if ( Long.parseLong(eventTimestamp) != -1 ) {
            updateFile(eventTimestamp);
            populateFieldsOnEditEvent(updatedEvent);
        }

        else {
            Button deleteEventBtn = findViewById(R.id.event_deleteBtn);
            deleteEventBtn.setEnabled(false);
        }

        onCancelButtonClick();
        onDeleteEventButtonClick();
    }

    /**
     * modifying event
     */
    /*
    if timestamp is not -1, then I have to find
     */
    private void updateFile (String timeStamp) {
        String scheduleFileName = "test.txt";

        File scheduleFile = new File(getApplicationContext().getFilesDir(), scheduleFileName);
        File tempFile = new File(getApplicationContext().getFilesDir(),"temp.txt");

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            Log.e("fileStuff", "you made it into the try");
            reader = new BufferedReader(new FileReader(scheduleFile));
            Log.e("fileStuff", "buffered reader initialized");
            writer = new BufferedWriter(new FileWriter(tempFile));
            Log.e("fileStuff", "buffered writer initialized");


            Log.e("fileStuff", "buffer reader and writer initialized");

            String line;

            //case 1: event is not repeating
            //case 2: event is repeating
            boolean repeating = timeStamp.contains("e");
            String chainId = "";
            if ( repeating ) {
                chainId = timeStamp.substring(0, timeStamp.indexOf("-"));
            }

            while( (line = reader.readLine()) != null ) {
                //9 fields in a line
                String [] event = line.split(",");

                event[4] = (event[4] + "").trim();
                chainId = (chainId + "").trim();


                if ( event[0].equals(timeStamp + "") && !repeating) {
                    this.updatedEvent = arrayToArrayList(event);
                    continue;
                }
                else if ( repeating && event[4].equals(chainId)) {
                    this.updatedEvent = arrayToArrayList(event);
                    continue;
                }
                //else if ( repeating && event[4].equals())
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();


            boolean successful = tempFile.renameTo(scheduleFile);
            sendToast(successful + "");
        }
        catch (IOException e) {
            sendToast("cannot write to file");
            e.printStackTrace();
        }
    }
    /*
    populate fields for an event that is being edited
     */
    private void populateFieldsOnEditEvent(ArrayList<String> event) {
        EditText titleEditText = findViewById(R.id.event_titleInput);
        TextView startDateTextView = findViewById(R.id.event_startDate);
        TextView endDateTextView = findViewById(R.id.event_endDate);
        EditText starHourEditText = findViewById(R.id.event_startTimeInput);
        EditText endHourEditText = findViewById(R.id.event_endTimeInput);
        Spinner startSpinner = findViewById(R.id.event_startSpinner);
        Spinner endSpinner = findViewById(R.id.event_endSpinner);
        TextView recurringStatusTextView = findViewById(R.id.event_recurringStatus);
        EditText locationEditText = findViewById(R.id.event_locationInput);
        Button colourButton = findViewById(R.id.event_colourButton);
        EditText notesEditText = findViewById(R.id.event_noteInput);

        //title
        titleEditText.setText(event.get(1));

        String [] startDateTime = event.get(2).split(" ");
        startDateTextView.setText(startDateTime[0]);
        starHourEditText.setText(startDateTime[1]);
        if ( startDateTime[2].equals("AM") ) {
            startSpinner.setSelection(0);
        }
        else {
            startSpinner.setSelection(1);
        }

        String [] endDateTime = event.get(3).split(" ");
        endDateTextView.setText(endDateTime[0]);
        endHourEditText.setText(endDateTime[1]);
        if ( endDateTime[2].equals("PM") ) {
            endSpinner.setSelection(1);
        }
        else {
            endSpinner.setSelection(0);
        }

        if ( Long.parseLong(event.get(4)) != -1 ) {
            switch (event.get(5)) {
                case "0" :
                    recurringStatusTextView.setText("does not repeat");
                    break;
                case "1" :
                    recurringStatusTextView.setText("once a day");
                    break;
                case "2":
                    recurringStatusTextView.setText("once a week");
                    break;
                case "3" :
                    recurringStatusTextView.setText("once a month");
                    break;
                case "4" :
                    recurringStatusTextView.setText("once a year");
            }
        }

        this.frequency = Integer.parseInt(event.get(5));
        this.repetitions = Integer.parseInt(event.get(6));


        if ( isEmpty(event.get(7)) ) {
            locationEditText.setText("");
        }
        else {
            locationEditText.setText(event.get(7));
        }

        colourButton.setBackgroundColor(Color.parseColor(event.get(8)));

        if ( isEmpty(event.get(9)) ) {
            notesEditText.setText("");
        }
        else {
            notesEditText.setText(event.get(9));
        }

    }

    /**
     * things that are gonna happen dynamically yikes
     */

    /*
    when user clicks on the text view beside the recurring label, it will open a dialog box
    prompting user for input
     */
    private void onRecurringEventClick() {
        TextView recurringStatus = findViewById(R.id.event_recurringStatus);
        recurringStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecurringEventDialog(); //opens dialog box
            }
        });
    }
    /*
    opens event dialog fragment
     */
    private void openRecurringEventDialog() {
        RecurringDialog dialog = new RecurringDialog();
        dialog.show(getSupportFragmentManager(), "recurring dialog");
    }
    /*
    method from RecurringDialogListener interface. If user enters invalid information into dialog, it
    will give user the an error toast indicating what is wrong and re-open the dialog box
     */
    @Override
    public void applyTexts(String text, int level, int repetitions) {
        TextView status = findViewById(R.id.event_recurringStatus);
        //"does not repeat" selected but with some number of recurring events
        if ( level == 0 && repetitions != 0 ) {
            this.repetitions = 0;
            frequency = level;
            sendToast("You have created a non-repeating event");
            status.setText(text);
        }
        //cannot have negative recurring events
        else if ( repetitions < 0 ) {
            sendToast("cannot have a number of repetitions less than 0. Please input again");
            openRecurringEventDialog();
        }
        //cannot have an event that repeats where there are no repetitions
        else if ( level != 0 && repetitions < 1 ) {
            sendToast("Please input a number of times to repeat your event");
            openRecurringEventDialog();
        }
        //everything is gucci and event(s) is created :)
        else {
            sendToast("Event will repeat " + text + " " + repetitions + " more times");
            frequency = level;
            this.repetitions = repetitions;
            status.setText(text);
        }
    }
    /*
    when user changes the start time this will dynamically update the end time and end date elements
    to a valid date/time and try to predict users event
     */
    private void onTimeInputChange () {
        //TODO: is there where it all goes wrong (declare views in methods)
//        final TextView startDate = findViewById(R.id.event_startDate);
//        final TextView endDate = findViewById(R.id.event_endDate);
        EditText startTime = findViewById(R.id.event_startTimeInput);
        EditText endTime = findViewById(R.id.event_endTimeInput);
//        final Spinner startSpinner = findViewById(R.id.event_startSpinner);
//        final Spinner endSpinner = findViewById(R.id.event_endSpinner);

        startTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    TextView startDate = findViewById(R.id.event_startDate);
                    TextView endDate = findViewById(R.id.event_endDate);
                    EditText startTime = findViewById(R.id.event_startTimeInput);
                    EditText endTime = findViewById(R.id.event_endTimeInput);
                    Spinner startSpinner = findViewById(R.id.event_startSpinner);
                    Spinner endSpinner = findViewById(R.id.event_endSpinner);

                    String startTimeStr = startTime.getText().toString();
                    String endTimeStr = endTime.getText().toString();

                    //ensures user has entered a valid time into edit text
                    if ( !validTime(startTimeStr) ) {
                        sendToast("Please enter start time in the form HH:MM");
                    }
                    else {
                        int colonIndexStart = startTimeStr.indexOf(":");
                        int startHour = Integer.parseInt(startTimeStr.substring(0, colonIndexStart));
                        int startMinutes = Integer.parseInt(startTimeStr.substring(colonIndexStart + 1, startTimeStr.length()));
                        String startAmPm = startSpinner.getSelectedItem().toString();
                        String startDateStr = startDate.getText().toString();
                        Date startDateObject;
                        try {
                            startDateObject = dateFormat.parse(startDateStr);
                        }
                        catch (ParseException e) {
                            sendToast("Failed to create start date object");
                            startDateObject = date;
                        }

                        int colonIndexEnd = endTimeStr.indexOf(":");
                        int endHour = Integer.parseInt(endTimeStr.substring(0, colonIndexEnd));
                        int endMinutes = Integer.parseInt(endTimeStr.substring(colonIndexEnd + 1, endTimeStr.length()));
                        String endAmPm = endSpinner.getSelectedItem().toString();
                        String endDateStr = endDate.getText().toString();
                        Date endDateObject;
                        try {
                            endDateObject = dateFormat.parse(endDateStr);
                        } catch (ParseException e) {
                            sendToast("Failed to create end date object");
                            endDateObject = date;
                        }

                        if (!validTime(startTimeStr)) {
                            sendToast("Please insert start time in form HH:MM");
                        } else {
                            if (startAmPm.equals("pm") && endAmPm.equals("am")) {
                                //start: 12 pm, end: 1 pm
                                if (startHour == 12) {
                                    endHour = 1;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(1);
                                }
                                //start: 11 am, end: 12 pm
                                else if (startHour == 11) {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(0);
                                    //get date object from start and add one to it
                                    try {
                                        Date newEndDate = dateFormat.parse(startDate.getText().toString());
                                        newEndDate = addOneDay(newEndDate);
                                        setDateTextView(endDate, newEndDate);

                                    }
                                    catch (ParseException e) {
                                        sendToast("Cannot create Date object");
                                    }

                                }
                                else {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(1);
                                }

                            }
                            else if (startAmPm.equals("pm") && startAmPm.equals("pm")) {
                                if (startHour > endHour && startHour != 12) {
                                    //end hour will be 12 AM, therefore day must be changed
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                }
                                else if ( startHour == 1 ) {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(1);
                                }
                                else if (startHour == 11 && startHour > endHour) {
                                    try {
                                        Date newEndDate = dateFormat.parse(startDate.getText().toString());
                                        newEndDate = addOneDay(newEndDate);
                                        setDateTextView(endDate, newEndDate);
                                        endSpinner.setSelection(0);
                                        endHour = startHour + 1;

                                    }
                                    catch (ParseException e) {
                                        sendToast("Cannot create Date object");
                                    }
                                }
                                else if (startHour == endHour && startMinutes > endMinutes) {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                }

                            }
                            else if (startAmPm.equals("am") && endAmPm.equals("am")) {
                                if ( startHour == 11 ) {
                                    endHour = 12;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(1);
                                }
                                else if (startHour > endHour) {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                } else if (startHour == endHour && startMinutes > endMinutes) {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                }
                            }
                            else if (startAmPm.equals("am") && endAmPm.equals("pm") && startDateObject.compareTo(endDateObject) > 0) {
                                if (startHour == 12) {
                                    endHour = 1;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(0);
                                } else if (startHour == 11) {
                                    endHour = startHour + 1;
                                    endMinutes = startMinutes;
                                    endSpinner.setSelection(1);
                                } else {
                                    endHour = startHour + 1;
                                }
                            }

                            if (endMinutes == 0) {
                                endTimeStr = endHour + ":" + "00";
                            }
                            else {
                                endTimeStr = endHour + ":" + endMinutes;
                            }
                            endTime.setText(endTimeStr);
                            endDate.setText(startDateStr);
                        }
                    }

                }
            }
        });

        endTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /*
            ensure that user enters a valid time for the end time
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ( !hasFocus ) {
                    TextView startDate = findViewById(R.id.event_startDate);
                    TextView endDate = findViewById(R.id.event_endDate);
                    EditText endTime = findViewById(R.id.event_endTimeInput);
                    String endTimeStr = endTime.getText().toString();

                    if ( !validTime(endTimeStr) ) {
                        sendToast("Please enter a valid end time in form HH:MM");
                    }
                }
            }
        });
    }

    /*
    when user clicks on all day check mark, it updates time and spinner views. When user unclicks the
    check, it reverts all views back to previous state
     */
    private void onAllDayCheck () {
        final CheckBox allDayBox = findViewById(R.id.event_allDayCheckBox);

        allDayBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( allDayBox.isChecked() ) {
                    updateDateTimeOnAllDayEvent();
                }
                else {
                    EditText startTime = findViewById(R.id.event_startTimeInput);
                    EditText endTime = findViewById(R.id.event_endTimeInput);
                    Spinner startSpinner = findViewById(R.id.event_startSpinner);
                    Spinner endSpinner = findViewById(R.id.event_endSpinner);
                    startTime.setEnabled(true);
                    endTime.setEnabled(true);
                    startSpinner.setEnabled(true);
                    endSpinner.setEnabled(true);
                    endSpinner.setEnabled(true);

                }
            }
        });
    }

    /*
    updates views when user clicks on the all day event check mark. This causes the time and spinners
    to be disabled
     */
    private void updateDateTimeOnAllDayEvent() {
        TextView startDate = findViewById(R.id.event_startDate);
        TextView endDate = findViewById(R.id.event_endDate);

        EditText startTime = findViewById(R.id.event_startTimeInput);
        EditText endTime = findViewById(R.id.event_endTimeInput);

        Spinner startSpinner = findViewById(R.id.event_startSpinner);
        Spinner endSpinner = findViewById(R.id.event_endSpinner);

        //default times
        startTime.setText("12:00");
        endTime.setText("11:59");

        startSpinner.setSelection(0);
        endSpinner.setSelection(1);

        //disable time views
        setDateTextView(startDate, date);
        setDateTextView(endDate, date);
        startTime.setEnabled(false);
        endTime.setEnabled(false);
        startSpinner.setEnabled(false);
        endSpinner.setEnabled(false);
    }
     /*
    when user clicks on the start date TextView, it will open a calendar dialogue and the TextView will
    update to the date that the user selects from the calendar
     */
    private void onStartDateClick () {
        final TextView startDateTextView = findViewById(R.id.event_startDate);
        final TextView endDateTextView = findViewById(R.id.event_endDate);

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCalendarDialogueBox(startDateSetListener);
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("CreateEvent", "onDateSet: mm/dd/yy: " + month + "/" + dayOfMonth + "/" + year);
                String date1 = (month + 1) + "/" + dayOfMonth + "/" + year;
                try {
                    startDateObject = dateFormat.parse(date1);
                }
                catch (ParseException e) {
                    sendToast("Error in creating Date object");
                }
                startDateTextView.setText(date1);
                endDateTextView.setText(date1);
            }
        };
    }
    /*
    when user clicks on the end date text view, a calendar dialogue box will open and the user can select
    the end date of their event. The end date text view will update to the date selected by user
     */
    private void onEndDateClick() {
        final TextView endDate = findViewById(R.id.event_endDate);

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCalendarDialogueBox(endDateSetListener);
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("CreateEvent", "onDateSet: mm/dd/yy: " + month + "/" + dayOfMonth + "/" + year);
                String date = (month +1) + "/" + dayOfMonth + "/" + year;
                endDate.setText(date);
            }
        };

    }

    private void createCalendarDialogueBox (DatePickerDialog.OnDateSetListener dateSetListener ) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(CreateEvent.this,
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                dateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * things that must be done when page is loaded
     */
    /*
    get date to populate form with from calendar view. The date will be dependent on what view and
    where the user creates the events from. A boolean signifiying whether the given date is the present
    date will also be passed in. If present, then the next hour will represent the start time and the
    following hour will be the end time. The date will also be adjusted in accordance with the time.
    If not present, date is loaded and start and end times are set to 10 am and 11 am respectively
     */
    private void setInitialDates() {
        //TODO: Replace this with date from the bundle
//        Date date = new GregorianCalendar(2018, 10, 11).getTime();   //note January = 0 month
//        boolean present = true;         //is that date given from the other activity the present day?

        TextView start = findViewById(R.id.event_startDate);
        TextView end = findViewById(R.id.event_endDate);
        EditText startTimeEditView = findViewById(R.id.event_startTimeInput);
        EditText endTimeEditView = findViewById(R.id.event_endTimeInput);
        Spinner startSpinner = findViewById(R.id.event_startSpinner);
        Spinner endSpinner = findViewById(R.id.event_endSpinner);

        //set start and end days to the given date
        Date startDate = date;
        Date endDate = date;

                if (present) {
            //TODO: when done testing uncomment the next line
            String formattedDate = getCurrentTimeHHMM();
//            String formattedDate = "6:01 AM";          //this is for TESTING
            int colonIndex = formattedDate.indexOf(':');
            int currentHour = Integer.parseInt(formattedDate.substring(0,colonIndex));
            int initialStartHour = currentHour + 1;
            int initialEndHour = currentHour + 2;

            String AmPmStatus = formattedDate.substring(formattedDate.length()-2, formattedDate.length());  //determine if time is AM or PM

            if ( AmPmStatus.equals("PM") ) {
                //case where current time = 11:XX PM, then start = 12 AM and end = 1 AM
                if ( initialStartHour >= 12 ) {
                    //update dates
                    startDate = addOneDay(startDate);
                    endDate = addOneDay(endDate);

                    //update times
                    initialStartHour = 12;
                    initialEndHour = 1;

                    //spinners are already set to am
                }
                //case where current time = 10:XX PM, start = 11 PM, end = 12 AM
                else if ( initialEndHour >= 12 ) {
                    //modify date
                    endDate = addOneDay(endDate);

                    //set spinners
                    startSpinner.setSelection(1);
                }
                else {
                    //set spinners to pm
                    startSpinner.setSelection(1);
                    endSpinner.setSelection(1);
                }
            }

            else if ( AmPmStatus.equals("AM") ) {
                //case where current time = 11:XX AM, then start = 12 PM and end = 1 PM
                if( initialStartHour >= 12 ) {
                    initialEndHour = 1;
                    startSpinner.setSelection(1);
                    endSpinner.setSelection(1);
                }
                //case where current time = 10:XX AM, then start = 11 AM, end = 12 PM
                else if ( initialEndHour >= 12 ) {
                    endSpinner.setSelection(1);
                }
            }

            String initialStartTime = initialStartHour + ":00";
            String initialEndTime = initialEndHour + ":00";

            setDateTextView(start, startDate);              //set initial date for start based off input from previous activity
            setDateTextView(end, endDate);                  //set initial date for end based off input from previous activity
            startTimeEditView.setText(initialStartTime);    //set initial time for start
            endTimeEditView.setText(initialEndTime);        //set initial time for end
        }
        //default times for non-present day. Note times are set to AM
        else {
            setDateTextView(start,date);
            setDateTextView(end,date);

            startTimeEditView.setText("10:00");
            endTimeEditView.setText("11:00");
        }
    }
    /*
    sets a text view to the date given as a string
     */
    private void setDateTextView(TextView textView, Date date) {
        String text = dateFormat.format(date);
        textView.setText(text);
    }

    /*
    gets current time
    TODO: Don't forget to use this method!!!
     */
    private String getCurrentTimeHHMM() {
        Date time = new Date();
        String strDateFormat = "hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

        return dateFormat.format(time);
    }
    /*
    takes a given date and adds a day to it. Handles cases where month and year change.
     */
    private Date addOneDay( Date date ) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        c.set(year, month, day);
        c.add(Calendar.DAY_OF_MONTH, 1);

        return c.getTime();
    }

    /*
    When colour button is clicked, it will open a window for user to select the colour that they want their
    event to be. Colour is set to yellow as a default
     */
    private void onColourButtonClick() {
        Button button = findViewById(R.id.event_colourButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CreateEvent.this, ColourPop.class), 1);
            }
        });
    }

    /*
    when the colour picker activity returns to the create event activity, it takes the colour of the selected
    button and sets the button on the create event activity to that colour
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Button colourButton = findViewById(R.id.event_colourButton);

        if ( requestCode == 1 ) {
            if( resultCode == Activity.RESULT_OK ) {
                int colour = intent.getIntExtra("hexacode",0);
                String strColour = colour + "";
               /* EditText notes = findViewById(R.id.event_noteInput);
                notes.setText(strColour);*/
                
                if ( strColour.equals("2131099779") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#ffff00"));
                    this.colour = "#ffff00";
                }
                else if ( strColour.equals("2131099682")) {
                    colourButton.setBackgroundColor(Color.parseColor("#ff0000"));
                    this.colour = "#ff0000";
                }
                else if ( strColour.equals("2131099761") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#3333ff"));
                    this.colour = "#3333ff";
                }
                else if ( strColour.equals("2131099651") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#ff3399"));
                    this.colour = "#ff3399";
                }
                else if ( strColour.equals("2131099722") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#ff5050"));
                    this.colour = "#ff5050";
                }
                else if ( strColour.equals("2131099648") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#ff6600"));
                    this.colour = "#ff6600";
                }
                else if ( strColour.equals("2131099649") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#85e0e0"));
                    this.colour = "#85e0e0";
                }
                else if ( strColour.equals("2131099719") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#33cc33"));
                    this.colour = "#33cc33";
                }
                else if ( strColour.equals("2131099650") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#cc0099"));
                    this.colour = "#cc0099";
                }
                else if ( strColour.equals("2131099778") ) {
                    colourButton.setBackgroundColor(Color.parseColor("#006699"));
                    this.colour = "#006699";
                }
            }
        }
    }

    /*
    fill spinners
     */
    private void populateSpinners() {

        Spinner spinner2 = findViewById(R.id.event_startSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.event_twelveHourClockSpinner, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = findViewById(R.id.event_endSpinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.event_twelveHourClockSpinner, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
    }


    private void sendToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /**
     * validation & button clicks (confirm, cancel, delete)
     */
    private void onConfirmButtonClick() {
        Button submitBtn = findViewById(R.id.event_confirmBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ( isValid() ) {
                        sendToast("you did it! Event can be created");
                        ArrayList<Event> list = createEventObjectList();
                        writeToFile(list);
                        finish();
                    }
                }
                catch (ParseException e) {
                    sendToast("event not successfully created");
                }
            }
        });
    }
    /*
    on cancel button
     */
    private void onCancelButtonClick() {
        Button cancel = findViewById(R.id.event_cancelBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //case 1: this is new event user is creating
                //case 2: this is an event that user is modifying
                if ( eventTimestamp.contains("e") ) {
                    ArrayList<Event> list = createEventListFromArrayList(updatedEvent);
                    writeToFile(list);
                    finish();
                }
                else if ( Long.parseLong(eventTimestamp) == -1 ) {
                    finish();
                }
                else {
                    ArrayList<Event> list = createEventListFromArrayList(updatedEvent);
                    writeToFile(list);
                    finish();
                }
            }
        });
    }
    /*
    on delete event button click
     */
    private void onDeleteEventButtonClick() {
        Button deleteEventBtn = findViewById(R.id.event_deleteBtn);

        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                sendToast("Event deleted");
                finish();
            }
        });
    }
    /*
    is valid method used to validate the whole form app, will be used for on submit.
     */
    private boolean isValid() throws ParseException {
        boolean valid = false;
        EditText title = findViewById(R.id.event_titleInput);
        EditText startTimeEditText = findViewById(R.id.event_startTimeInput);
        EditText endTimeEditText = findViewById(R.id.event_endTimeInput);
        TextView startDate = findViewById(R.id.event_startDate);
        TextView endDate = findViewById(R.id.event_endDate);
        Spinner startTimeSpinner = findViewById(R.id.event_startSpinner);
        Spinner endTimeSpinner = findViewById(R.id.event_endSpinner);
        CheckBox allDayCheckBox = findViewById(R.id.event_allDayCheckBox);


        String titleText = title.getText().toString();
        String startTime = startTimeEditText.getText().toString();
        String endTime = endTimeEditText.getText().toString();

        //event must have title
        if ( titleText.trim().length() < 1 ) {
            sendToast("Please enter a title for the event");
        }
        //start and end times must be in valid forms
        else if ( !validTime(startTime) || !validTime(endTime) ) {
            sendToast("Invalid entry for start and or end time. Please enter times in the form HH:MM");
        }
        //start time cannot be after end time
        else if ( createDateObject(startDate,startTimeEditText,startTimeSpinner).compareTo(createDateObject(endDate,endTimeEditText,endTimeSpinner)) > 0 ) {
            sendToast("Start time cannot be after the end time.");
        }
        else if ( createDateObject(startDate,startTimeEditText,startTimeSpinner).compareTo(createDateObject(endDate,endTimeEditText,endTimeSpinner)) == 0 ) {
            sendToast("Event cannot start and end at same time");
        }

        else if ( !startDate.getText().toString().equals(endDate.getText().toString()) ) {
            sendToast("Events cannot span multiple days, please use Recurring Events feature.");
        }

        else {
            valid = true;
        }
        return valid;
    }

    /*
    takes time as a string and will make sure it is a valid time. Note this disregards am/pm rFTls3RIjMEOue603GBj
     */
    private boolean validTime(String time) {
        boolean valid = true;

        int colonIndex;

        if ( !time.contains(":") ) {
            return false;
        }
        else if ( time.length() < 4 || time.length() > 5 ) {
            return false;
        }
        else {
            colonIndex = time.indexOf(":");
            //case where hour is less than 1 or greater than 12
            if (Integer.parseInt(time.substring(0, colonIndex)) < 1 || Integer.parseInt(time.substring(0, colonIndex)) > 12) {
                sendToast("Please enter a time between 1 and 12");
                valid = false;
            }
            else if (time.substring(colonIndex + 1, time.length()).contains(":")) {
                valid = false;
            }
            //case where minute is less than 0 or greater than 59
            else if (Integer.parseInt(time.substring(colonIndex + 1, time.length())) < 0 || Integer.parseInt(time.substring(colonIndex + 1, time.length())) > 59) {
                valid = false;
            }
        }
        return valid;
    }
    /*
    creates a date object using values from date, time and spinner views
     */
    private Date createDateObject (TextView date, EditText time, Spinner amPm) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        String strDate = date.getText().toString().replaceAll("\\s", "");
        String strTime = time.getText().toString().replaceAll("\\s", "");
        String strAmPm = amPm.getSelectedItem().toString().replaceAll("\\s", "");

        String strDateTime = strDate + " " + strTime + " " + strAmPm;
        Date dateObjDateTime = sdf.parse(strDateTime);

        return dateObjDateTime;
    }

    /**
     * writing to a file
     */
    /*
    creates an array list of event objects. Empty fields are going to be filled with rFTls3RIjMEOue603GBj
     */
    private ArrayList<Event> createEventObjectList () throws ParseException{
        ArrayList<Event> eventsCreated = new ArrayList<>();

        TextView titleTextView = findViewById(R.id.event_titleInput);
        TextView startDateTextView = findViewById(R.id.event_startDate);
        TextView endDateTextView = findViewById(R.id.event_endDate);
        EditText startTimeEditText = findViewById(R.id.event_startTimeInput);
        EditText endTimeEditText = findViewById(R.id.event_endTimeInput);
        Spinner startSpinner = findViewById(R.id.event_startSpinner);
        Spinner endSpinner = findViewById(R.id.event_endSpinner);
        //get frequency from private attribute
        //get colour from private attribute
        EditText locationEditText = findViewById(R.id.event_locationInput);
        EditText notesEditText = findViewById(R.id.event_noteInput);

        String strId = System.currentTimeMillis() + "";
        String strTitle = titleTextView.getText().toString();
        Date startDateTime = createDateObject(startDateTextView, startTimeEditText, startSpinner);
        Date endDateTime = createDateObject(endDateTextView, endTimeEditText, endSpinner);
        String strLocation = fillIfEmptyField(locationEditText);
        String strNotes = fillIfEmptyField(notesEditText);

        //case 1: event is not repeating
        if ( repetitions == 0 ) {
            Event event = new Event(strId, strTitle, startDateTime, endDateTime, "-1", frequency, repetitions, strLocation, colour, strNotes);
            eventsCreated.add(event);
        }
        //case 2: event is repeating
        else {
            for ( int i = 0; i <= repetitions; i++ ) {
                String recurringChainId = strId;
                String strRecurringEventId = recurringChainId + "-e" + i;
                Event event = new Event(strRecurringEventId, strTitle, startDateTime, endDateTime, recurringChainId, frequency, repetitions, strLocation, colour, strNotes);
                eventsCreated.add(event);
            }
        }
        return eventsCreated;
    }

    /*
    if edit text view is empty, it will replaced with rFTls3RIjMEOue603GBj to signify that it is an empty string
     */
    private String fillIfEmptyField(EditText field) {
        if ( field.getText().toString().trim().length() == 0 ) {
            return "rFTls3RIjMEOue603GBj";
        }
        else {
            return field.getText().toString();
        }
    }

    private boolean isEmpty(String text) {
        boolean empty = false;
        if ( text.equals("rFTls3RIjMEOue603GBj") ) {
            empty = true;
        }
        return empty;
    }

    private void testObject (Event event) {
        EditText noteSection = findViewById(R.id.event_noteInput);
        noteSection.setText(event.getNote());
    }

    /*
    write to an array list of events to a file
     */
    private void writeToFile(ArrayList<Event> eventList) {
        String filename = username + ".txt";
        FileOutputStream outputStream;

        for ( int i = 0; i < eventList.size(); i++ ) {
            String contents = eventContentsToWriteToFile(eventList.get(i));
            try {
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                outputStream.write(contents.getBytes());
                outputStream.close();
            }
            catch (Exception e ) {
                e.printStackTrace();
            }
        }

    }
    /*
    each user will have their own id that will uniquely identify their schedule text file
     */
    private boolean fileExists() {
        String filename = username + ".txt";
        File file = new File(getApplicationContext().getFilesDir(),filename);

        return false;
    }
    /*
    converts an Event to a String in comma delimited form
     */
    private String eventContentsToWriteToFile(Event event) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String contents = "";
        Date startDateTime = event.getStartTime();
        String strStartDateTime = sdf.format(startDateTime);
        Date endDateTime = event.getEndTime();
        String strEndDateTime = sdf.format(endDateTime);

        contents += event.getId() + "," + event.getTitle() + "," + strStartDateTime + "," + strEndDateTime + ","
                + event.getRecurringChainId() + "," + event.getFrequency() + ","
                + event.getRepetitions() + "," + event.getLocation() + "," + event.getColour() + ","
                + event.getNote() + "\n";

        return contents;
    }

    /*
    James's week date conversion. Passes in a sunday. I need to get sunday of following week and saturday of
    following week
     */
    private String nextWeekDate (int month, int sunday, int year) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");

        String strDate = month + "-" + sunday + "-" + year;
        Date currentDate = sdf2.parse(strDate);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        int nextSundayDay = c.get(Calendar.DATE);
        int nextSundayMonth = c.get(Calendar.MONTH);
        int nextSundayYear = c.get(Calendar.YEAR);

        c.set(nextSundayYear, nextSundayMonth, nextSundayDay);
        c.add(Calendar.DAY_OF_MONTH, 7);

        Date nextSundayDate = c.getTime();

        c.setTime(nextSundayDate);

        int nextSaturdayDay = c.get(Calendar.DATE);
        int nextSaturdayMonth = c.get(Calendar.MONTH);
        int nextSaturdayYear = c.get(Calendar.YEAR);

        c.set(nextSaturdayYear, nextSaturdayMonth, nextSaturdayDay);
        c.add(Calendar.DAY_OF_MONTH, 6);

        Date nextSaturdayDate = c.getTime();

        String nextSaturdayStr = sdf.format(nextSaturdayDate);
        String nextSundayStr = sdf.format(nextSundayDate);

        String label = nextSundayStr + " - " + nextSaturdayStr;
        return label;

    }

    private ArrayList<String> arrayToArrayList(String [] array) {
        ArrayList<String> arrList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            arrList.add(array[i]);
        }
        return arrList;
    }

    /*
    method to create an array list of event from a String array list (will be used for the cancel button)
     */
    private ArrayList<Event> createEventListFromArrayList(ArrayList<String> arrayList) {
        ArrayList<Event> eventList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        if ( Integer.parseInt(arrayList.get(6)) == 0 ) {
            try {
                Event event = new Event(arrayList.get(0), arrayList.get(1), sdf.parse(arrayList.get(2)), sdf.parse(arrayList.get(3)),
                        arrayList.get(4), Integer.parseInt(arrayList.get(5)), Integer.parseInt(arrayList.get(6)), arrayList.get(7),
                        arrayList.get(8), arrayList.get(9));
                eventList.add(event);
            }
            catch (ParseException e) {
                sendToast("cannot create event");
            }
        }
        else {
            for ( int i = 0; i <= Integer.parseInt(arrayList.get(6)); i++ ) {
                String eventId = arrayList.get(4) + "-e" + i;
                try {
                    Event event = new Event(eventId, arrayList.get(1), sdf.parse(arrayList.get(2)), sdf.parse(arrayList.get(3)),
                            arrayList.get(4), Integer.parseInt(arrayList.get(5)), Integer.parseInt(arrayList.get(6)), arrayList.get(7),
                            arrayList.get(8), arrayList.get(9));
                    eventList.add(event);
                }
                catch (ParseException e) {
                    sendToast("cannot create recurring event");
                }
            }
        }

        return eventList;
    }
}
