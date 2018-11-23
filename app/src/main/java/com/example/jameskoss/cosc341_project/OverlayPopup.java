package com.example.jameskoss.cosc341_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class OverlayPopup extends AppCompatActivity {

    private HashMap<String, Boolean> state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay_popup);
        this.state = readState();
        LinearLayout ll = findViewById(R.id.overlayPopupLinearLayout);
        if (this.state == null) {
            // print text to screen that says no schedule file exists yet.
            TextView tv = new TextView(this);
            tv.setText(R.string.popupNoFileError);
            tv.setGravity(Gravity.CENTER);
            ll.addView(tv);
        } else {
            //TODO generate the checkboxes/etc to set the state
            File[] schedules = getScheduleFiles();
            for (File f: schedules) {
                final String key = f.getName().substring(0,f.getName().length()-4);
                if (!state.containsKey(key)) {
                    state.put(key,false);
                }
                LinearLayout subll = new LinearLayout(this);
                subll.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(subll);
                CheckBox cb = new CheckBox(this);
                cb.setPadding(20,20,20,20);
                if (state.get(key)) cb.setChecked(true);
                cb.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        state.put(key, !state.get(key));
                    }
                });
                subll.addView(cb);
                TextView tv = new TextView(this);
                tv.setText(key);
                tv.setPadding(20,20,20,20);
                subll.addView(tv);
            }
        }
    }

    public void saveEvents(View v) {
        printStateToFile();
        finish();
    }

    private HashMap<String, Boolean> readState() {
        File f = new File(getFilesDir(), "statedata");
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                return (HashMap<String, Boolean>)ois.readObject();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    private void printStateToFile() {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "statedata")))) {
            writer.writeObject( this.state);
            writer.flush();
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private int countSchedules() {
        return getScheduleFiles().length;
    }

    private File[] getScheduleFiles() {
        File fileDir = getApplication().getFilesDir();
        File[] listOfFiles = fileDir.listFiles();
        ArrayList<File> al = new ArrayList<>();
        for (File f: listOfFiles) {
            if (f.getName().endsWith(".txt")) {
                al.add(f);
            }
        }
        File[] files = new File[al.size()];
        return al.toArray(files);
    }

    @Override
    public void onResume() {
        this.state = readState();
        super.onResume();
    }

    @Override
    public void onPause() {
        printStateToFile();
        super.onPause();
    }

}
