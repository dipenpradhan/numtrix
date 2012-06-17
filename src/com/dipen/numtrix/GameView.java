package com.dipen.numtrix;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Dipen Pradhan
 * @email dipenpradhan13@gmail.com
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	// declarations

	private static final String TAG = GameView.class.getSimpleName();

	private GameThread mGameThread;

	public Context mContext;
	private int height, width;

	// constructor
	public GameView(Context _Context) {

		super(_Context);

		this.mContext = _Context;

		// get SurfaceHolder and pass SH and view as parameters to GameThread
		getHolder().addCallback(this);
		mGameThread = new GameThread(getHolder(), this);

		// force screen to stay on
		setKeepScreenOn(true);
		setFocusable(true);

	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder _SurfaceHolder) {

		mGameThread.setRunning(true);
		mGameThread.start();

		// get height and width of screen
		height = getHeight();
		width = getWidth();

	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// stop thread if surface is destroyed i.e. activity hidden or closed
		mGameThread.setRunning(false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// when screen is touched
		// set static booleans in GameThread to indicate
		// moving block to left or right
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			// if touched on left side of screen
			if (event.getX() < width / 2) {
				GameThread.moveBlockLeft = true;
			}
			// if touched on right side of screen
			else {
				GameThread.moveBlockRight = true;
			}
		}

		// when finger is lifted
		if (event.getAction() == MotionEvent.ACTION_UP) {
			//
		}

		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode==KeyEvent.KEYCODE_DPAD_LEFT)
			{
				GameThread.moveBlockLeft = true;
			}
				
			if(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT)
			{
				GameThread.moveBlockRight= true;
			}
				
				
		return super.onKeyDown(keyCode, event);
	}
	
	

}
