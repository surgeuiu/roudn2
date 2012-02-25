package com.uiubd.brta_driving_exam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BRTADrivingExamLoginActivity extends Activity {

    Button buttonLogin;
    static EditText editTextRegistrationNumber;
    EditText editTextPinNumber;
    int userLoginStatus = -1;
    ProgressDialog progressDialog;
    private static final String TAG = BRTADrivingExamLoginActivity.class.getSimpleName();

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userLoginStatus = -1;
        progressDialog = new ProgressDialog(this); 
        setLayoutViews();
        setActionListeners();

            /*
        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM mytable WHERE _id=5", null);
        cursor.moveToFirst();
        Log.d("Db Result: >", cursor.getString(cursor.getColumnIndex("category_name")));
        db.close();   */


    }

    private void setActionListeners() {

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(BRTADrivingExamLoginActivity.this,
                    getResources().getText(R.string.please_wait), getResources().getText(R.string.task_progress), true);

                //Thread.setDefaultUncaughtExceptionHandler(handler);

                new Thread( new Runnable() {
                    @Override
                    public void run() {    

                        // check authentication
                        userLoginStatus = userAuthentication(editTextRegistrationNumber.getText().toString(),
                            editTextPinNumber.getText().toString());

                        runOnUiThread(stopProgressDialog);   
                    }

                }).start();
            }
        });
    }

    private void setLayoutViews() {
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        editTextRegistrationNumber = (EditText) findViewById(R.id.etRegistrationNumber);
        editTextPinNumber = (EditText) findViewById(R.id.etPinNumber);
    }

    int userAuthentication (String regNum, String pinNum) {
        int ret = -1;
        SQLiteDatabase database;

        database = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        String sql = "SELECT * FROM tbUser WHERE reg_number = \"" + regNum + "\"";
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        if ( cursor.getCount() == 0 ) {		// invalid registration number
            ret = -1;
        } else if ( cursor.getString(cursor.getColumnIndex("pin_number")).equals(pinNum) != true ){	// pin number invalid
            ret = -1;
        } else {
            if ( cursor.getInt(cursor.getColumnIndex("is_active")) == 1 ) {		// active user
                ret = 1;
            } else {
                ret = 0;
            }
        }

        Log.d(TAG, "user login Status: " + ret);
        cursor.close();
        database.close();

        return ret;
    }

    protected Runnable stopProgressDialog = new Runnable() {

        @Override
        public void run() {
            progressDialog.dismiss();
            
            switch (userLoginStatus) {
                case 0:
                    Toast.makeText(BRTADrivingExamLoginActivity.this, "Session Expired. Please contact with Admin", Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    Toast.makeText(BRTADrivingExamLoginActivity.this, "Invalid User. Please contact with Admin", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    startActivity(new Intent(BRTADrivingExamLoginActivity.this, SetupApplicationActivity.class));
                    finish();
                    break;
                default:
                    Toast.makeText(BRTADrivingExamLoginActivity.this, "Application Error. Please contact with Admin", Toast.LENGTH_LONG).show();
            }

        }
    };

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

    
}