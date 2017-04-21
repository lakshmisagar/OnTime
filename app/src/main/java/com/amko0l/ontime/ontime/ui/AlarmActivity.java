package com.amko0l.ontime.ontime.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amko0l.ontime.ontime.R;
import com.amko0l.ontime.ontime.database.DataValues;
import com.amko0l.ontime.ontime.database.OnTimeDB;
import com.amko0l.ontime.ontime.receivers.Alarm_Receiver;

import java.util.Calendar;

/**
 * Created by Lakshmisagar on 4/14/2017.
 */

public class AlarmActivity extends AppCompatActivity {
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_alarm;
    Calendar calendar;
    Context context;
    PendingIntent pending_intent;
    static SQLiteDatabase sqLiteDatabase;
    OnTimeDB onTimeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        onTimeDB = new OnTimeDB(this, "OnTimeDB", null, 1);
        sqLiteDatabase = onTimeDB.getWritableDatabase();
    }

    public void onSave(View v) {
        EditText classname_text = (EditText) findViewById(R.id.classname);
        String classname = classname_text.getText().toString();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
        calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());
        int hour = alarm_timepicker.getHour();
        int minute = alarm_timepicker.getMinute();
        String hour_string = String.valueOf(hour);
        String minute_string = String.valueOf(minute);
        //convert 24 hour time to 12 hour time
        if (hour > 12) {
            hour_string = String.valueOf(hour - 12);
        }
        if (minute < 10) {
            minute_string = "0" + String.valueOf(minute);
        }

        Intent alarm_intent = new Intent(context, Alarm_Receiver.class);
        alarm_intent.putExtra("extra", "Alarm on");
        pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
        Switch switchButton = (Switch) findViewById(R.id.switchbutton);

        DataValues eventData = new DataValues(classname, hour_string, minute_string, switchButton.isChecked());
        Log.d("SAGAR", "ClassName :" + classname + " Alarm Time: " + hour_string + ":" + minute_string + "   Preference:" + switchButton.isChecked());
        OnTimeDB.insertIntoDB(sqLiteDatabase);
        super.onBackPressed();
    }
}
