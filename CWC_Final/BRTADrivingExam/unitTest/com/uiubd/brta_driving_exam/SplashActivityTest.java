package com.uiubd.brta_driving_exam;

import android.R.string;

import com.uiubd.brta_driving_exam.BRTADrivingExamLoginActivity;
import com.uiubd.brta_driving_exam.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricTestRunner.class)

public class SplashActivityTest {
	
	@Test
	public void AppnameTest () throws Exception {
		String appName = new BRTADrivingExamLoginActivity().getResources().getString(R.string.app_name);
		assertThat(appName, equalTo("BRTA Driving Exam"));
	}

}
