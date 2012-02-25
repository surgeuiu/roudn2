package com.uiubd.brta_driving_exam;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.uiubd.brta_driving_exam.utility.Config;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/17/12
 * Time: 4:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayQuestionActivity extends SetupApplicationActivity {

    private static final String TAG = DisplayQuestionActivity.class.getSimpleName();
    int categoryIndex;
    TextView textViewQuestionTitle;
    ImageView imageViewQuestion;
    Button buttonNextQuestion;
    Button buttonPrevQuestion;
    RadioGroup radioGroup;
    int quesIndex;
    RadioButton radioButton0, radioButton1, radioButton2, radioButton3;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_question);
        
        textViewQuestionTitle = (TextView) findViewById(R.id.tvQuestion);
        imageViewQuestion = (ImageView) findViewById(R.id.imageViewQuestion);
        buttonNextQuestion = (Button) findViewById(R.id.btnNextQues);
        buttonPrevQuestion = (Button) findViewById(R.id.btnPrevQues);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        quesIndex = 0;

        categoryIndex = getIntent().getExtras().getInt("categoryIndex");
        Log.d(TAG, "category Index: " + categoryIndex);

        textViewQuestionTitle.setText(SetupApplicationActivity.questionSet [categoryIndex] [0].getQuestionTitle());

        imageViewQuestion.setImageBitmap(getBitmapFromAsset("images/" +
            SetupApplicationActivity.questionSet[categoryIndex][0].getQuestionImage() + ".png"));

        radioButton0 = ((RadioButton) findViewById(R.id.radio0));
        radioButton0.setText((SetupApplicationActivity.questionSet[categoryIndex][0].getAnswerOptions())[0]);

        radioButton1 = ((RadioButton) findViewById(R.id.radio1));
        radioButton1.setText((SetupApplicationActivity.questionSet[categoryIndex][0].getAnswerOptions())[1]);

        radioButton2 = ((RadioButton) findViewById(R.id.radio2));
        radioButton2.setText((SetupApplicationActivity.questionSet[categoryIndex][0].getAnswerOptions())[2]);

        radioButton3 = ((RadioButton) findViewById(R.id.radio3));
        radioButton3.setText((SetupApplicationActivity.questionSet[categoryIndex][0].getAnswerOptions())[3]);

        switch (questionSet [categoryIndex] [quesIndex].selectedAnswerId) {
            case 0: radioButton0.setChecked(true); break;
            case 1: radioButton1.setChecked(true); break;
            case 2: radioButton2.setChecked(true); break;
            case 3: radioButton3.setChecked(true); break;
        }

        setActionListeners ();
    }

    private void setActionListeners() {
        
        buttonNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quesIndex++;
                
                if ( quesIndex > Config.NUM_OF_QUES_PER_CATEGORY [categoryIndex] - 1 ) {
                    DisplayQuestionActivity.this.finish();
                    return;
                }
                
                textViewQuestionTitle.setText(SetupApplicationActivity.questionSet [categoryIndex] [quesIndex].getQuestionTitle());

                imageViewQuestion.setImageBitmap(getBitmapFromAsset("images/" +
                    SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getQuestionImage() + ".png"));

                radioButton0.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[0]);

                radioButton1.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[1]);

                radioButton2.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[2]);

                radioButton3.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[3]);

                radioGroup.clearCheck();

                switch (questionSet [categoryIndex] [quesIndex].selectedAnswerId) {
                    case 0: radioButton0.setChecked(true); break;
                    case 1: radioButton1.setChecked(true); break;
                    case 2: radioButton2.setChecked(true); break;
                    case 3: radioButton3.setChecked(true); break;
                }

            }
        });
        
        buttonPrevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quesIndex--;
                
                if ( quesIndex < 0 ) {
                    DisplayQuestionActivity.this.finish();
                    return;
                }
                
                textViewQuestionTitle.setText(SetupApplicationActivity.questionSet [categoryIndex] [quesIndex].getQuestionTitle());
                imageViewQuestion.setImageBitmap(getBitmapFromAsset("images/" + SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getQuestionImage() + ".png"));

                radioButton0.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[0]);

                radioButton1.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[1]);

                radioButton2.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[2]);

                radioButton3.setText((SetupApplicationActivity.questionSet[categoryIndex][quesIndex].getAnswerOptions())[3]);
                
                Log.d(TAG, "row: " + categoryIndex + "col: " + quesIndex + " index: " + questionSet [categoryIndex] [quesIndex].selectedAnswerId);

                radioGroup.clearCheck();

                switch (questionSet [categoryIndex] [quesIndex].selectedAnswerId) {
                    case 0: radioButton0.setChecked(true); break;
                    case 1: radioButton1.setChecked(true); break;
                    case 2: radioButton2.setChecked(true); break;
                    case 3: radioButton3.setChecked(true); break;

                }

            }
        });


        
        radioButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioOptionChanged (0);
            }
        });
        
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioOptionChanged (1);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioOptionChanged(2);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioOptionChanged(3);
            }
        });
    }

    private void radioOptionChanged(int checkedId) {
        questionSet [categoryIndex] [quesIndex].setAnswered(true);
        questionSet [categoryIndex] [quesIndex].selectedAnswerId = checkedId;

        if (checkedId == SetupApplicationActivity.questionSet [categoryIndex] [quesIndex].getCorrectAnswerId()) {
            SetupApplicationActivity.questionSet [categoryIndex] [quesIndex].setCorrectAnswered(true);
        } else {
            SetupApplicationActivity.questionSet [categoryIndex] [quesIndex].setCorrectAnswered(false);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.

    }

    private Bitmap getBitmapFromAsset(String strName) {
        AssetManager assetManager = getAssets();

        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }
}