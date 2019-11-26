package com.abdul_waheed.serviceandbackgroundtask;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.abdul_waheed.serviceandbackgroundtask.App.CHANNEL_ID;

/*
* Intent service runs on background thread and make queues.
* */
public class ExampleIntentService extends IntentService {

    private static final String TAG = ExampleIntentService.class.getSimpleName();
    private PowerManager.WakeLock wakeLock;

    public ExampleIntentService() {
        //super constructor takes argument just for debugging purposes
        super(ExampleIntentService.class.getSimpleName());
        /*
        * false ==> if system fills the service it will not be created again
        * */
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        /*
        * If you think that your service could keep running when device is locked. Wake lock will keep CPU of the phone running when
        *  the screen turned off
        * */

        /*
        * PARTIAL_WAKE_LOCK means screen can still turn off but we just want the CPU keep running
        * NOTE: Second parametere is just for debugging purposes and it has naming convention that it takes : in name
        *
        * */
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "ExampleApp:WackeLock");

        /*
        * param is a time out in millis for wake lock in case some accidently keeps wak lock when he is done because wake lock
        * drains the battery of the phone
        * */

        wakeLock.acquire();
        Log.d(TAG, "wake lock acquired");
        /*
        * If you wanna run foreground service lower than OREO jus remove conditional check
        * Foreground is less likely to be killed in low memory situations like background service
        * */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Intent Service")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
        wakeLock.release();
        Log.d(TAG, "wake lock released");
    }

    /*
    * Below method will be executed by background thread.
    * */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "ON handle intent");
        String input = intent.getStringExtra("inputExtra");

        for (int i =0; i < 10; i ++) {
            Log.d(TAG, input + " - " + i);
            SystemClock.sleep(1000);
        }
    }
}
