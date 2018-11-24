package com.example.jameskoss.cosc341_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
        setContentView(R.layout.activity_overlay_popup);
        this.state = readState();
        LinearLayout ll = findViewById(R.id.overlayPopupLinearLayout);
        if (this.state == null) {
            this.state = new HashMap<>();
        }
        String[] eventColours = getResources().getStringArray(R.array.eventcolorsskeetskeet);
        int colourKey = 0;
        // Generate a checkbox per schedule
        File[] schedules = getScheduleFiles();
        for (File f: schedules) {
            final String key = f.getName().substring(0, f.getName().length() - 4);
            if (!state.containsKey(key)) {
                if (key.equals("Mothership")) {
                    state.put(key,true);
                } else {
                    state.put(key, false);
                }
            }
            LinearLayout subll = new LinearLayout(this);
            subll.setOrientation(LinearLayout.HORIZONTAL);
            ll.addView(subll);
            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
            param.setMargins(0,0,0,40);
            ll.setLayoutParams(param);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this);
            btn.setBackgroundColor(Color.parseColor(eventColours[colourKey%eventColours.length]));
            btn.setLayoutParams(param2);
            btn.setPadding(50,20,50,20);
            subll.addView(btn);
            CheckBox cb = new CheckBox(this);
            cb.setPadding(20, 20, 20, 20);
            if (state.get(key)) cb.setChecked(true);
            subll.addView(cb);
            TextView tv = new TextView(this);
            tv.setText(key);
            tv.setPadding(20, 20, 20, 20);
            subll.addView(tv);
        }
        super.onCreate(savedInstanceState);
    }

    public void saveEvents(View v) {
        printStateToFile();
        Intent i = new Intent(this, MainViews.class);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    public HashMap<String, Boolean> readState() {
        File f = new File(getFilesDir(), "statedata");
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                return (HashMap<String, Boolean>)ois.readObject();
            } catch (Exception e) {
                return new HashMap<>();
            }
        } else {
            return new HashMap<>();
        }
    }

    private void saveState() {
        if (this.state == null) {
            this.state = new HashMap<>();
        }
        LinearLayout ll = findViewById(R.id.overlayPopupLinearLayout);
        for (int i = 0; i < ll.getChildCount(); i++) {
            if (ll.getChildAt(i) instanceof LinearLayout && ll.getChildAt(i) != null) {
                LinearLayout subll = (LinearLayout)ll.getChildAt(i);
                CheckBox cb = null;
                TextView keyholder = null;
                for (int j = 0; j < subll.getChildCount(); j++) {
                    if (subll.getChildAt(j) instanceof CheckBox && subll.getChildAt(j) != null) {
                        cb = (CheckBox)subll.getChildAt(j);
                    }
                    if (subll.getChildAt(j) instanceof TextView && subll.getChildAt(j) != null) {
                        keyholder = (TextView)subll.getChildAt(j);
                    }
                }
                boolean val = cb.isChecked();
                String key = keyholder.getText().toString();
                state.put(key,val);
            }
        }
    }
    private void printStateToFile() {
        saveState();
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "statedata")))) {
            writer.writeObject(this.state);
            Log.e("printStateToFile", "Wrote State:"+this.state.toString());
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
