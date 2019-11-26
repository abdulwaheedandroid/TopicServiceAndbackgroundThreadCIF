package com.abdul_waheed.serviceandbackgroundtask;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;


/*
* Minimum API 21 is required
* */
public class ExampleJobService extends JobService {

    private static final String TAG = ExampleJobService.class
            .getSimpleName();

    private boolean jobCancelled = false;


    /*
    * If there is a long running task we will return true else false. If we are returning true then it keeps the wake lock
    * on. It keeps device to continue the task. So our system can finish its work. That means we are reponsible to tell the
    * system we are finished with job manually so it can release wake lock
    * */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i <10; i++ ){
                    Log.d(TAG, "run " + i);
                    if (jobCancelled)
                        return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job Finished");
                //false ==> we don't wanna reschedule
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        //this boolean indicated as to reschedule job or not
        return false;
    }
}
