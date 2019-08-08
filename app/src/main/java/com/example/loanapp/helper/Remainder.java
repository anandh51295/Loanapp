package com.example.loanapp.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.loanapp.MainActivity;
import com.example.loanapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.loanapp.App.CHANNEL_ID;

public class Remainder extends Service {
    DatabaseHelper db;
    private static final String TAG = "Loan Test";
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
    int check = 0;
    ArrayList<String> times  = new ArrayList<String>();
    ArrayList<String> amounts = new ArrayList<String>();
    String date;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("service", "Started");
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Loan");
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Loan");
        bigTextStyle.bigText("Loan Remainder Running");
        builder.setStyle(bigTextStyle);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setFullScreenIntent(pendingIntent, true);
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    private void stopForegroundService() {
        Log.d("service", "Stop foreground service.");
        stopForeground(true);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();

            switch (action) {
                case ACTION_START_FOREGROUND_SERVICE:
                    startForegroundService();
                    Toast.makeText(getBaseContext(), "Loan service is started.", Toast.LENGTH_LONG).show();

                    ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
                    scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                        public void run() {
                                checkdue();
                                Log.d("test","yes");
                        }
                    }, 0, 1, TimeUnit.SECONDS);
                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    Toast.makeText(getBaseContext(), "Loan service is stopped.", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.d("service", "default works");
            }
        } else {
            Log.d("restart", "no intent");
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.d("Service", "Stopped");
        super.onDestroy();
    }

    public void checkdue() {
        db = new DatabaseHelper(getApplicationContext());
        try {
//            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            if(check==0){
                Cursor cursor = db.getinstallment();
                if(cursor == null){
                    Log.d("service","null");
                }
//            String username="hello";
//            float amount=1000;
                while (cursor.moveToNext()) {
//                username = cursor.getColumnName(0);
//                amount = cursor.getFloat(0);
//                    sendnotify(String.valueOf(cursor.getFloat(0)));
                    times.add(String.valueOf(cursor.getString(0)));
                    amounts.add(String.valueOf(cursor.getFloat(1)));
                }
                check=1;
            }
            String cdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if(date!=cdate){
                check=0;
                times.clear();
                amounts.clear();
                date=cdate;
            }

            if(!times.isEmpty()){
                int l=times.size();
                for(int i=0;i<l;i++){
                    if(times.get(i)==ts){
                        sendnotify(amounts.get(i));
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("service","error");
        }

    }

    public void sendnotify(String amount) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification n = new Notification.Builder(this)
                .setContentTitle("Remainder For Due")
                .setContentText("Pay: " + amount)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

}
