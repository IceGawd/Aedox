import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Object {
	public Image[] sprites;
	public int health = 3;
	public double actualx;
	public double actualy;
	public final static int SPEED = 2;
	public final static int MAX = 20;
	public boolean right;
	public int frame = 0;
	public int step = 0;
	public boolean cooldown;
	public Healthbar healthbar = new Healthbar(this);
	public Enemy(int x, int y, int w, int h, boolean c, int a, String i) {
		super(x, y, w, h, c, a, i);
		actualx = x;
		actualy = y;
		try {
			sprites = new Image[13];
			for (int z = 0; z < 8; z++) {
				sprites[z] = ImageIO.read(this.getClass().getClassLoader().getResource("moving-" + z + ".png"));
				sprites[z] = sprites[z].getScaledInstance(width, height, 1);
			}
			for (int z = 0; z < 5; z++) {
				sprites[z + 8] = ImageIO.read(this.getClass().getClassLoader().getResource("dying-" + z + ".png"));
				sprites[z + 8] = sprites[z + 8].getScaledInstance(width, height, 1);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int px, int py, int a) {
		frame++;
		healthbar.draw(g, px, py);
		if (frame > 10) {
			frame = 0;
			step++;
		}
		if (health > 0 && !cooldown) {
			double increasex = SPEED * (x - px) / Math.sqrt(Math.pow(x - px, 2) + Math.pow(y - py, 2));
	//		System.out.println(x);
	//		System.out.println(y);
	//		System.out.println(step);
			actualx -= increasex;
			actualy -= SPEED * (y - py) / Math.sqrt(Math.pow(x - px, 2) + Math.pow(y - py, 2));
			if (increasex > SPEED / 5) {
				right = true;
			}
			if (increasex < SPEED / 5) {
				right = false;
			}
			if (step > 3) {
				step = 0;
			}
			if (right) {
				image = sprites[step];
			}
			else {
				image = sprites[step + 4];
			}
		}
		else if (cooldown){
			image = sprites[8];
			if (step > 5) {
				cooldown = false;
				step = 0;
			}
			if (health < 1) {
				cooldown = false;
			}
		}
		else {
			if (step < 5) {
				image = sprites[step + 8];
			}
		}
		
		x = (int) actualx;
		y = (int) actualy;
		super.draw(g, px, py, a);
	}
	
	public void hit(int damage, int x, int y) {
		cooldown = true;
		if (x > drawx) {
			this.actualx -= 100;
		}
		else {
			this.actualx += 100;
		}
		if (y > drawy) {
			this.actualy -= 100;
		}
		else {
			this.actualy += 100;
		}
		health--;
		if (health < 1) {
			frame = 0;
			step = 0;
		}
	}
}
