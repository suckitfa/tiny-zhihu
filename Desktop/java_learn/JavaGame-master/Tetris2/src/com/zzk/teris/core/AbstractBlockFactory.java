package com.zzk.teris.core;

import java.awt.Color;
/**
 * ����鹤��
 * @author zzk
 *
 */
public interface AbstractBlockFactory {
	public AbstractBlock getBlock(int x,int y,Color color);
}
