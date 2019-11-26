package com.abdul_waheed.serviceandbackgroundtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.et_input);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancel(123);
                Log.d(TAG, "Job Cancelled");
            }
        });

        Button btnSchedule = findViewById(R.id.btn_schedule);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName componentName = new ComponentName(MainActivity.this, ExampleJobService.class);
                JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //this is for wifi connnection
                        .setPersisted(true) // if the device restart it keeps running
                        .setPeriodic(15* 60 * 1000) // if this is less than 15 minutes it will override time to at least 15 mnts
                        .build();

                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = scheduler.schedule(jobInfo);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "Job Schedelued");
                } else {
                    Log.d(TAG, "Job not Schedelued");
                }

            }
        });

        final Button startService = findViewById(R.id.btn_start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etInput.getText().toString();
                //Intent service work
                Intent serviceIntent = new Intent(MainActivity.this, ExampleIntentService.class);
                serviceIntent.putExtra("inputExtra", input);
                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
                //startService(serviceIntent);

                //Service work
                /*Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                serviceIntent.putExtra("inputExtra", input);
                //startService(serviceIntent);

                *//*As below method is not available before api level 26 the convinenint way to call is below
                *startForegroundService(serviceIntent);
                * *//*
                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);

                *//*
                * startService method works here because while our app still open. If you want to start your service while your app
                * is in background 'startForeGround()' method should be called instead. This tells to the system that you want to
                * promote forground service as quickly as possible. And after calling this method you have a time window of 5 seconds
                * to call startForeground method of service class. If you don't do this in 5 seconds, Your service will be killed automatically.
                * NOTE: If you call service from background it will throuhg illegal exception
                * */
            }
        });

        Button stopService = findViewById(R.id.btn_stop_service);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                stopService(serviceIntent);
            }
        });


    }
}
