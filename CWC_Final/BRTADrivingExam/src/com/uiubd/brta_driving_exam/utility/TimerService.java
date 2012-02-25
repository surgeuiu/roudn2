package com.uiubd.brta_driving_exam.utility;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;
import com.uiubd.brta_driving_exam.CategoryNavActivity;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/20/12
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimerService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Timer started...", Toast.LENGTH_LONG).show();
        final CategoryNavActivity categoryNavActivity = new CategoryNavActivity();
        
        new CountDownTimer(30000, 500) {


            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;

                //categoryNavActivity.settimer(seconds);
                // CategoryNavActivity.settimer(seconds);
            }

            public void onFinish() {
                stopService(new Intent(TimerService.this, TimerService.class));
               // textViewTimeCount.setText("done!");
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
