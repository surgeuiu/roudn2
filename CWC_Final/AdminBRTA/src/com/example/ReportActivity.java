package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: KITTY
 * Date: 2/25/12
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportActivity extends Activity {

    private static String DB_PATH = "/data/data/com.uiubd.brta_driving_exam/databases/";
    private static String DB_NAME = "mydb";
    private static final String TAG = ReportActivity.class.getSimpleName();
    TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        textView = (TextView) findViewById(R.id.tvReport);
        getDataFromDatabase ();
    }

    private void getDataFromDatabase() {
        Context sharedContext = null;

        try {
            sharedContext = this.createPackageContext("com.uiubd.brta_driving_exam", Context.CONTEXT_INCLUDE_CODE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM tbReport";
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        String str = "";

        while (!cursor.isAfterLast()) {
            str += "Reg Number: ";
            str += cursor.getString(cursor.getColumnIndex("reg_number"));
            str += "\nCorrect Ans: ";
            str += cursor.getString(cursor.getColumnIndex("correct"));
            str += "\nTime: ";
            str += cursor.getString(cursor.getColumnIndex("time"));
            str += " ms\n\n";

            cursor.moveToNext();
        }

        textView.setText(str);

        cursor.close();
        database.close();
    }
}