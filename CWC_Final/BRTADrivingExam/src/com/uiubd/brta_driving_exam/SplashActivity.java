package com.uiubd.brta_driving_exam;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import com.uiubd.brta_driving_exam.utility.LoadDatabase;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/18/12
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class SplashActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_splash);
        startAnimation ();
    }

    private void startAnimation() {

        Animation spinny = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        spinny.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, BRTADrivingExamLoginActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        LayoutAnimationController controller = new LayoutAnimationController(spinny);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeSplash);
        relativeLayout.setLayoutAnimation(controller);

        LoadDatabase loadDatabase = new LoadDatabase(SplashActivity.this);
        loadDatabase.createDatabase();
        
    }

    @Override
    protected void onPause() {
        super.onPause(); 
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeSplash);
        relativeLayout.clearAnimation();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setContentView(R.layout.new_splash);
    }
}
