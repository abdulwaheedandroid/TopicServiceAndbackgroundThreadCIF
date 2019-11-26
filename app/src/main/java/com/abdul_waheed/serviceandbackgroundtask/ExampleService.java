package com.abdul_waheed.serviceandbackgroundtask;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.abdul_waheed.serviceandbackgroundtask.App.CHANNEL_ID;

public class ExampleService extends Service {

    /*
    * Below method is used to bound our service. We bound our service to communicate with our component. By returning means our
    * service is started service.
    * */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
    * Below method will be trigger when we start our service. And can be called many times when we call 'startServiceMethod()'
    * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        //below stuff is mandatory for notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        /*
        * if we don't call startForeground() method this will not show any notification on tray but will be displayed and can be seen
        * in developer option but automatically be killed after a minute because it will be treated as normal background service
        * NOTE: Normal service does not start on background thread by default. Whatever work is done in onStartCommand method will be
        * done on main thread by default "Do heavy work on background thread".
        * */
        startForeground(1, notification);
        return START_NOT_STICKY;

        /*
        * START_NOT_STICKY ==> start service but not restart if gets killed
        * START_STICKY ==> start service if gets killed as soon as possible but the intent that gets will be null
        * START_REDELIVER_INTENT == > Start service again if gets killed and redelibered the last intent
        *
        * Forground services are very unlikely to be killed untill we pass START_NOT_STICKY
        * */

    }

    /*
    * Below method will be called once we create our service
    * */

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
