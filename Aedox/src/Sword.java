import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword {
	Image image;
	int turn;
	boolean action;
	boolean right;
	static int width = 32;
	static int height = 125;
	int nowwidth;
	int nowheight;
	int frames = 0;
	int drawLocationX = (Runner.WINX) / 2;
	int drawLocationY = (Runner.WINY) / 2 - height;
	static final int FRAME = 60;
	public Sword() {
		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResource("steampunksword.png"));
			image = image.getScaledInstance(width, height, 1);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	
	public BufferedImage rotateImage(BufferedImage originalImage, double degree) {
	    int w = width;
	    int h = height;
	    double toRad = Math.toRadians(degree);
	    int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
	    int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

	    BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = rotatedImage.createGraphics();
	    g.setColor(Color.LIGHT_GRAY);
	    g.fillRect(0, 0, wPrime, hPrime);  // fill entire area
	    g.translate(wPrime / 2, hPrime / 2);
	    g.rotate(toRad);
	    g.translate(-w / 2, -h / 2);
	    g.drawImage(originalImage, 0, 0, null);
	    g.dispose();  // release used resources before g is garbage-collected
	    return rotatedImage;
	}
	
	public Image rotateBy(Image image, double degrees) {

	    // The size of the original image
	    int w = width;
	    int h = height;
	    // The angel of the rotation in radians
	    double rads = Math.toRadians(degrees);
	    // Some nice math which demonstrates I have no idea what I'm talking about
	    // Okay, this calculates the amount of space the image will need in
	    // order not be clipped when it's rotated
	    double sin = Math.abs(Math.sin(rads));
	    double cos = Math.abs(Math.cos(rads));
	    int newWidth = (int) Math.floor(w * cos + h * sin);
	    int newHeight = (int) Math.floor(h * cos + w * sin);

	    // A new image, into which the original can be painted
	    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotated.createGraphics();
	    // The transformation which will be used to actually rotate the image
	    // The translation, actually makes sure that the image is positioned onto
	    // the viewable area of the image
	    AffineTransform at = new AffineTransform();
	    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

	    // And we rotate about the center of the image...
	    int x = w / 2;
	    int y = h / 2;
	    at.rotate(rads, x, y);
	    g2d.setTransform(at);
	    // And we paint the original image onto the new image
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();

	    return rotated;
	}
	
	public void draw(Graphics g) {
		drawLocationX = (Runner.WINX) / 2;
		drawLocationY = (Runner.WINY) / 2 - height;
		if (action) {
			frames++;
			if (right) {
				turn = 2 * frames - 30;
			}
			else {
				turn = 30 - 2 * frames;
			}

			if (frames > FRAME) {
				action = false;
				frames = 0;
			}

			// Rotation information
			double rotationRequired = Math.toRadians(turn);
		    double sin = Math.sin(rotationRequired);
		    double cos = Math.cos(rotationRequired);
		    nowwidth = (int) Math.floor(width * cos + height * sin);
		    nowheight = (int) Math.floor(height * cos + width * sin);
		    double locationX = width / 2;
			double locationY = height;
			
			AffineTransform tx = AffineTransform.getTranslateInstance(drawLocationX, drawLocationY);
			tx.rotate(rotationRequired, locationX, locationY);
			
			Image i = image;
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(i, tx, null);
			
//			g.drawImage(rotateBy(image, turn), (Runner.WINX) / 2, (Runner.WINY) / 2 - height, null);
			
			/*
			// The required drawing location
			int drawLocationX = (Runner.WINX) / 2;
			int drawLocationY = (Runner.WINY) / 2 - height;

			// Rotation information
			double rotationRequired = Math.toRadians(turn);
		    
			double locationX = width / 2;
			double locationY = height;
			
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			
			Image i = op.filter(toBufferedImage(image), null);
			
			
			// Drawing the rotated image at the required drawing locations
			g.drawImage(i, drawLocationX, drawLocationY, null);
			*/
			
//			Image draw = rotateImage(toBufferedImage(image), turn);
//			g.drawImage(draw, (Runner.WINX - width) / 2, (Runner.WINY - height) / 2, null);
			
			/*
			BufferedImage bimage = toBufferedImage(image);
			final double rads = Math.toRadians(turn);
			final double sin = Math.abs(Math.sin(rads));
			final double cos = Math.abs(Math.cos(rads));
			final int w = (int) Math.floor(bimage.getWidth() * cos + bimage.getHeight() * sin);
			final int h = (int) Math.floor(bimage.getHeight() * cos + bimage.getWidth() * sin);
			final BufferedImage rotatedImage = new BufferedImage(w, h, bimage.getType());
			final AffineTransform at = new AffineTransform();
			at.translate(w / 2, h / 2);
			at.rotate(rads, 0, 0);
			at.translate(-bimage.getWidth() / 2, -bimage.getHeight() / 2);
			final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(rotateOp.filter(bimage,rotatedImage), (Runner.WINX) / 2, (int) (height * (Math.sin(rads) - 3.5) + (Runner.WINX) / 2), null);
			*/
		}
	}
}
