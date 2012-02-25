package com.uiubd.brta_driving_exam.test;

import junit.framework.TestCase;
import java.util.ArrayList; 
import android.test.ActivityInstrumentationTestCase2; 
import android.widget.EditText; 
import android.widget.TextView; 
import com.uiubd.*;
import com.uiubd.brta_driving_exam.BRTADrivingExamLoginActivity;
import com.jayway.android.robotium.solo.Solo;

public class TestBRTADrivingExamLogin extends ActivityInstrumentationTestCase2<BRTADrivingExamLoginActivity> {

	private Solo solo;
	
	public TestBRTADrivingExamLogin() {
		super("com.uiubd.brta_driving_exam.test", BRTADrivingExamLoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testDisplayBlackBox () {
		solo.enterText(0, "011081020");
		solo.enterText(1, "20");
		solo.clickOnButton("Login");
		assertTrue(solo.searchText("Exam Instruction"));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
