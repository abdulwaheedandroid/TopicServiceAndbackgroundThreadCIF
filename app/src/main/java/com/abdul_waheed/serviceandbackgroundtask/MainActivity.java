package com.abdul_waheed.serviceandbackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }
}
