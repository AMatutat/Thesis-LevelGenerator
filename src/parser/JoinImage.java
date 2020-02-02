package parser;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Hilfsklasse zur Verbindung von BufferdImages
 * @author Andre Matutat
 *
 */
public class JoinImage {
/**
 * Verbindet Images nebeneinander
 * @param img1 linkes Image
 * @param img2 rechtes Image
 * @return neues Image
 */
	public static BufferedImage joinBufferedImageSide(final BufferedImage img1, final BufferedImage img2) {
		int wid = img1.getWidth() + img2.getWidth();
		int height = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, wid, height);
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, img1.getWidth(), 0);
		g2.dispose();
		return newImage;
	}

	/**
	 * Verbindet Images untereinander
	 * @param img1 obers Image
	 * @param img2 unteres Image
	 * @return neues Image
	 */
	public static BufferedImage joinBufferedImageDown(final BufferedImage img1, final BufferedImage img2) {
		{
			int wid = Math.max(img1.getWidth(), img2.getWidth());
			int height = img1.getHeight() + img2.getHeight();
			BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = newImage.createGraphics();
			Color oldColor = g2.getColor();
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, wid, height);
			g2.setColor(oldColor);
			g2.drawImage(img1, null, 0, 0);
			g2.drawImage(img2, null, 0, img1.getHeight());
			g2.dispose();
			return newImage;
		}
	}
}
