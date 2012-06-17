package com.dipen.numtrix;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Dipen Pradhan
 * @email dipenpradhan13@gmail.com
 */

public class GameActivity extends Activity {

	private static final String TAG = GameActivity.class.getSimpleName();

	private GameView mKoozacGameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// remove titlebar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// make fullscreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// force portrait orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// create new GameView and set it
		mKoozacGameView = new GameView(this);

		setContentView(mKoozacGameView);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
