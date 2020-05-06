import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Heart {
	public int width = 59;
	public int height = 50;
	public int num;
	public Image[] sprites;
	public Image current;
	public boolean alive = true;
	public int level = 0;
	
	public Heart(int n) {
		num = n;
		try {
			sprites = new Image[4];
			for (int z = 0; z < 4; z++) {
				sprites[z] = ImageIO.read(this.getClass().getClassLoader().getResource("heart" + (z + 1) + ".png"));
				sprites[z] = sprites[z].getScaledInstance(width, height, 1);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g) {
		level = (int) (Runner.time / (3 * 60 * 1000));
		if (!alive) {
			current = sprites[level];
			g.drawImage(current, 75 * num, 0, null);
		}
	}
}
