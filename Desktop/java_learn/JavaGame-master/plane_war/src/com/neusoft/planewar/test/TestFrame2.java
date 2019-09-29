package com.neusoft.planewar.test;

import java.awt.Graphics;
import java.awt.Image;

import com.neusoft.planewar.constant.Constant;
import com.neusoft.planewar.core.MyFrame;
import com.neusoft.planewar.util.GameUtil;

/**
 * 
 * @author zzk<br>
 * 1.дһ����̳�Frame��<br>
 * 2.���ô������� <br>
 * 3.��дpaint(Graphics g)
 */
public class TestFrame2 extends MyFrame {

	Image background = GameUtil.getImage("com/neusoft/planewar/img/timg.png");
	int x = 0;
	int y = 0;
	int xSpeed = 10;
	int ySpeed = 1;
	int width = background.getWidth(null);// �õ���ǰͼƬ�Ŀ��
	int height = background.getHeight(null);// �õ���ǰͼƬ�Ŀ��
	/**
	 * ���Boolean���͵ı����ı��˶�״̬<br>
	 * һ��ʼ�����˶�
	 */
	boolean right = false;

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, x, y, null);
		if (right) {// ��ǰ����
			x += xSpeed;
		} else {
			x -= xSpeed;
		}

		if (x > (Constant.GAME_WIDTH - width)) {// ��һ���ķ���
			right = false;
		} else if (x < 0) {
			right = true;
		}
	}

	public static void main(String[] args) {
		TestFrame2 tf = new TestFrame2();
		tf.launchFrame();
	}
}
