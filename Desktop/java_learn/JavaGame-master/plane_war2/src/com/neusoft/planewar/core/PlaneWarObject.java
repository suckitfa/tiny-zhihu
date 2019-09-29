package com.neusoft.planewar.core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import com.neusoft.planewar.client.PlaneWarClient;

public abstract class PlaneWarObject implements Drawable,Moveable{
	//������ϵ(���ϵ)
	//��ͣ�����ģʽ
	public PlaneWarClient pwc;
	public int x;
	public int y;
	public Image img;
	public int width;
	public int height;
	public boolean good;
	/**
	 * ���зɻ���ս��ͳһ�Ļ��ķ���
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
	}
	/**
	 * �ж��ǵз����Ǽ���
	 */
	
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}
	/**
	 * �������Լ�ʵ���Լ���move����
	 */
	@Override
	public abstract void move();
	/**
	 * ��ȡ�ӵ���Ӧ�ľ���
	 */
	public Rectangle getRectangle(){
		return new Rectangle(x, y, width, height);
	}
}
