package com.neusoft.planewar.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.neusoft.planewar.constant.Constant;
import com.neusoft.planewar.util.ImageUtil;

/**
 * ��Ϸ���Զ��崰�ڵĸ��� ����һ�Σ�����ʹ��
 * 
 * @author zzk
 *
 */
public class MyFrame extends Frame {
	/**
	 * �Զ������ɴ��ڵķ���
	 * 
	 * @throws InterruptedException
	 */
	public void launchFrame() {
		// ���ô��ڴ�С
		setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		// ���ô���λ��
		// setLocation(0, 0);
		setLocationRelativeTo(null);// ��Ծ��У�����null���������ʾ����Ļ
		// ���ô��ڱ���
		setTitle("�ɻ���ս(�޾���)");
		// ���ÿɼ�
		setVisible(true);
		// ���ò��ܸı��С
		setResizable(false);
		// ���ùرմ���
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);// ���ùر�
			}
		});
		enableInputMethods(false);//�������뷨
		setBackground(Color.BLACK);

		new MyThread().start();

	}

	/**
	 * ��ֹͼƬ��˸
	 * 
	 * @param g
	 */
	Image backImg = null;

	@Override
	public void update(Graphics g) {
		if (backImg == null) {
			backImg = createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		}
		Graphics backg = backImg.getGraphics();
		Color c = backg.getColor();
		backg.setColor(Color.BLACK);
		backg.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		backg.setColor(c);
		paint(backg);		
		g.drawImage(backImg, 0, 0, null);
	}

	/**
	 * ���� ����һ�����»����߳��ڲ���
	 * 
	 * @param args
	 */
	class MyThread extends Thread {
		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
