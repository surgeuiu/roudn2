package com.uiubd.brta_driving_exam;

import android.R.string;
import android.widget.Button;
import android.widget.EditText;

import com.uiubd.brta_driving_exam.BRTADrivingExamLoginActivity;
import com.uiubd.brta_driving_exam.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricTestRunner.class)
public class BRTADrivingExamLoginActivityTest {
	private BRTADrivingExamLoginActivity activity;
	private Button buttonLogin;
	private EditText editTextRegNumber;
	private EditText editTextPinNumber;
	
	public void setUp () throws Exception {
		activity = new BRTADrivingExamLoginActivity();
		activity.onCreate(null);
		buttonLogin = (Button) activity.findViewById(R.id.btnLogin);
		editTextRegNumber = (EditText) activity.findViewById(R.id.etRegistrationNumber);
		editTextPinNumber = (EditText) activity.findViewById(R.id.etPinNumber);
	}
	
	/*
	@Test
	public void loginButtonActive () throws Exception {
		//assertThat((String)buttonLogin.getText(), equalTo("Login"));
		buttonLogin.performClick();
	}
	*/

	@Test
	public void AppnameTest () throws Exception {
		String appName = new BRTADrivingExamLoginActivity().getResources().getString(R.string.app_name);
		assertThat(appName, equalTo("BRTA Driving Exam"));
	}
}
