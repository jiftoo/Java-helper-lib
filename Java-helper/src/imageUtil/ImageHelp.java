package imageUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageHelp {
	
	///////
	public static BufferedImage fillColor(int w, int h, String color) {
		BufferedImage img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		Graphics gr = img.createGraphics();
		gr.setColor(Color.decode(color));
		gr.fillRect(0, 0, w, h);
		return img;
	}
	public static BufferedImage fillColor(int w, int h, Color color) {
		BufferedImage img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		Graphics gr = img.createGraphics();
		gr.setColor(color);
		gr.fillRect(0, 0, w, h);
		return img;
	}
	///////
	public static BufferedImage drawRect(BufferedImage img, int shapeX, int shapeY, int shapeW, int shapeH) {
		img.createGraphics().drawRect(shapeX, shapeY, shapeW, shapeH);
		return img;
	}

}