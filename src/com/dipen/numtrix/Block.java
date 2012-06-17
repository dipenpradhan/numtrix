package com.dipen.numtrix;

import com.dipen.koozac.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Dipen Pradhan
 * @email dipenpradhan13@gmail.com
 */

public class Block {

	// declarations
	private Bitmap mBlockBitmap;
	private int x;
	private int y;
	private int number;
	private int ID;
	private boolean isLanded;

	// constructor takes view, number, id and location of block

	public Block(GameView _GameView, int _number, int _ID, int _x, int _y) {

		switch (_number) {
		case 1:
			mBlockBitmap = BitmapFactory.decodeResource(_GameView.getResources(), R.drawable.block_1);
			break;

		case 2:
			mBlockBitmap = BitmapFactory.decodeResource(_GameView.getResources(), R.drawable.block_2);
			break;

		case 3:
			mBlockBitmap = BitmapFactory.decodeResource(_GameView.getResources(), R.drawable.block_3);
			break;

		case 4:
			mBlockBitmap = BitmapFactory.decodeResource(_GameView.getResources(), R.drawable.block_4);
			break;

		case 5:
			mBlockBitmap = BitmapFactory.decodeResource(_GameView.getResources(), R.drawable.block_5);
			break;

		case 6:
			mBlockBitmap = BitmapFactory.decodeResource(_GameView.getResources(), R.drawable.block_6);
			break;

		}

		this.number = _number;
		this.ID = _ID;
		this.x = _x;
		this.y = _y;

	}

	/*********** GET METHODS ********/

	// get current value of x
	public int getXLoc() {
		return x;
	}

	// get current value of y
	public int getYLoc() {
		return y;
	}

	// get bitmap of this Block
	public Bitmap getBitmap() {
		return mBlockBitmap;
	}

	// get ID of this Block
	public int getID() {
		return ID;
	}

	// get number of this Block ( the value that block carries in game )
	public int getNumber() {
		return number;
	}

	// get landed status of Block
	public boolean getLanded() {
		return isLanded;
	}

	/********** SET METHODS ********/

	// set value of x
	public void setXLoc(int x) {
		this.x = x;
	}

	// set value of y
	public void setYLoc(int y) {
		this.y = y;
	}

	// set bitmap for Block
	public void setBitmap(Bitmap _Bitmap) {
		this.mBlockBitmap = _Bitmap;
	}

	// set landed status of Block
	public void setLanded(boolean landedStatus) {
		isLanded = landedStatus;
	}
}
