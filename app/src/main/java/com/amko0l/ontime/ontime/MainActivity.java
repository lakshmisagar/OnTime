package com.amko0l.ontime.ontime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amko0l.ontime.ontime.ui.AlarmActivity;
import com.amko0l.ontime.ontime.ui.LaunchActivity;
import com.amko0l.ontime.ontime.ui.MapActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnalarmactivity = (Button) findViewById(R.id.button2);
        btnalarmactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        Button btnLaunchactivity = (Button) findViewById(R.id.button1);
        btnLaunchactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaunchActivity.class);
                startActivity(intent);
            }
        });

        Button btnmap = (Button) findViewById(R.id.btnmap);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        Button btnmapcoffee = (Button) findViewById(R.id.btnmapcoffee);
        btnmapcoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });


    }
}
