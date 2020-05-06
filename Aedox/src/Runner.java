import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Runner {
	private JFrame frame = new JFrame("Aedox");
	private JPanel panel;
	private Player player = new Player();
	private Keyboard key = new Keyboard(player);
	public static Font main;
	public static Font second;
	private ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> important = new ArrayList<Object>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Ben ben = new Ben(200, 100, 100, 115, true, 2, "ben-0.png");
	private Girl girl = new Girl(0, 0, 130, 130, false, 1, "girl-0.png");
	private TextBox text = new TextBox();
	public static Clip clip1;
	public static Clip clip2;
	public static Clip clip3;
	public static Clip clip4;
	public static Clip clip5;
	public static Clip music;
	public int frames;
	public Image rose;
	public Image coin;
	public String[] musics = {"Talus.wav", "Disc_3.wav", "Menu_Music.wav", "Area_Math.wav"};
	private static final int delay = 17;
	private int actualdelay = delay;
	public static int WINX = 500;
	public static int WINY = 500;
	public static int time = 5 * 60 * 1000;
	// TODO OPACITY 255
	public static int opacity = 255;
	private Timer repaint = new Timer(actualdelay, new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if (10 * 60 * 1000 > time) {
				if (0 < time) {
					long a = System.currentTimeMillis();
					frame.repaint();
					long len = System.currentTimeMillis() - a;
					actualdelay = (int) (delay - len);
				}
				else {
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Keep the timer up by giving roses to Chloe!", "You Lose!", JOptionPane.ERROR_MESSAGE);
					repaint.stop();
				}
			}
			else {
				frame.dispose();
				JOptionPane.showMessageDialog(null, "This is the end of the demo version!", "You Win! ... kinda", JOptionPane.INFORMATION_MESSAGE);
				repaint.stop();
			}
		}
	});
	public static void main(String[] args) {
		new Runner().start();
	}
	
	public void playSound(String s, Clip c) {
		if (c != null) {
			try {
				c.stop();
				c.close();
				AudioInputStream a = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(s));
				c.open(a);
				c.start();
			}
			catch (Exception ex) {
				System.out.println("Error with playing sound.");
				ex.printStackTrace();
			}
		}
	}
	
	private void start() {
		try {
			clip1 = AudioSystem.getClip();
			clip2 = AudioSystem.getClip();
			clip3 = AudioSystem.getClip();
			clip4 = AudioSystem.getClip();
			clip5   = AudioSystem.getClip();
			music = AudioSystem.getClip();
			rose = ImageIO.read(this.getClass().getClassLoader().getResource("rose.png"));
			coin = ImageIO.read(this.getClass().getClassLoader().getResource("coin.png"));
			rose = rose.getScaledInstance(150, 150, 1);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		createObjects();
		makeFrame();
		repaint.start();
	}

	private void createObjects() {
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				objects.add(new Object(x * 500, y * 500, 500, 500, false, 0, "cave3.png"));
			}
		}
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				objects.add(new Object(x * 500, y * 500, 500, 500, false, 1, "village.png"));
			}
		}
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				objects.add(new Object(x * 500, y * 500, 500, 500, false, 3, "dungeon.png"));
			}
		}
		objects.add(new Object(-455, -256, 910, 512, false, 2, "room.png"));
		objects.add(new Object(-100, -150, 226, 296, false, 2, "carpet.png"));
		objects.add(new Object(-350, -150, 142, 280, true, 2, "counter.png"));
		objects.add(new Object(350, -150, 72, 118, true, 2, "table.png"));
		
		objects.add(new Object(-600, -600, 400, 500, true, 1, "house.png"));
		objects.add(new Object(0, -600, 400, 500, true, 1, "house2.png"));
		objects.add(new Object(600, -600, 400, 500, true, 1, "house4.png"));
		objects.add(new Object(-700, 600, 500, 500, true, 1, "house1.png"));
		objects.add(new Object(0, 600, 500, 500, true, 1, "house3.png"));
		objects.add(new Object(700, 600, 400, 500, true, 1, "house.png"));
		objects.add(new Object(-450, 1500, 400, 500, true, 1, "house4.png"));
		objects.add(new Object(450, 1500, 500, 500, true, 1, "house3.png"));
		objects.add(new Object(-450, -1500, 400, 500, true, 1, "house2.png"));
		objects.add(new Object(450, -1500, 500, 500, true, 1, "house1.png"));
		
		Object o;
		o = new Object(0, 1000, 100, 100, true, 0, "heart.png");
		objects.add(o);
		important.add(o);
		o = new Object(1500, 1000, 200, 400, true, 0, "door.png");
		objects.add(o);
		important.add(o);
		o = new Object(-143, 137, 272, 119, false, 2, "exit.png");
		objects.add(o);
		important.add(o);
		o = new Object(1500, -200, 200, 400, false, 1, "door.png");
		objects.add(o);
		important.add(o);
		o = new Object(-1500, 0, 200, 400, false, 3, "door.png");
		objects.add(o);
		important.add(o);
		o = new Object(-1500, -200, 200, 400, false, 1, "door.png");
		objects.add(o);
		important.add(o);
		o = new Object(-430, -190, 70, 110, false, 1, "phandelver.png");
		objects.add(o);
		important.add(o);
		
		objects.add(ben);
	}

	@SuppressWarnings("serial")
	private void makeFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel  = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		panel.addMouseListener(new MouseAdapter() {
			// Thats STILL future me's problem ;)
		});
		frame.add(panel);
		panel.setPreferredSize(new Dimension(WINX, WINY));
		panel.addKeyListener(key);
		panel.setFocusable(true);
		panel.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.pack();
		frame.setVisible(true);
		
		try {
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("zephyrea.ttf");
			main = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);
			stream = ClassLoader.getSystemClassLoader().getResourceAsStream("zephyrea.ttf");
			second = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(95f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
		frame.setIconImage(img.getImage());
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
	}

	public void draw(Graphics g){
//		System.out.println(WINX);
//		System.out.println(WINY);
		
		time -= delay;
		if (player.health < 1) {
			time -= 2 * 60 * 1000;
			player.health = player.max_health;
			player.coins /= 2;
			player.area = 0;
			player.x = 0;
			player.y = 0;
		}
		
		WINX = frame.getWidth();
		WINY = frame.getHeight();
		g.setFont(main);
		if (player.area == 0) {
			g.setColor(Color.DARK_GRAY);
		}
		if (player.area == 1) {
			g.setColor(Color.BLACK);
		}
		if (player.area == 2) {
			g.setColor(Color.BLACK);
		}
		if (player.area == 3) {
			frames++;
			if (frames > 60 && enemies.size() < Enemy.MAX) {
//				System.out.println("pom");
				frames = 0;
				int x = (int) (-1500 + Math.random() * 3000);
				int y = (int) (-1500 + Math.random() * 3000);
				while (Math.abs(x - player.x) < Runner.WINX / 2) {
					x = (int) (-1500 + Math.random() * 3000);
				}
				while (Math.abs(y - player.y) < Runner.WINY / 2) {
					y = (int) (-1500 + Math.random() * 3000);
				}
				enemies.add(new Enemy(x, y, 100, 75, true, 3, "moving-0.png"));
			}
		}
		g.fillRect(0, 0, WINX, WINY);
		
		for (Object o : objects) {
			o.draw(g, player.x, player.y, player.area);
		}
		
		for (int z = 0; z < enemies.size(); z++) {
			Enemy e = enemies.get(z);
			e.draw(g, player.x, player.y, player.area);
			if (playercollide(e) && player.invis < 0 && e.health > 0 && !e.cooldown) {
				player.health--;
				player.invis = 100;
				playSound("Damaged.wav", getFreeClip());
			}
			Sword s = player.sword;
			if (collide(s.drawLocationX, s.drawLocationY, s.nowwidth, s.nowheight, e.drawx, e.drawy, e.width, e.height) && s.action && e.health > 0 && !e.cooldown) {
				e.hit(1, s.drawLocationX, s.drawLocationY);
				playSound("Ghost_hit.wav", getFreeClip());
				if (e.health < 1) {
					playSound("Ghost_Death.wav", getFreeClip());
				}
			}
			
			if ((e.health < 1 && e.step > 10) || player.area != e.area) {
				if (e.health < 1 && e.step > 10) {
					player.coins++;
				}
				enemies.remove(z);
				z--;
			}
		}
		ben.draw(g, player.x, player.y, player.area, text.gamestate);
		girl.draw(g, player.x, player.y, player.area);
		player.draw(g, objects, text.move, text.gamestate);
		text.draw(g);
		
		changeText();
		boolean change = changeArea();
		miscSFX();
		
		if (change) {
			playSound(musics[player.area], music);
		}
		if (!music.isActive()) {
			playSound(musics[player.area], music);
		}
		
		if (text.gamestate == 1) {
			if (opacity > 0) {
				opacity -= 1;
			}
		}
		if (text.gamestate == 2) {
			opacity = 0;
		}		
		if (text.gamestate == 9) {
			opacity = (player.frames + ben.frames) / 2;
		}
		if (text.gamestate == 10) {
			opacity = 0;
		}
		
		for (int z = 0; z < player.healthbar.length; z++) {
			Heart h = player.healthbar[z];
			h.draw(g);
			if (player.health < z + 1) {
				h.alive = true;
			}
			else {
				h.alive = false;
			}
		}
		
		player.sword.draw(g);
		
		g.setFont(second);
		g.setColor(Color.PINK);
		g.drawString(Integer.toString(player.roses), WINX - 500, 100);
		g.drawImage(rose, WINX - 450, 0, null);
		g.drawString(Integer.toString(player.coins), WINX - 200, 100);
		g.drawImage(coin, WINX - 100, 0, null);
		String minutes = Integer.toString((time / (60 * 1000)) % 60);
		String seconds = Integer.toString((time / 1000) % 60);
		String milliseconds = Integer.toString(time % 1000);
		g.drawString(minutes + ":" + seconds + ":" + milliseconds, 0, 200);
		
		g.setColor(new Color(0, 0, 0, opacity));
		g.fillRect(0, 0, WINX, WINY);
		
		if (Player.cunk == 1 && TextBox.exist && text.done && text.move) {
			TextBox.exist = false;
		}
		Player.cunk = 0;
	}
	
	public static boolean collide(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
		if (x1 < x2 + width2 && x1 + width1 > x2 && y1 < y2 + height2 && y1 + height1 > y2) {
			return true;
		}
		return false;
	}
	
	public static boolean playercollide(Object o) {
		return collide((WINX - Player.width) / 2, (WINY - Player.height) / 2, Player.width, Player.height, o.drawx, o.drawy, o.width, o.height);
	}
	
	public Clip getFreeClip() {
		if (!clip2.isActive()) {
			return clip2;
		}
		if (!clip3.isActive()) {
			return clip3;
		}
		if (!clip4.isActive()) {
			return clip4;
		}
		if (!clip5.isActive()) {
			return clip5;
		}
		return null;
	}
	
	public void miscSFX() {
		if ((player.walkies != 0 || player.uppies != 0) && text.move) {
			if (!clip1.isActive()) {
				playSound("Movement.wav", clip1);
			}
		}
		else {
			clip1.stop();
		}
	}
	
	public boolean changeArea() {
		if (player.area == 0 && playercollide(important.get(1)) && text.gamestate > 10) {
			playSound("Interact.wav", getFreeClip());
//			System.out.println("a");
			player.area = 1;
			player.x = -1000;
			player.y = 0;
			return true;
		}
		if (player.area == 2 && playercollide(important.get(2))) {
			playSound("Interact.wav", getFreeClip());
//			System.out.println("b");
			player.area = 1;
			player.x = -200;
			player.y = 0;
			return true;
		}
		if (player.area == 1 && playercollide(important.get(3))) {
			playSound("Interact.wav", getFreeClip());
//			System.out.println("c");
			player.area = 3;
			player.x = -1000;
			player.y = 0;
			return true;
		}
		if (player.area == 3 && playercollide(important.get(4))) {
			playSound("Interact.wav", getFreeClip());
//			System.out.println("d");
			player.area = 1;
			player.x = 1000;
			player.y = 0;
			return true;
		}
		if (player.area == 1 && playercollide(important.get(5))) {
			playSound("Interact.wav", getFreeClip());
//			System.out.println("e");
			player.area = 0;
			player.x = 1250;
			player.y = 1000;
			return true;
		}
		if (player.area == 1 && playercollide(important.get(6))) {
			playSound("Interact.wav", getFreeClip());
//			System.out.println("f");
			player.area = 2;
			player.x = 0;
			player.y = 0;
			return true;
		}
		return false;
	}
	
	public void changeText() {
		if (text.done && playercollide(important.get(1)) && Player.cunk == 1 && text.gamestate < 10) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Triston", "This door reminds me of another pixelated world ... maybe 3D", true);
		}
		if (text.done && playercollide(girl) && player.roses > 0 && player.area == 1 && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Chloe", "Thank you for the rose!", true);		
			time += 60 * 1000;
			player.roses--;
		}
		if (text.done && playercollide(girl) && player.roses == 0 && player.area == 1 && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Chloe", "I wish I had a rose right now...", true);
		}
		
		if (text.gamestate == 0 && text.done) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Triston", "Why do I feel so empty? Could it be my heart?", false);
			text.gamestate++;
		}
		if (text.gamestate == 1 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Triston", "Its way too dark...", true);
			text.gamestate++;
		}
		if (text.gamestate == 2 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Triston", "How did I even get here...?", true);
			text.gamestate++;
		}
		if (text.gamestate == 3 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Triston", "Is there a glow?", true);
			text.gamestate++;
		}
		if (text.gamestate == 4 && text.done && playercollide(important.get(0))) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Triston", "W-what's... that?", false);
			text.gamestate++;
			ben.area = 0;
			ben.x = important.get(1).x;
			ben.y = important.get(1).y;
			ben.ogx = important.get(1).x;
			ben.ogy = important.get(1).y;
		}
		if (text.gamestate == 5 && text.done && ben.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "Is that your heart?!", false);
			text.gamestate++;
		}
		if (text.gamestate == 6 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "I haven't seen you around here before?", false);
			text.gamestate++;
		}
		if (text.gamestate == 7 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "You must be one of Aedox's victims...", false);
			text.gamestate++;
		}
		if (text.gamestate == 8 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "You must be cold, let's warm you up!", false);
			text.gamestate++;
		}
		if (text.gamestate == 9 && text.done && ben.done) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "I should tell you about Aedox.", false);
			text.gamestate++;
			player.area = 2;
			ben.area = 2;
			ben.x = 200;
			ben.y = 100;
			player.x = 0;
			player.y = 0;
			music.stop();
			playSound("Menu_Music.wav", music);
		}
		if (text.gamestate == 10 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "Aedox was like you, found himself in this dungeon alone with no one to help him", false);
			text.gamestate++;
		}
		if (text.gamestate == 11 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "so his heart slowly deteriorated so he became heartless", false);
			text.gamestate++;
		}
		if (text.gamestate == 12 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "and started pulling people down to this dungeon", false);
			text.gamestate++;
		}
		if (text.gamestate == 13 && text.done && Player.cunk == 1) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "to deteriorate their hearts and make them feel pain the way he did", true);
			text.gamestate++;
		}
		if (text.gamestate == 14 && text.done && playercollide(ben) && Player.cunk == 1 && player.area == 2) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "I have roses for sale! 2 coins for 1 rose! Talk to me to buy one!", true);
			text.gamestate++;
		}		
		if (text.gamestate == 15 && text.done && playercollide(ben) && Player.cunk == 1 && player.area == 2) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "Buying roses Triston?", false);
			text.gamestate++;
		}
		if (text.gamestate == 16 && text.done && Player.cunk == 1 && player.coins > 1 && player.area == 2) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "Thank you for your purchase!", true);
			player.roses += 1;
			player.coins -= 2;
			text.gamestate--;
		}
		if (text.gamestate == 16 && text.done && Player.cunk == 1 && player.coins < 2 && player.area == 2) {
			playSound("Interact.wav", getFreeClip());
			text.newText("Ben", "Man, 2 coins is cheap. I can't lower it", true);
			text.gamestate--;
		}

	}
}
