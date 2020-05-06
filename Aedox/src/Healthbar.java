import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Healthbar {
	Enemy e;
	Image[] sprites;
	Image image;
	public Healthbar(Enemy e) {
		this.e = e;
        try {
        	sprites = new Image[3];
            for (int z = 0; z < 3; z++) {
                sprites[z] = ImageIO.read(this.getClass().getClassLoader().getResource("hp" + (z + 1) + ".png"));
                sprites[z] = sprites[z].getScaledInstance(100, 20, 1);
            }
        }
        catch (IOException ea) {
            ea.printStackTrace();
        }
	}
	
	public void draw(Graphics g, int px, int py) {
		if (e.health > 0) {
			image = sprites[e.health - 1];
			int drawx = e.x - px + Runner.WINX / 2;
			int drawy = e.y - 20 - py + Runner.WINY / 2;
			g.drawImage(image, drawx, drawy, null);
		}
	}
}
