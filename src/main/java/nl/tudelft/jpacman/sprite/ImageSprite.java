package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

/**
 * Simplest implementation of a Sprite, it merely consists of a static image.
 * 
 * @author Jeroen Roosen
 */
public class ImageSprite implements Sprite {

	/**
	 * Internal image.
	 */
	private final Image image;

	/**
	 * Creates a new sprite from an image.
	 * 
	 * @param img
	 *            The image to create a sprite from.
	 */
	public ImageSprite(Image img) {
		this.image = img;
	}

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.drawImage(image, x, y, x + width, y + height, 0, 0,
				image.getWidth(null), image.getHeight(null), null);
	}

	@Override
	public Sprite split(int x, int y, int width, int height) {
		if (image.getWidth(null) < x || image.getHeight(null) < y) {
			return new EmptySprite();
		}

		BufferedImage newImage = newImage(width, height);
		newImage.createGraphics().drawImage(image, 0, 0, width - 1, height - 1,
				x, y, x + width - 1, y + height - 1, null);

		return new ImageSprite(newImage);
	}

	/**
	 * Creates a new, empty image of the given width and height. Its
	 * transparency will be a bitmask, so no try ARGB image.
	 * 
	 * @param width
	 *            The width of the new image.
	 * @param height
	 *            The height of the new image.
	 * @return The new, empty image.
	 */
	private BufferedImage newImage(int width, int height) {
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		return gc.createCompatibleImage(width, height, Transparency.BITMASK);
	}

	@Override
	public int getWidth() {
		return image.getWidth(null);
	}

	@Override
	public int getHeight() {
		return image.getHeight(null);
	}

}
