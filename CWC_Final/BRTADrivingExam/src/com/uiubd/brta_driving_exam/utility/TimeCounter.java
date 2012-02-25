package com.uiubd.brta_driving_exam.utility;

import android.os.CountDownTimer;
import com.uiubd.brta_driving_exam.CategoryNavActivity;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/18/12
 * Time: 1:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeCounter extends CountDownTimer{

    boolean blink = false;
    CategoryNavActivity categoryNavActivity;

    public TimeCounter(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

        categoryNavActivity = new CategoryNavActivity();
    }

    @Override
    public void onTick(long l) {
        long seconds = l / 1000;

    }

    @Override
    public void onFinish() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
