package com.example.jameskoss.cosc341_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class ColourPop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.colour_pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*1.0), (int) (height*1.0));

        onButton1Click();
        onButton2Click();
        onButton3Click();
        onButton4Click();
        onButton5Click();
        onButton6Click();
        onButton7Click();
        onButton8Click();
        onButton9Click();
        onButton10Click();

    }

    private void onButton1Click() {
        Button yellowBtn = findViewById(R.id.popup_yellowBtn);
        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent with hexadecimal code
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.sunshineYellow);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton2Click() {
        Button redBtn = findViewById(R.id.popup_redBtn);
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.bloodOfYourEnemies);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton3Click() {
        Button blueBtn = findViewById(R.id.popup_blueBtn);
        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.myTearsBlue);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton4Click() {
        Button pinkBtn = findViewById(R.id.popup_pink1Btn);
        pinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.ProfessorUmbridgePink);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton5Click() {
        Button pinkBtn = findViewById(R.id.popup_pink2Btn);
        pinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.itsSalmonNotPink);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton6Click() {
        Button orangeBtn = findViewById(R.id.popup_orangeBtn);
        orangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.GoHolland);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton7Click() {
        Button btn = findViewById(R.id.popup_turquoiseBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.LakeLouiseTurquoise);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton8Click() {
        Button btn = findViewById(R.id.popup_greenBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.healthyLawnGreen);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton9Click() {
        Button btn = findViewById(R.id.popup_lightPurpleBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.Lilac);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onButton10Click() {
        Button btn = findViewById(R.id.popup_anotherBlueBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                intent.putExtra("hexacode", R.color.slightlyAggressiveBlue);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private Intent makeIntent() {
        Intent intent = new Intent(this, CreateEvent.class);
        return intent;
    }
}
