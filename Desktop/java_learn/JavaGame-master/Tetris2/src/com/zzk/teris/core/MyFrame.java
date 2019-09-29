package com.zzk.teris.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.zzk.teris.constant.Constant;

@SuppressWarnings("serial")
public class MyFrame extends Frame{
	public static int sleepTime = 30;
	/**
	 * ���ش���
	 */
	public void loadFrame(){
		this.setTitle("����˹����");
		this.setSize(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);//����
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
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
			backImg = createImage(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
		}
		Graphics backg = backImg.getGraphics();
		Color c = backg.getColor();
		backg.setColor(Color.WHITE);
		backg.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
		backg.setColor(c);
		paint(backg);		
		g.drawImage(backImg, 0, 0, null);
	}
	/**
	 * ���� ����һ�����»����߳��ڲ���
	 * 
	 * @param args
	 */
	class MyThread extends Thread{
		@Override
		public void run() {
			while(true){
				repaint();//�ػ�
				try {
					sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
