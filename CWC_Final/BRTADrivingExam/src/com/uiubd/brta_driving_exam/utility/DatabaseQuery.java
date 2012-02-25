package com.uiubd.brta_driving_exam.utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseQuery extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.uiubd.brta_driving_exam/databases/";
	private static String DB_NAME = "mydb";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
    private static final String TAG = DatabaseQuery.class.getSimpleName();
	String allAnswers [];
	
	public DatabaseQuery(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
    public static int getCategoryNumber () {
        int ret = 0;

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT category_name FROM tbCategory";
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        ret = cursor.getCount();
        cursor.close();
        database.close();

        return ret;
    }

    public static String [] getAllAnswers () {
        String ret [];

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT answer FROM tbQuestionnaire";
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        
        ret = new String[cursor.getCount()];
        int index = 0;

        while (!cursor.isAfterLast()) {
            ret [index++] = cursor.getString(cursor.getColumnIndex("answer"));
            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        
        return ret;
    }

    public static int countQuestionPerCategory (int categoryId) {
        int ret = 0;

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT category_id FROM tbQuestionnaire WHERE category_id = " + categoryId;
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        ret = cursor.getCount();
        cursor.close();
        database.close();
        return ret;
    }


    public void startSetup () {
		getDatabase ();
		populateAllAnswers ();
		myDataBase.close();
	}

	private void getDatabase() {
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	private void populateAllAnswers() {

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM mytable WHERE _id=5", null);
        cursor.moveToFirst();
        Log.d("Db Result: >", cursor.getString(cursor.getColumnIndex("category_name")));
        
		
	}


    public static Question getQuestionObjectById(int id) {
        Question question = null;

        int ret = 0;

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM tbQuestionnaire WHERE category_id = " + id;
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        ret = cursor.getCount();
        cursor.close();
        database.close();
        
        return question;
    }

    public static String[] getCategoryNames() {
        String [] ret;

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT category_name FROM tbCategory";
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();


        Log.d(TAG, "cursor.getcount (): " + cursor.getCount());
        ret = new String[cursor.getCount()];
        int index = 0;

        while (!cursor.isAfterLast()) {
            Log.d(TAG, "name : " + cursor.getString(cursor.getColumnIndex("category_name")));
            ret [index++] = cursor.getString(cursor.getColumnIndex("category_name"));

            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        return ret;
    }

    public static Question [] getQuestionSet(int categoryId) {

        Question question [] = new Question[Config.NUM_OF_QUES_PER_CATEGORY [categoryId - 1]];

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM tbQuestionnaire WHERE category_id = " + categoryId + " ORDER BY RANDOM () LIMIT " + Config.NUM_OF_QUES_PER_CATEGORY [categoryId - 1];
        Log.d(TAG, "Sql : " + sql);
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        int index = 0;

        while (!cursor.isAfterLast()) {
            String title = cursor.getString(cursor.getColumnIndex("question_title"));
            String image = cursor.getString(cursor.getColumnIndex("picture_name"));
            String answer = cursor.getString(cursor.getColumnIndex("answer"));
            
            Question q = new Question(title, image, answer);
            question [index] = q;
            //SetupApplicationActivity.questionSet [categoryId - 1] [index] = q;
            index++;
            //question [index++] = q;

            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        return question;
    }

    public static void populateTbMarks(String regNumber, int[] marks) {

        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        for ( int i = 0; i < marks.length; i++ ) {
            String sql = "INSERT INTO tbMarks VALUES ('" + regNumber + "', " + (i + 1) + ", " + marks [i] + ");";
            Log.d(TAG, "Sql : " + sql);
            database.execSQL(sql);
        }
        
        database.close();

    }
    
    public static void blockUser (String regNumber) {
        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        String sql = "UPDATE tbUSER SET is_active = 0 WHERE reg_number = '" + regNumber + "';";
        Log.d(TAG, "Sql : " + sql);
        database.execSQL(sql);

        database.close();
    }

    public static void populateTbReport(String regNumber, int totalMarks, int totalAnswered, int totalCorrectAnswered, long timeTaken) {
        SQLiteDatabase database;

        String mypath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        String sql = "INSERT INTO tbReport VALUES ('" + regNumber + "', " + totalMarks + ", " + totalAnswered + ", " + totalCorrectAnswered + ", " + timeTaken + ");";
        Log.d(TAG, "Sql : " + sql);
        database.execSQL(sql);

        database.close();


    }
}
