package com.dipen.numtrix;

import java.util.ArrayList;

/**
 * @author Dipen Pradhan
 * @email dipenpradhan13@gmail.com
 */

public class BlockManager extends ArrayList<Block> {

	/***********************************************************
	 * find a block from list using its ID
	 ***********************************************************/
	public Block findBlockByID(int _ID) {
		final ArrayList<Block> blockList = this;

		for (int i = blockList.size() - 1; i >= 0; i--) {

			final Block mBlock = blockList.get(i);

			if (mBlock.getID() == _ID) {
				return mBlock;
			}

		}
		return null;
	}

	/***********************************************************
	 * find a block from list using its position
	 ***********************************************************/

	public Block findBlockByPosition(int _x, int _y) {

		final ArrayList<Block> blockList = this;

		for (int i = blockList.size() - 1; i >= 0; i--) {
			final Block mBlock = blockList.get(i);

			if (mBlock.getXLoc() == _x && mBlock.getYLoc() == _y) {
				return mBlock;
			}

		}
		return null;
	}

}
