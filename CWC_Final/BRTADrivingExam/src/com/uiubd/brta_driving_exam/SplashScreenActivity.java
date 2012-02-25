package com.uiubd.brta_driving_exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.uiubd.brta_driving_exam.utility.LoadDatabase;

public class SplashScreenActivity extends Activity {

    protected int _splashTime = 5000;
    private Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        final SplashScreenActivity sPlashScreen = this;

        // thread for displaying the SplashScreen
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized(this){
                        wait(_splashTime);
                        LoadDatabase loadDatabase = new LoadDatabase(SplashScreenActivity.this);
                        loadDatabase.createDatabase();
                    }

                } catch(InterruptedException e) {}
                finally {
                    finish();

                    Intent i = new Intent();
                    i.setClass(sPlashScreen, BRTADrivingExamLoginActivity.class);
                    startActivity(i);

                    stop();
                }
            }
        };

        splashTread.start();
    }
    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized(splashTread){
                splashTread.notifyAll();
            }
        }
        return true;
    }
    */
}
