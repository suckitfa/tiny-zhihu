package com.zzk.teris.core.factory;

import java.awt.Color;

import com.zzk.teris.core.AbstractBlock;
import com.zzk.teris.core.AbstractBlockFactory;
import com.zzk.teris.core.blocks.Block4;
/**
 * �鹤��
 * @author zzk
 */
public class Block4Factory implements AbstractBlockFactory{
	@Override
	public AbstractBlock getBlock(int x, int y, Color color) {
		return new Block4(x,y,color);

	}
}
