package com.example.lampemagique.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lampemagique.R;
import com.example.lampemagique.controller.HomeController;
import com.example.lampemagique.controller.LightController;

public class Home extends AppBase{

    private HomeController controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.controller = new HomeController(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lampe Magique");

        Button first = findViewById(R.id.first);
        first.setOnClickListener(controller);
        Button second = findViewById(R.id.second);
        second.setOnClickListener(controller);
        Button third = findViewById(R.id.third);
        third.setOnClickListener(controller);
        Button off = findViewById(R.id.off);
        off.setOnClickListener(controller);

        Log.d("LIFELINE", "Home Created - onCreate");
    }

    /* Log demandés dans le cours */

    protected void onStart(){
        super.onStart();
        Log.d("LIFELINE", "Home Started - onStart");
    }

    protected void onResume(){
        super.onResume();
        Log.d("LIFELINE", "Home - onResume");
    }

    protected void onPause(){
        super.onPause();
        Log.d("LIFELINE", "Home - onPause");
    }

    protected void onStop(){
        super.onStop();
        Log.d("LIFELINE", "Home Stopped - onStop");
    }

    protected void onRestart(){
        super.onRestart();
        Log.d("LIFELINE", "Home Restarted - onRestart");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.d("LIFELINE", "Home has been destroyed - onDestroy");
    }
}
