package com.example.lampemagique.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import com.example.lampemagique.R;
import com.example.lampemagique.controller.HomeController;
import com.example.lampemagique.controller.LightController;

public class MainActivity extends AppBase{

    public final static String RED = "RED";
    public final static String GREEN = "GREEN";
    public final static String BLUE = "BLUE";

    private Button light;
    private Button plusRed;
    private Button minusRed;
    private Button plusGreen;
    private Button minusGreen;
    private Button plusBlue;
    private Button minusBlue;
    private Button color1;
    private Button color2;
    private Button color3;

    private LightController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.controller = new LightController(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lampe Magique");

        this.light = findViewById(R.id.light);
        this.plusRed = findViewById(R.id.plusRed);
        this.minusRed = findViewById(R.id.minusRed);
        this.plusGreen = findViewById(R.id.plusGreen);
        this.minusGreen = findViewById(R.id.minusGreen);
        this.plusBlue = findViewById(R.id.plusBlue);
        this.minusBlue = findViewById(R.id.minusBlue);
        this.color1 = findViewById(R.id.color1);
        this.color2 = findViewById(R.id.color2);
        this.color3 = findViewById(R.id.color3);

        light.setOnClickListener(controller);
        plusRed.setOnClickListener(controller);
        minusRed.setOnClickListener(controller);
        plusGreen.setOnClickListener(controller);
        minusGreen.setOnClickListener(controller);
        plusBlue.setOnClickListener(controller);
        minusBlue.setOnClickListener(controller);
        color1.setOnClickListener(controller);
        color2.setOnClickListener(controller);
        color3.setOnClickListener(controller);

        int red = getIntent().getIntExtra(MainActivity.RED, 100);
        int green = getIntent().getIntExtra(MainActivity.GREEN, 100);
        int blue = getIntent().getIntExtra(MainActivity.BLUE, 100);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        this.color1.setBackgroundColor(preferences.getInt(this.getString(R.string.preset1), HomeController.DEFAULT_1));
        this.color2.setBackgroundColor(preferences.getInt(this.getString(R.string.preset2), HomeController.DEFAULT_2));
        this.color3.setBackgroundColor(preferences.getInt(this.getString(R.string.preset3), HomeController.DEFAULT_3));

        controller.setColor(red, green, blue);

        if (savedInstanceState != null) {
            red = savedInstanceState.getInt(RED);
            green = savedInstanceState.getInt(GREEN);
            blue = savedInstanceState.getInt(BLUE);

            controller.setColor(red, green, blue);
        }

        boolean isOff = getIntent().getBooleanExtra(HomeController.STATE, false);
        controller.setState(!isOff);
        if(isOff){
            switchOff();
        }else{
            controller.setState(true);
        }

        controller.applyColor();


        Log.d("LIFELINE", "Main Activity Created - onCreate");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int red = controller.getRed();
        int green = controller.getGreen();
        int blue = controller.getBlue();
        outState.putInt(RED, red);
        outState.putInt(GREEN, green);
        outState.putInt(BLUE, blue);
        controller.setColor(red, green, blue);
    }

    public void setColor(int color){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean colorComponent = sharedPreferences.getBoolean(getString(R.string.COLOR_COMPONENT), false);
        if (colorComponent) {
            light.setText("ROUGE: " + Color.red(color) + " VERT : " + Color.green(color) + " BLEU : " +  Color.blue(color));
        }else{
            light.setText("");
        }
        light.setBackgroundColor(color);
    }

    public void setColor(int r, int g, int b){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean colorComponent = sharedPreferences.getBoolean(getString(R.string.COLOR_COMPONENT), false);
        if (colorComponent) {
            light.setText("ROUGE: " + r + " VERT : " + g + " BLEU : " +  b);
        }else{
            light.setText("");
        }
        light.setBackgroundColor(Color.rgb(r, g, b));
    }

    public void switchOff(){
        controller.setState(false);
        light.setBackgroundColor(ContextCompat.getColor(this, R.color.off));
        light.setText("Eteinte");
    }

    /* Log demandés dans le cours */

    protected void onStart(){
        super.onStart();
        controller.applyColor();
        Log.d("LIFELINE", "Main Activity Started - onStart");
    }

    protected void onResume(){
        super.onResume();
        controller.applyColor();
        Log.d("LIFELINE", "Main Activity - onResume");
    }

    protected void onPause(){
        super.onPause();
        Log.d("LIFELINE", "Main Activity - onPause");
    }

    protected void onStop(){
        super.onStop();
        Log.d("LIFELINE", "Main Activity Stopped - onStop");
    }

    protected void onRestart(){
        super.onRestart();
        controller.applyColor();
        Log.d("LIFELINE", "Main Activity Restarted - onRestart");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.d("LIFELINE", "Main Activity has been destroyed - onDestroy");
    }
}