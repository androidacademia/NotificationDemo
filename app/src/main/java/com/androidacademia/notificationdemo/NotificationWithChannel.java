package com.androidacademia.notificationdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationWithChannel {


    private static final String CHANNEL_ID = "my_channel_id";
    private static final CharSequence CHANNEL_NAME = "Channel_name";


    public boolean getOSVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return true;
        }else {
            return false;
        }
    }

    public NotificationChannel getNotificationChannel(){
        if (getOSVersion()){
            //return notification channel
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(R.color.colorAccent);
            return notificationChannel;
        }else {
            return null;
        }
    }

    public void sendNotification(Context context, String title, String message, Intent intent){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1234,intent,0);
        NotificationCompat.Builder builder = null;
        if (getOSVersion()){
            builder = new NotificationCompat.Builder(context,CHANNEL_ID);
            notificationManager.createNotificationChannel(getNotificationChannel());
        }else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        notificationManager.notify(1234,builder.build());
    }
}
