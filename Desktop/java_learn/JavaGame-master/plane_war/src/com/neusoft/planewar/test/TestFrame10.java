package com.neusoft.planewar.test;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import com.neusoft.planewar.constant.Constant;
import com.neusoft.planewar.core.MyFrame;
import com.neusoft.planewar.util.GameUtil;

/**
 * ˫����
 * @author zzk
 */
public class TestFrame10 extends MyFrame {

	Image background = GameUtil.getImage("com/neusoft/planewar/img/ball.png");
	int x = 0;
	int y = 0;
	int width = background.getWidth(null);// �õ���ǰͼƬ�Ŀ��
	int height = background.getHeight(null);// �õ���ǰͼƬ�Ŀ��
	/**
	 * ����Ƕ�theta
	 */
	Point center = new Point((Constant.GAME_WIDTH-width)/2, (Constant.GAME_HEIGHT-height)/2);
	double theta;
	double speed = 0.05;
	int a=100;
	int b =100;
	// double theta = Math.PI/6;
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, x, y, null);
		x=(int) (center.x+a*1/Math.cos(theta));
		y=(int) (center.y+b*Math.sin(theta)/Math.cos(theta));
		theta += speed;

	}

	public static void main(String[] args) {
		TestFrame10 tf = new TestFrame10();
		tf.launchFrame();
	}
}