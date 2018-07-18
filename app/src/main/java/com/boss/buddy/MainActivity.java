package com.boss.buddy;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.boss.buddy.CalendarEvent.EventActivity;


public class MainActivity extends AppCompatActivity {
    boolean exit = false;

    private Button ScheduleButton;
    public int FLAG_1;
    public int FLAG_2;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout mainLayout = findViewById(R.id.mainlayout);

        Bundle B = getIntent().getExtras();
        assert B != null;
        FLAG_1 = B.getInt("FLAG_AVAILABLE");
        FLAG_2 = B.getInt("FLAG_FINISHED");


        if (FLAG_1 == 0 && FLAG_2 == 1) {
            SharedPreferences SPW = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = SPW.edit();
            editor.putInt(getString(R.string.StoredValue), FLAG_1);
            editor.apply();
        }
        SharedPreferences SPR = this.getPreferences(Context.MODE_PRIVATE);
        int DefaultValue = Integer.parseInt(getResources().getString(R.string.DefaultValue));
        int FinalVal = SPR.getInt(getString(R.string.StoredValue), DefaultValue);


        if (FLAG_1 == 1 && FinalVal == 0)
            {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(250, 100, 30, 0);
                ScheduleButton = new Button(this);
                ScheduleButton.setText("SCHEDULE EXAM");
                final SharedPreferences SPW = this.getPreferences(Context.MODE_PRIVATE);
                ScheduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ScheduleButton.setEnabled(false);
                        SharedPreferences.Editor editor = SPW.edit();
                        editor.putInt(getString(R.string.StoredValue), FLAG_1);
                        editor.apply();
                        Intent Show=new Intent(getApplicationContext(), EventActivity.class);
                        Bundle BShow=new Bundle();
                        BShow.putInt("flag",0);
                        Show.putExtras(BShow);
                        startActivity(Show);
                        ScheduleButton.setEnabled(true);
                    }
                });
                mainLayout.addView(ScheduleButton, layoutParams);
            }
            if (FLAG_1 == 1 && FinalVal == 1 )
             {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(250, 100, 30, 0);
                ScheduleButton = new Button(this);
                ScheduleButton.setText("SHOW EXAM SCHEDULE");
                ScheduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ScheduleButton.setEnabled(false);

                        Intent Show=new Intent(getApplicationContext(), EventActivity.class);
                        Bundle BShow=new Bundle();
                        BShow.putInt("flag",1);
                        Show.putExtras(BShow);
                        startActivity(Show);
                        ScheduleButton.setEnabled(true);
                    }
                });
                mainLayout.addView(ScheduleButton, layoutParams);
            }


            //----------------CLOUD MESSAGING----------//
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create channel to show notifications.
                String channelId = getString(R.string.default_notification_channel_id);
                String channelName = getString(R.string.default_notification_channel_name);
                NotificationManager notificationManager =
                        getSystemService(NotificationManager.class);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }
            //----------------CLOUD MESSAGING----------//
        }


    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Onooode Alojichitu Pore",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
