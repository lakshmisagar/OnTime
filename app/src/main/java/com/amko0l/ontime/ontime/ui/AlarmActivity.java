package com.amko0l.ontime.ontime.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amko0l.ontime.ontime.MainActivity;
import com.amko0l.ontime.ontime.R;
import com.amko0l.ontime.ontime.receivers.Alarm_Receiver;

/**
 * Created by amko0l on 4/14/2017.
 */

public class AlarmActivity extends AppCompatActivity{
    //to make our alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_alarm;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);.
        this.context = this;

        //initialise alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialise our timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //initialise text update box
        update_alarm = (TextView) findViewById(R.id.update_alarm);

        //create an instance of calendar
        final Calendar calendar = Calendar.getInstance();

        //create an intent to the alarm receiver class
        final Intent alarm_intent = new Intent (this.context, Alarm_Receiver.class);

        //Initialise the start alarm button
        Button start_alarm = (Button) findViewById(R.id.start_alarm);

        //Create an onClick listener to start the alarm
        start_alarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //setting calendar instance with the hour and minute that we have picked
                //on the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                //get the string values of the hour and minute
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                //convert the int values to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                //convert 24 hour time to 12 hour time
                if (hour > 12){
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    //10:7 -> 10:07
                    minute_string = "0" + String.valueOf(minute);
                }


                //method to update the alarm text box
                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);

                //put in extra string into alarm_intent
                //tells the clock that you put the alarm on button
                alarm_intent.putExtra("extra","Alarm on");


                //create a pendig intent that delays the intent
                //until the specified calendar time
                pending_intent =  PendingIntent.getBroadcast(AlarmActivity.this, 0, alarm_intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);


            }
        });

        //initialise the end alarm button
        Button end_alarm = (Button) findViewById(R.id.end_alarm);

        //Create an onClick listener to turn off the alarm

        end_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method to update the alarm text
                set_alarm_text("Alarm off!");

                //cancel the alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into alarm_intent
                //tells the clock that you pressed "end_alarm" button
                alarm_intent.putExtra("extra", "Alarm off");

                //stop alarm
                sendBroadcast(alarm_intent);
            }

        });

    }

    private void set_alarm_text(String output) {

        update_alarm.setText(output);
    }
}
