package com.uiubd.brta_driving_exam.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.*;

public class LoadDatabase extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.uiubd.brta_driving_exam/databases/";
	private static String DB_NAME = "mydb";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	
	public LoadDatabase(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	public void createDatabase() {

		boolean dbExist = checkDatabase();

		if (!dbExist) {
			this.getReadableDatabase();
			copyDatabase();
		}
	}

	private void copyDatabase() {
		InputStream myInput = null;
		try {
			myInput = myContext.getAssets().open(DB_NAME + ".sqlite");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String outFilename = DB_PATH + DB_NAME;
		OutputStream myOutput = null;
		try {
			myOutput = new FileOutputStream(outFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		byte[] buffer = new byte[1024];
		int length;

		try {
			while ((length = myInput.read(buffer)) > 0) {
				System.out.println(length);
				myOutput.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private boolean checkDatabase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	public SQLiteDatabase openDatabase() {

		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null,
				SQLiteDatabase.OPEN_READONLY);
		
		return myDataBase;

	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();

		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub 

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
