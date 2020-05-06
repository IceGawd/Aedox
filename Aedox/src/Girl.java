import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Girl extends Object {
	boolean right = true;
	int frames = 0;
	int steps = 0;
	Image[] sprites;
	public Girl(int x, int y, int w, int h, boolean c, int a, String i) {
		super(x, y, w, h, c, a, i);
        try {
        	sprites = new Image[3];
            for (int z = 0; z < 3; z++) {
                sprites[z] = ImageIO.read(this.getClass().getClassLoader().getResource("girl-" + z + ".png"));
                sprites[z] = sprites[z].getScaledInstance(width, height, 1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public void draw(Graphics g, int px, int py, int a) {
//		System.out.println(x);
//		System.out.println(y);
		
		if (!(TextBox.name.equals("Chloe") && TextBox.exist)) {
			frames++;
			if (frames > 10) {
				steps++;
				frames = 0;
			}
			if (steps > 2) {
				steps = 0;
			}
			
			image = sprites[steps];
			
			if (right) {
				x += 3;
			}
			if (x > 1000) {
				right = false;
			}
			if (!right) {
				x -= 3;
				// Flip the image horizontally
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(Sword.toBufferedImage(image), null);
			}
			if (x < -1000) {
				right = true;
			}
		}
		super.draw(g, px, py, a);
	}

}
