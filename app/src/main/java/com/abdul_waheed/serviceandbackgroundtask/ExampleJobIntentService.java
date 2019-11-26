package com.abdul_waheed.serviceandbackgroundtask;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;

public class ExampleJobIntentService extends JobIntentService {

    private static final String TAG = ExampleIntentService.class.getSimpleName();
    /*
    * Below method is like onHandleIntent of IntentService Class. This method runs on background thread
    * automatically. We do not need to write wake lock code. It handled it automatically
    * */

    static void enqueuWork(Context context, Intent work) {
        enqueueWork(context,ExampleIntentService.class, 123, work);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork");

        String input = intent.getStringExtra("input_extra");

        for (int i = 0; i < 10; i++) {
            Log.d(TAG, input + " - " + i);
            if (isStopped())
                return;
            SystemClock.sleep(1000);
        }
    }

    /*
    * this will called when the has been stopped this will be called when it is using JobSchedular.
    * This method is called when device requires memory or simple when it has been running since too long as Job has time limits
    * which is around 10 minutes and after that they stop and defered
    * */

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        /*
        * default value is true==> that means when this methid is called should be resumed and yes it should in case of false not
        * to resume. If it started again it will start with same intent as it was passed in first attempt
        * */
        return super.onStopCurrentWork();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
