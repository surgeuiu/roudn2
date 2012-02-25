package com.uiubd.brta_driving_exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.uiubd.brta_driving_exam.utility.Config;
import com.uiubd.brta_driving_exam.utility.DatabaseQuery;
import com.uiubd.brta_driving_exam.utility.Question;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/15/12
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class SetupApplicationActivity extends Activity {

    protected ProgressDialog progressDialog;
    private static final String TAG = SetupApplicationActivity.class.getSimpleName();
    public static int numberOfCategory;
    int questionsPerCategory [];
    public static Question questionSet [] [];
    String allAnswers [];
    public static boolean isCategoryCompleted [];
    public static int answeredCnt [];


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        Button buttonStartText = (Button) findViewById(R.id.btnStartTest);
        buttonStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetupApplicationActivity.this, CategoryNavActivity.class));
                finish();
            }
        });



        if ( questionSet == null ) {
            initializeMembers ();
            populateQuestionSet ();

            AlertDialog.Builder builder = new AlertDialog.Builder(SetupApplicationActivity.this);
            builder.setMessage("Negative markings included.Please read the Instruction.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // put your code here
                        }
                    }).show();
        }

        
    }

    private void populateQuestionSet() {
        for ( int i = 0; i < numberOfCategory; i++ ) {


            // DatabaseQuery.getQuestionSet (i + 1);
            questionSet [i] = DatabaseQuery.getQuestionSet (i + 1);
            
            for ( int j = 0; j < Config.NUM_OF_QUES_PER_CATEGORY [i]; j++ ) {
                generateAnswers(questionSet[i][j]);
            }
        }
          
        
    }

    private void generateAnswers(Question question) {

        int randNums [] = randomNumbers(allAnswers.length, 4);
        
        Log.d(TAG, "random numbers for answers: " + randNums [0] + " " + randNums [1] + " " + randNums [2] + " " + randNums [3]);

        String [] strings = new String[4];

        strings [0] = question.getAnswer();
        int index = 1;
        
        for ( int i = 0; i < 4; i++ ) {
            if ( allAnswers [randNums [i]] != strings [0] ) {
                strings [index++] = allAnswers [randNums [i]];        
            }
            if ( index == 4 ) break;
        }

        strings = permuteArray(strings);
        question.setAnswerOptions(strings);
        
        for ( int i = 0; i < 4; i++ ) {
            if ( strings [i] == question.getAnswer() ) { question.setCorrectAnswerId(i); break; } 
        }
    }

    private String [] permuteArray(String[] strings) {
        
        int perm [] = {1234, 1243, 1324, 1342, 1423, 1432, 2134, 2143, 2314, 2341, 2413, 2431, 3124, 3142, 3214, 3241,
        3412, 3421, 4123, 4132, 4213, 4231, 4312, 4321};
        
        int randNums [] = randomNumbers(24, 1);
        int permNum = perm [randNums [0]];
        
        String newString [] = new String[4];
        
        newString [0] = strings [(permNum / 1000) - 1];
        permNum %= 1000;
        newString [1] = strings [(permNum / 100) - 1];
        permNum %= 100;
        newString [2] = strings [(permNum / 10) - 1];
        permNum %= 10;
        newString [3] = strings [permNum - 1];

        return newString;
    }

    private void initializeMembers() {
        numberOfCategory = DatabaseQuery.getCategoryNumber();
        questionSet = new Question[numberOfCategory][];
        allAnswers = DatabaseQuery.getAllAnswers();
        questionsPerCategory = new int[numberOfCategory];
        SetupApplicationActivity.isCategoryCompleted = new boolean[numberOfCategory];
        answeredCnt = new int[numberOfCategory];
        
        for ( int i = 0; i < numberOfCategory; i++ ) {
            questionsPerCategory [i] = DatabaseQuery.countQuestionPerCategory(i + 1);
            isCategoryCompleted [i] = false;
        }
        
    }

    protected int [] randomNumbers (int limit, int size) {
        int ret [] = new int[size];
        boolean isVisited [] = new boolean[limit];

        for (int i = 0; i < size; i++) {
            int randNum = (int) (Math.random() * limit);

            if (isVisited[randNum]) {
                i--;	// loop once again 
                continue;
            }

            isVisited[randNum] = true;
            ret [i] = randNum; // generate random options
        }


        return ret;
    }

    protected Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            Log.e(SplashScreenActivity.class.getSimpleName(), "uncaught exception: ", throwable);

            AlertDialog.Builder builder=new AlertDialog.Builder(SetupApplicationActivity.this);
            builder
                .setTitle("Exception")
                .setMessage(throwable.toString())
                .setPositiveButton("Okay", null)
                .show();
        }
    };
}