package com.uiubd.brta_driving_exam;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.uiubd.brta_driving_exam.utility.Config;
import com.uiubd.brta_driving_exam.utility.DatabaseQuery;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/21/12
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShowReportActivity extends SetupApplicationActivity {
    
    private static final String TAG = ShowReportActivity.class.getSimpleName();
    public static boolean flag_once = false;
    private static String str;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        if ( !flag_once ) {
            CategoryNavActivity.countDownTimer.cancel();
            saveDataInDatabase ();
        }

        TextView textView = (TextView) findViewById(R.id.tvTemp);
        textView.setText(str);

        flag_once = true;


    }

    private void saveDataInDatabase() {

        long timeTaken = Config.EXAM_TIME_IN_MILISECONDS - CategoryNavActivity.TimePassed;
        String regNumber = BRTADrivingExamLoginActivity.editTextRegistrationNumber.getText().toString();

        DatabaseQuery.blockUser(regNumber);

        int categoryWiseMarks [] = new int[Config.NUM_OF_CATEGORY];
        int totalMarks = 0;
        for ( int i = 0; i < Config.NUM_OF_CATEGORY; i++ )
            totalMarks += (Config.NUM_OF_QUES_PER_CATEGORY [i] * 2);

        int totalAnswered = 0;
        int totalCorrectAnswered = 0;

        for ( int i = 0; i < Config.NUM_OF_CATEGORY; i++ ) {
            int cnt = 0;
            for ( int j = 0; j < Config.NUM_OF_QUES_PER_CATEGORY [i]; j++ ) {
                if ( questionSet [i] [j].isAnswered() ) {
                    totalAnswered++;
                    if ( questionSet [i] [j].isCorrectAnswered() ) {
                        cnt++;
                        totalCorrectAnswered++;
                    }
                }

            }
            categoryWiseMarks [i] = cnt;
        }

        DatabaseQuery.populateTbMarks(regNumber, categoryWiseMarks);
        DatabaseQuery.populateTbReport(regNumber, totalMarks, totalAnswered, totalCorrectAnswered, timeTaken);

        // initiate views 

        str = "";

        String categoryName [] = DatabaseQuery.getCategoryNames();

        for ( int i = 0; i < Config.NUM_OF_CATEGORY; i++ ) {

            str += (categoryName [i] + ": >> ");
            str += "Correct: " + categoryWiseMarks [i] + "\n\n";
        }

        str += "--------------------------\n";
        str += "Total Marks: " + totalMarks + "\n";
        str += "Total Answered: " + totalAnswered + "\n";
        str += "Total Correct Ans: " + totalCorrectAnswered + "\n";
        str += "Wrong Ans: " + (totalAnswered - totalCorrectAnswered) + "\n";
        str += "Marks Obtained: (" + totalCorrectAnswered + " * 2) - " + (totalAnswered - totalCorrectAnswered) + " = " + (totalCorrectAnswered * 2 - (totalAnswered - totalCorrectAnswered)) + "\n";
        str += "Time Taken: " + timeTaken + " ms\n";
    }
}