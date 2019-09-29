package com.zzk.teris.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import com.zzk.teris.client.TerisClient;
import com.zzk.teris.constant.Constant;
import com.zzk.teris.core.blocks.Block4;
import com.zzk.teris.core.state.ActiveState;
import com.zzk.teris.core.state.StopState;

/**
 * ����
 * 
 * @author zzk
 */
public abstract class AbstractBlock implements Drawable {
	private static enum Direction{
		LEFT,RIGHT,DOWN,NORMAL,ROTATE;
	}
	private List<Square> squareList = new LinkedList<>();// ����������ɱ�
	private int x, y;// ����
	private Direction direction;// ��ķ���״̬
	private AbstractBlockState state;// ����˶�״̬
	private int downSpeed = 1;// �½����ٶ�
	private Point center;// �����ĵ�λ��
	private Color color;// ����ɫ
	private int count = 1;// ��ת��ʱ����
	private int minX;// ����߽�
	private int minY;// ���ϱ߽�
	private int maxX;// ���ұ߽�
	private int maxY;// ���±߽�
	{
		MyFrame.sleepTime = 30;
		direction = Direction.NORMAL;//��ʼ������
		state = new ActiveState(this);// ��ʼ��Ϊ��Ծ̬
	}
	public AbstractBlock(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		createSquareList();
		updateCenter();
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<Square> getSquareList() {
		return squareList;
	}


	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public AbstractBlockState getState() {
		return state;
	}

	public void setState(AbstractBlockState state) {
		this.state = state;
	}

	/**
	 * ��ת��
	 */
	private void rotate() {
		if (this instanceof Block4) {
			return;
		}
		for (Square square : squareList) {
			int dx = square.x - center.x;
			int dy = square.y - center.y;
			if (x <= Constant.GAME_X_LEFT) {
				square.setLocation(center.x - dy + Constant.BLOCK_SPACE
						+ count * (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE) / 2, center.y + dx);
			} else if (maxX >= Constant.GAME_X_RIGHT - Constant.BLOCK_WIDTH / 2) {
				square.setLocation(center.x - dy - Constant.BLOCK_WIDTH * 2 - Constant.BLOCK_SPACE
						+ count * (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE) / 2, center.y + dx);
			} else {
				square.setLocation(center.x - dy - Constant.BLOCK_WIDTH
						+ count * (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE) / 2, center.y + dx);
			}
		}
		count = -count;
	}

	/**
	 * �������ҷ�������ײ
	 */
	private void handleCollision() {
		for (Square square : squareList) {
			switch (direction) {
			case LEFT:// �������ķ�����������������,��ֹͣ
				if (square.getCenterX() == minX && (TerisClient.getSquare(square, 0, -1) != null || TerisClient.getSquare(square, 1, -1) != null
						|| TerisClient.getSquare(square, -1, -1) != null)) {
					direction = Direction.NORMAL;
				}
				break;
			case RIGHT:// �������ķ�����Ҳ�����������,��ֹͣ
				if (square.getCenterX() == maxX && (TerisClient.getSquare(square, 0, 1) != null || TerisClient.getSquare(square, 1, 1) != null
						|| TerisClient.getSquare(square, -1, 1) != null)) {
					direction = Direction.NORMAL;
				}
				break;
			default:
				break;
			}
		}
	}

	public void move() {
		handleCollision();// �������ҷ�����ײ����
		switch (direction) {// ���ݷ���״̬����
		case LEFT:
			x -= (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE);
			for (Square square : squareList) {// ����ÿ��С��ʹ������
				square.x -= (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE);
			}
			break;
		case RIGHT:
			x += (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE);
			for (Square square : squareList) {// ����ÿ��С��ʹ������
				square.x += (Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE);
			}
			break;
		case DOWN:
			MyFrame.sleepTime /= 30;// �ӿ������ٶ�
			break;
		case ROTATE:
			rotate();// ��ת
			break;
		case NORMAL:
			break;
		}
		direction = Direction.NORMAL;// �ָ�״̬
		y += downSpeed;
		for (Square square : squareList) {// ��Ȼ����
			square.y += downSpeed;
		}
	}

	@Override
	public void draw(Graphics g) {
		updateCenter();// �������ĵ�����
		state.saveSquares();// ����square
		state.draw(g);// ����״̬����
	}
	/**
	 * �����±߽�
	 */
	public void outOfBounds() {
		if (maxY == (Constant.GAME_Y_DOWN - Constant.BLOCK_HEIGHT / 2 - Constant.BLOCK_SPACE)) {
			state = new StopState(this);// ֹͣ
		}
	}
	/**
	 * �������ĵ�����
	 */
	private void updateCenter() {
		if (center == null)
			center = new Point();
		minX = maxX = (int) squareList.get(0).getCenterX();
		minY = maxY = (int) squareList.get(0).getCenterY();
		for (Square square : squareList) {
			int centerX = (int) square.getCenterX();
			int centerY = (int) square.getCenterY();
			minX = Math.min(centerX, minX);
			maxX = Math.max(centerX, maxX);
			minY = Math.min(centerY, minY);
			maxY = Math.max(centerY, maxY);
		}
		center.x = (minX + maxX) / 2;
		center.y = (minY + maxY) / 2;
		x=minX - Constant.BLOCK_WIDTH / 2;
		y=minY - Constant.BLOCK_HEIGHT / 2;
	}

	/**
	 * ���squareList
	 */
	protected abstract void createSquareList();

	/**
	 * ���̰����¼�
	 * 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			direction = Direction.ROTATE;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			direction = Direction.DOWN;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			if (x <= Constant.GAME_X_LEFT) {// ��߽�
				direction = Direction.NORMAL;
			} else {
				direction = Direction.LEFT;
			}
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			if (maxX >= Constant.GAME_X_RIGHT - Constant.BLOCK_WIDTH / 2) {// �ұ߽�
				direction = Direction.NORMAL;
			} else {
				direction = Direction.RIGHT;
			}
			break;
		}
	}

}
