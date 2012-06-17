package com.dipen.numtrix;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * @author Dipen Pradhan
 * @email dipenpradhan13@gmail.com
 */

public class GameThread extends Thread {

	// declarations

	private static final String TAG = GameThread.class.getSimpleName();

	private Boolean running;
	private SurfaceHolder mSurfaceHolder;
	private GameView mGameView;

	private int blockID;
	private BlockManager mBlockManager;
	private Random mRandom;
	private int targetSum;
	public static boolean moveBlockLeft, moveBlockRight;
	private Paint mTextPaint;
	private int blockPosY;
	private int blockSize = 60;
	private int gameSpeed = 3;
	private int count;
	
Context context;
	// constructor
	public GameThread(SurfaceHolder _SurfaceHolder, GameView _GameView) {
		super();
		context=_GameView.getContext();
		this.mSurfaceHolder = _SurfaceHolder;
		this.mGameView = _GameView;

		// create a BlockManager, Random number generator, paint object for text

		mBlockManager = new BlockManager();

		mRandom = new Random();

		mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(80);
		mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		// spawn first block
		spawnBlock();

	}
int mWifiLevel;
//WifiManager mWifiManager;
	//
	public void run() {

		Log.d(TAG, "Starting thread");

		Canvas mCanvas;

		while (running) {
            
			mCanvas = null;

			try {
				// lock canvas and draw on it

				mCanvas = this.mSurfaceHolder.lockCanvas();

				synchronized (mSurfaceHolder) {

					// fill with black color
					mCanvas.drawARGB(255, 0, 0, 0);

					// show target sum
					mCanvas.drawText("Target: " + targetSum, 0, 80, mTextPaint);

					// draw all blocks to screen
					for (int i = 0; i < blockID; i++) {
						Block tempBlock = mBlockManager.findBlockByID(i);

						if (tempBlock != null) {
							mCanvas.drawBitmap(tempBlock.getBitmap(), tempBlock.getXLoc(), tempBlock.getYLoc(), null);
						}
					}

					// currentBlock is the block that is currently falling down
					Block currentBlock = mBlockManager.findBlockByID(blockID - 1);

					int currentXLoc = currentBlock.getXLoc();
					int currentYLoc = currentBlock.getYLoc();

					// if left side of screen is touched, check if block goes
					// outside of screen, check for overlap after moving -> move
					// block to left
					if (moveBlockLeft) {

						// if block is not going outside of screen
						if (currentXLoc != 0) {
							boolean allowMove = true;

							// loop through all existing blocks, excluding
							// current for checking overlap after movement
							// i.e. 0 to less than (blockID-1)
							for (int i = 0; i < blockID - 1; i++) {
								if (mBlockManager.findBlockByID(i) != null)

								{
									// if current block will overlap with an
									// existing block after moving to left, do
									// not allow movement
									if (currentXLoc - blockSize == mBlockManager.findBlockByID(i).getXLoc()
											&& (currentYLoc + blockSize > mBlockManager.findBlockByID(i).getYLoc() && currentYLoc < mBlockManager
													.findBlockByID(i).getYLoc() + blockSize)) {
										allowMove = false;
									}
								}
							}

							// if block won't overlap, move to left
							if (allowMove)
								currentBlock.setXLoc(currentXLoc - blockSize);
						}

						moveBlockLeft = false;
					}

					// if right side of screen is touched, check if block goes
					// outside of screen, check for overlap after moving -> move
					// block to right
					if (moveBlockRight) {

						// if block is not going outside of screen
						if (currentXLoc + blockSize != mCanvas.getWidth()) {

							boolean allowMove = true;

							// loop through all existing blocks, excluding
							// current for checking overlap after movement
							// i.e. 0 to less than (blockID-1)

							for (int i = 0; i < blockID - 1; i++) {
								if (mBlockManager.findBlockByID(i) != null)

								{ // if current block will overlap with an
									// existing block after moving to right, do
									// not allow movement

									if (currentXLoc + blockSize == mBlockManager.findBlockByID(i).getXLoc()
											&& (currentYLoc + blockSize > mBlockManager.findBlockByID(i).getYLoc() && currentYLoc < mBlockManager
													.findBlockByID(i).getYLoc() + blockSize)) {
										allowMove = false;
									}
								}
							}
							// if block won't overlap, move to right
							if (allowMove)
								currentBlock.setXLoc(currentXLoc + blockSize);
						}
						moveBlockRight = false;
					}

					// loop through all existing blocks, excluding
					// current for checking overlap after movement
					// i.e. 0 to less than (blockID-1)

					for (int i = 0; i < blockID - 1; i++) {

						// avoid null values caused by removing blocks
						if (mBlockManager.findBlockByID(i) != null) {

							// if current block lands on top of another existing
							// block
							if (currentBlock.getYLoc() + currentBlock.getBitmap().getHeight() == mBlockManager.findBlockByID(i).getYLoc()
									&& currentBlock.getXLoc() == mBlockManager.findBlockByID(i).getXLoc())

							{
								currentBlock.setLanded(true);

								int tempSum = currentBlock.getNumber();

								// if sum of current block and block lying
								// immediately below = target sum, remove both
								// blocks from screen
								if (currentBlock.getNumber()
										+ mBlockManager.findBlockByPosition(currentBlock.getXLoc(), currentBlock.getYLoc() + blockSize).getNumber() == targetSum) {
									mBlockManager.remove(currentBlock);
									mBlockManager.remove(mBlockManager.findBlockByPosition(currentBlock.getXLoc(), currentBlock.getYLoc() + blockSize));
								}

								// else, check sum of all blocks below current
								// block
								else {
									// loop from current block position to
									// bottom of screen, and find sum of all
									// numbers
									for (int j = currentBlock.getYLoc(); j < mCanvas.getHeight() - currentBlock.getBitmap().getHeight(); j += blockSize) {
										tempSum = tempSum + mBlockManager.findBlockByPosition(currentBlock.getXLoc(), j + blockSize).getNumber();
									}

									if (tempSum == targetSum) {

										for (int j = currentBlock.getYLoc(); j < mCanvas.getHeight(); j += blockSize) {
											mBlockManager.remove(mBlockManager.findBlockByPosition(currentBlock.getXLoc(), j));
										}
									}
								}

								// spawn a new block from top, now this becomes
								// currentBlock
								blockPosY = 0;
								spawnBlock();
							}
						}
					}

					// if current block lands at bottom edge of screen, stop
					// movement and spawn new block from top
					if (blockPosY > mCanvas.getHeight() - currentBlock.getBitmap().getHeight()) {
						currentBlock.setLanded(true);
						blockPosY = 0;
						spawnBlock();
					}

					// else, make current block fall downwards
					else {
						if (!currentBlock.getLanded()) {
							currentBlock.setYLoc(blockPosY += gameSpeed);
						}

					}
					// sleep thread for 10 ms to let all drawing and
					// calculations complete
					try {
						Thread.sleep(10);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

				}
				count++;
			}
			// after all operations, unlock canvas and post
			finally {
				if (mCanvas != null) {
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
			}
			// Log.d(TAG, "Game ticked" + count + " times");
		}

		while (!running) {
			try {
				Thread.sleep(1);
				if (checkRunning()) {
					setRunning(true);
				}
			} catch (InterruptedException e) {
				//
			}
		}
	}

	/*******************************************
	 * spawn a new block, with random number between 1 and 6, at random x
	 * position and given y position (y is always zero, in this case) also,
	 * generate a new target sum
	 *******************************************/
	private void spawnBlock() {
		mBlockManager.add(new Block(mGameView, mRandom.nextInt(6) + 1, blockID++, mRandom.nextInt(8) * blockSize, blockPosY));// mRandom.nextInt(8)*blockSize));
		updateTargetSum();
	}

	/*******************************************
	 * generate a random number for the target sum, which is not 1 and not
	 * smaller than the current block that drops from above
	 *******************************************/
	private void updateTargetSum() {
		int temp = mRandom.nextInt(10) + 1;
		if (temp != 0 && temp > mBlockManager.findBlockByID(blockID - 1).getNumber()) {
			// &&(temp-mBlockManager.size()>4||temp-mBlockManager.size()<0)){
			targetSum = temp;
		} else {
			updateTargetSum();
		}

	}

	// start or stop the thread
	public void setRunning(Boolean status) {
		this.running = status;

	}

	// check if thread is running
	public boolean checkRunning() {
		if (running == true) {
			return true;
		} else {
			return false;
		}
	}

}