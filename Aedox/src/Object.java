import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Object {
	public int x;
	public int y;
	public int drawx;
	public int drawy;
	public int width;
	public int height;
	public boolean collideable;
	public Image image;
	public int area;
	public String imgname;
	
	public Object(int x, int y, int w, int h , boolean c, int a, String i) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.collideable = c;
		this.area = a;
		this.imgname = i;
		try {
			this.image = ImageIO.read(this.getClass().getClassLoader().getResource(this.imgname));
			this.image = this.image.getScaledInstance(w, h, 1);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int px, int py, int a) {
		drawx = x - px + Runner.WINX / 2;
		drawy = y - py + Runner.WINY / 2;
		if (area == a) {
//			System.out.println("Draw ;D");
			g.drawImage(image, drawx, drawy, null);
			
		}
	}
}
