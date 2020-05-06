import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	public int x = 0;
	public int y = 0;
	public String[] names;
	public int[] amount;
	// TODO CHANGE BACK TO 4
	public int speed = 4;
	public int uppies = 0;
	public int walkies = 0;
	public static int cunk = 0;
	public static int lead = 0;
	// TODO CHANGE BACK TO 3
	public int max_health = 3;
	public int health = 3;
	// TODO CHANGE BACK TO 0
	public int area = 0;
	// area 1 = cave
	// area 2 = village
	// area 3 = ben's house
	// area 4 = dungeon
	public int frames = 0;
	public int healframes = 0;
	public int move_frames = 0;
	public boolean right = true;
	public int step = 0;
	public int ogx = 0;
	public int ogy = 0;
	public int coins = 0;
	public int roses = 0;
	public Image[] sprites;
	public Image currentsprite;
	public Heart[] healthbar;
	public Sword sword = new Sword();
	public int invis;
	public int swing;
	public final static int width = 75;
	public final static int height = 115;
	public final static int[] widths = {1500, 1500, 350, 1500, 1500};
	public final static int[] heights = {1500, 1500, 200, 1500, 1500};
	
	public Player() {
		healthbar = new Heart[max_health];
		for (int x = 0; x < max_health; x++) {
			healthbar[x] = new Heart(x);
		}
		loadSprites();
		currentsprite = sprites[0];
	}

	private void loadSprites() {
		try {
			sprites = new Image[9];
			sprites[0] = ImageIO.read(this.getClass().getClassLoader().getResource("triston.png"));
			sprites[0] = sprites[0].getScaledInstance(width, height, 1);
			for (int x = 0; x < 8; x++) {
				sprites[x + 1] = ImageIO.read(this.getClass().getClassLoader().getResource("triston-" + x + ".png"));
				sprites[x + 1] = sprites[x + 1].getScaledInstance(width, height, 1);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void key(int[] useKeys) {
		if (useKeys[0] != 0) {
			walkies = useKeys[0];
		}
		if (useKeys[1] != 0) {
			uppies = useKeys[1];
		}
		if (useKeys[2] == 1) {
			lead = 1;
			cunk = 1;
		}
		if (useKeys[3] == 1) {
			swing = 1;
		}
	}

	public void off(int remKeys) {
		if (remKeys == 0) {
			walkies = 0;
		}
		if (remKeys == 1) {
			uppies = 0;
		}
		if (remKeys == 2) {
			lead = 0;
		}
	}
	
	public void draw(Graphics g, ArrayList<Object> objects, boolean move, int gamestate) {
		// TODO REMOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOVE THIS
//		move = true;
		
		healframes++;
		if (healframes > 2000) {
			if (health < max_health) {
				health++;
			}
			healframes = 0;
		}
		
		if (gamestate == 6) {
			ogx = x;
			ogy = y;
		}
		if (gamestate == 9) {
			moveAbout(250, 350, 1500, 1000);
			walkies = 1;
			uppies = 0;
			move = true;
		}
		if (gamestate == 10) {
			walkies = 0;
			uppies = 0;
		}
		if (move) {
			int nowx = x + speed * walkies;
			int nowy = y + speed * uppies;
			if (notcollision(nowx, nowy, objects)) {
				x = nowx;
				y = nowy;
			}
			if (walkies != 0 || uppies != 0) {
				move_frames++;
				if (move_frames > 60 / speed) {
					step++;
					move_frames = 0;
				}
				if (step > 3) {
					step = 0;
				}
			}
			if (walkies == 1) {
				right = true;
			}
			else if (walkies == -1) {
				right = false;
				currentsprite = sprites[step + 5];
			}
			if (right) {
				currentsprite = sprites[step + 1];
			}
			else {
				currentsprite = sprites[step + 5];
			}
			if (!(walkies != 0 || uppies != 0)) {
				move_frames = 0;
				step = 0;
				currentsprite = sprites[0];
			}
		}
		else {
			currentsprite = sprites[0];
		}
		
		g.drawImage(currentsprite, (Runner.WINX - width) / 2, (Runner.WINY - height) / 2, null);
		if (swing == 1 && sword.action == false) {
			sword.action = true;
			sword.right = right;
			swing = 0;
		}
		invis--;
	}

	public void moveAbout(int a, int b, int px, int py) {
		if (frames < a) {
			frames++;
			x = (int) (ogx + (1.0 * (px - ogx) * frames / b));
			y = (int) (ogy + (1.0 * (py - ogy) * frames / b));
		}
	}
	
	private boolean notcollision(int nowx, int nowy, ArrayList<Object> objects) {
		for (Object o : objects) {
			if (o.collideable && o.area == area) {
				o.drawx = o.x - nowx + Runner.WINX / 2;
				o.drawy = o.y - nowy + Runner.WINY / 2;
				if (Runner.playercollide(o)) {
					System.out.println(o.imgname);
					return false;
				}
			}
		}
		if (Math.abs(nowx) > widths[area]) {
			System.out.println("bounds");
			return false;
		}
		if (Math.abs(nowy) > heights[area]) {
			System.out.println("bounds");
			return false;
		}
		return true;
	}
}
