package com.neusoft.planewar.test;

import java.awt.Graphics;
import java.awt.Image;

import com.neusoft.planewar.constant.Constant;
import com.neusoft.planewar.core.MyFrame;
import com.neusoft.planewar.util.GameUtil;

/**
 * 
 * @author zzk<br>
 *         1.дһ����̳�Frame��<br>
 *         2.���ô������� <br>
 *         3.��дpaint(Graphics g)
 */
public class TestFrame4 extends MyFrame {

	Image background = GameUtil.getImage("com/neusoft/planewar/img/ball.png");
	int x = 0;
	int y = 0;
	int width = background.getWidth(null);// �õ���ǰͼƬ�Ŀ��
	int height = background.getHeight(null);// �õ���ǰͼƬ�Ŀ��
	/**
	 * ����Ƕ�theta
	 */
	double theta;
	/**
	 * �볤��
	 */
	int longAixs = (Constant.GAME_WIDTH-width)/2;
	/**
	 * �����
	 */
	int shortAixs = (Constant.GAME_HEIGHT-height)/2;
	double speed = 0.05;

	// double theta = Math.PI/6;
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, x, y, null);
		x = longAixs+(int) (longAixs * Math.cos(theta));
		y = shortAixs+(int) (shortAixs * Math.sin(theta));
		theta += speed;
		
	}

	public static void main(String[] args) {
		TestFrame4 tf = new TestFrame4();
		tf.launchFrame();
	}
}
