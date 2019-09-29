package com.zzk.teris.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.zzk.teris.constant.Constant;
import com.zzk.teris.core.AbstractBlock;
import com.zzk.teris.core.AbstractBlockFactory;
import com.zzk.teris.core.MyFrame;
import com.zzk.teris.core.Square;
import com.zzk.teris.core.factory.Block1Factory;
import com.zzk.teris.core.factory.Block2Factory;
import com.zzk.teris.core.factory.Block3Factory;
import com.zzk.teris.core.factory.Block4Factory;
import com.zzk.teris.core.factory.Block5Factory;
import com.zzk.teris.core.factory.Block6Factory;
import com.zzk.teris.core.factory.Block7Factory;
import com.zzk.teris.core.state.StopedState;
import com.zzk.teris.util.ImageUtil;

/**
 * �ͻ���
 * 
 * @author zzk
 */
@SuppressWarnings("serial")
public class TerisClient extends MyFrame {
	public static final AbstractBlockFactory[] blockFactory = { 
			new Block1Factory(), new Block2Factory(), new Block3Factory(),
			new Block4Factory(), new Block5Factory(), new Block6Factory(),
			new Block7Factory(), };
	public static final Image failImg = ImageUtil.images.get("fail");// ��Ϸ����ͼ
	public static Square squares[][];
	public static AbstractBlock nextBlock;// ��һ����
	public static AbstractBlock block;// ��ǰ��
	public static int score;// ����
	public static final Random random=new Random();// �������������
	public static boolean fail;// ��Ϸ����״̬
	{
		//��ʼ����Ϸ����
		squares = new Square[Constant.GAME_HEIGHT / (Constant.BLOCK_HEIGHT + Constant.BLOCK_SPACE)
				+ 4][Constant.GAME_WIDTH / ((Constant.BLOCK_WIDTH + Constant.BLOCK_SPACE)) + 2];
		nextBlock = newBlock();
		block = newBlock();
		fail = false;
		score = 0;
	}

	@Override
	public void loadFrame() {
		super.loadFrame();
		// ��Ӽ��̼�����
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				block.keyPressed(e);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		drawBack(g);// ���Ʊ���
		for (int i = 0; i < squares[20].length; i++) {
			if (squares[20][i] != null) {//���鵽���ϱ߽�
				fail = true;
			}
		}
		if (!fail) {// �����Ϸδ����
			drawActiveBlock(g);// �����˶���
			drawStopSquare(g);// �᳤��ֹ������
			drawNextBlock(g);// �᳤��һ����
		} else {
			// ������Ϸ����ͼ
			g.drawImage(failImg, (Constant.FRAME_WIDTH - failImg.getWidth(null)) / 2 - 120,
					(Constant.FRAME_HEIGHT - failImg.getHeight(null)) / 2, null);
		}
	}

	/**
	 * �����˶��ķ���
	 */
	private void drawActiveBlock(Graphics g) {
		if (block.getState() instanceof StopedState) {
			block = nextBlock;
			nextBlock = newBlock();
		} else {
			block.draw(g);
		}
	}

	/**
	 * ���ƾ�ֹ�ķ���
	 * 
	 * @param g
	 */
	private void drawStopSquare(Graphics g) {
		for (int i = 1; i < squares.length; i++) {// �к�
			boolean isFull = true;
			for (int j = 1; j < squares[i].length; j++) {// �к�
				if (squares[i][j] != null) {
					squares[i][j].draw(g);
				} else {
					isFull = false;
				}
			}
			if (isFull) {// ���һ�����ˣ�����
				eliminate(i);
				System.out.println(1);
			}
		}
	}

	/**
	 * ����ָ����
	 */
	private void eliminate(int line) {
		score += 10;
		for (int k = line; k < squares.length - 1; k++) {
			for (int l = 1; l < squares[k].length; l++) {
				if (squares[k + 1][l] != null) {
					squares[k + 1][l].y += 42;
					squares[k][l] = squares[k + 1][l];
				} else {
					squares[k][l] = null;
				}
			}
		}
	}

	/**
	 * �����·���
	 * 
	 * @return
	 */
	private AbstractBlock newBlock() {
		int startX = Constant.GAME_X_LEFT + 42 * 7;// ��ĳ�ʼ������
		int startY = Constant.GAME_Y_UP + 1;// ���ʼ������
		return blockFactory[random.nextInt(blockFactory.length)].getBlock(startX, startY,
				Constant.COLOR_BLOCKS[random.nextInt(Constant.COLOR_BLOCKS.length)]);
	}

	/**
	 * ���Ʊ���
	 * 
	 * @param g
	 */
	private void drawBack(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(Constant.GAME_X_LEFT, Constant.GAME_Y_UP, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		g.setColor(Color.RED);
		// ���ƺ��
		for (int i = 1; i <= 6; i++) {
			g.drawRect(Constant.GAME_X_LEFT - i, Constant.GAME_Y_UP - i, Constant.GAME_WIDTH + 2 * i,
					Constant.GAME_HEIGHT + 2 * i);
			g.drawRect(Constant.GAME_X_RIGHT + i, Constant.GAME_Y_UP - i, 300, 300);
			g.drawRect(Constant.GAME_X_RIGHT + i, Constant.GAME_Y_UP + 300 - i, 300, 300);
		}
		// ������һ������
		drawNextBlock(g);
		g.setFont(new Font("΢���ź�", Font.BOLD, 40));
		// ���Ʒ���
		g.drawString("��  �� :" + score, Constant.GAME_X_RIGHT + 30, Constant.GAME_Y_UP + 350);
	}

	/**
	 * ������һ������
	 * 
	 * @param g
	 */
	private void drawNextBlock(Graphics g) {
		g.setFont(new Font("΢���ź�", Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString("NEXT", Constant.GAME_X_RIGHT + 50, Constant.GAME_Y_UP + 50);
		for (Square square : nextBlock.getSquareList()) {
			g.setColor(nextBlock.getColor());
			g.fillRect(square.x + 400, square.y + 100, square.width, square.height);
		}
	}
	/**
	 * ��ȡSquare����
	 * @param square С����
	 * @param dRow ��ƫ��
	 * @param dCol ��ƫ��
	 * @return Square
	 */
	public static Square getSquare(Square square, int dRow, int dCol) {
		return TerisClient.squares[(Constant.GAME_Y_DOWN - square.y) / (Constant.BLOCK_HEIGHT + Constant.BLOCK_SPACE)
				+ 1 + dRow][(square.x - Constant.GAME_X_LEFT) / (Constant.BLOCK_HEIGHT + Constant.BLOCK_SPACE) + 1
						+ dCol];
	}
	public static void main(String[] args) {
		new TerisClient().loadFrame();
	}
}
