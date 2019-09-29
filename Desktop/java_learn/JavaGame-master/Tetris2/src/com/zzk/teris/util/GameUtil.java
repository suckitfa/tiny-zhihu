package com.zzk.teris.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GameUtil {

	/**
	 * ����ͼƬ�����·����ȡͼƬ
	 * 
	 * @param imagePath
	 * @return ͼƬ
	 */
	public static Image getImage(String imagePath) {
		URL url = GameUtil.class.getClassLoader().getResource(imagePath);
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
