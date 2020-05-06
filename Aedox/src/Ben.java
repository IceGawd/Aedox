import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ben extends Object {
	boolean done = false;
	int frames = 0;
	int ogx;
	int ogy;
	Image[] sprites;
	public Ben(int x, int y, int w, int h, boolean c, int a, String i) {
		super(x, y, w, h, c, a, i);
        try {
        	sprites = new Image[6];
            for (int z = 0; z < 6; z++) {
                sprites[z] = ImageIO.read(this.getClass().getClassLoader().getResource("ben-" + z + ".png"));
                sprites[z] = sprites[z].getScaledInstance(width, height, 1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void draw(Graphics g, int px, int py, int a, int gamestate) {
		if (gamestate == 5) {
			moveAbout(250, 350, px, py);
			image = sprites[3 + (frames / 10) % 3];
		}
		if (gamestate == 6) {
			done = false;
			frames = 0;
			ogx = x;
			ogy = y;
			image = sprites[3];
		}
		if (gamestate == 9) {
			moveAbout(250, 350, 1500, 1000);
			image = sprites[(frames / 10) % 3];
		}
		if (gamestate == 10) {
			done = false;
			frames = 0;
			ogx = x;
			ogy = y;
			image = sprites[0];
		}
		super.draw(g, px, py, a);
	}
	
	public void moveAbout(int a, int b, int px, int py) {
		if (frames < a) {
			frames++;
			x = (int) (ogx + (1.0 * (px - ogx) * frames / b));
			y = (int) (ogy + (1.0 * (py - ogy) * frames / b));
		}
		else {
			done = true;
		}
	}
}
