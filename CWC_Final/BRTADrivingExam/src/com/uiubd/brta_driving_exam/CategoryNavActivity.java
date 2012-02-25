package com.uiubd.brta_driving_exam;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.uiubd.brta_driving_exam.utility.Config;
import com.uiubd.brta_driving_exam.utility.DatabaseQuery;

import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/17/12
 * Time: 3:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryNavActivity extends ListActivity {

    TaskListAdapter taskListAdapter;
    private static final String TAG = CategoryNavActivity.class.getSimpleName();
    TextView textViewTimeCount;
    TextView textViewTimeCountQuestion;
    boolean blink = false;
    Button buttonQuit;
    public static long TimePassed = Config.EXAM_TIME_IN_MILISECONDS;
    public static CountDownTimer countDownTimer = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_nav_list);
        
        textViewTimeCount = (TextView) findViewById(R.id.tvTimeCount);
        buttonQuit = (Button) findViewById(R.id.btnQuit);

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowReportActivity.class);
                startActivity(intent);
            }
        });

     
            countDownTimer = new  CountDownTimer(TimePassed, 500) {

                @Override
                public void onTick(long l) {
                    TimePassed = l;
                    long seconds = l / 1000;

                    if ( l < Config.EXAM_BLINK_TIME_IN_MILLISECONDS ) {
                        textViewTimeCount.setTextAppearance(getApplicationContext(), R.style.red_alert);

                        if ( blink ) {
                            textViewTimeCount.setVisibility(View.VISIBLE);
                        }
                        else {
                            textViewTimeCount.setVisibility(View.INVISIBLE);
                        }

                        blink = !blink;
                    }
                    textViewTimeCount.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));

                }

                @Override
                public void onFinish() {
                    textViewTimeCount.setText("00:00");
                    startActivity(new Intent(CategoryNavActivity.this, ShowReportActivity.class));
                }
            }.start();
        


        String categoryName [] = DatabaseQuery.getCategoryNames();

        setTaskListAdapter(categoryName);
    }

    private void setTaskListAdapter(String categoryName []) {
        
        
        int numOfCategory = categoryName.length;


        taskListAdapter = new TaskListAdapter(this, categoryName);
        setListAdapter(taskListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {


        Intent intent = new Intent(CategoryNavActivity.this, DisplayQuestionActivity.class);
        intent.putExtra("categoryIndex", position);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    
        Log.d(TAG,  "on destroy called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
        Log.d(TAG,  "on stop called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume call");
        boolean changed = false;
        for ( int i = 0; i < Config.NUM_OF_CATEGORY; i++ ) {
            if ( SetupApplicationActivity.isCategoryCompleted [i] ) continue;
            
            boolean flag = true;
            SetupApplicationActivity.answeredCnt [i] = 0;
            for ( int j = 0; j < Config.NUM_OF_QUES_PER_CATEGORY [i]; j++ ) {
                if ( !SetupApplicationActivity.questionSet [i] [j].isAnswered() ) flag = false;
                if ( SetupApplicationActivity.questionSet [i] [j].isAnswered() ) SetupApplicationActivity.answeredCnt [i]++;
            }
            
            if ( flag ) {
                changed = true;
                SetupApplicationActivity.isCategoryCompleted [i] = true;
            }
        }
        
        //if ( changed ) taskListAdapter.notifyDataSetChanged();
        taskListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //taskListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.category_nav_list);
    }
}