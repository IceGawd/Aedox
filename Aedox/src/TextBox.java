import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextBox {
	public Image textback;
	public boolean move;
	public String text = "";
	public static String name = "";
	public String show = "";
	public int frames = 0;
	public static boolean exist = false;
	//TODO CHANGE BACK TO 6
	public static final int FRAMEWAIT = 3;
	public int chars = 0;
	public boolean done = false;
	// TODO CHANGE BACK TO 0
	public int gamestate = 0;
	
	public TextBox() {
        try {
        	textback = ImageIO.read(this.getClass().getClassLoader().getResource("text.png"));
        	textback = this.textback.getScaledInstance(Runner.WINX, Runner.WINX * 5 / 16, 1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void draw(Graphics g) {
		int FRAMEWAIT = TextBox.FRAMEWAIT;
		if (Player.lead == 1) {
			// TODO make it TextBox.FRAMEWAIT / 2
			FRAMEWAIT = 0;
		}
		frames++;
		if (frames > FRAMEWAIT) {
			frames = 0;
			if (chars < text.length()) {
				if (text.charAt(chars) == ' ') {
					frames = -FRAMEWAIT;
				}
				if (text.charAt(chars) == '.' || text.charAt(chars) == '?') {
					frames = -2 * FRAMEWAIT;
				}
				chars++;
				show = text.substring(0, chars);
			}
			else {
				done = true;
			}
		}
		if (exist) {
			g.drawImage(textback, 0, Runner.WINY - 150, null);
			g.setColor(Color.PINK);
			g.setFont(Runner.second);
			g.drawString(name, 0, Runner.WINY - 170);
			g.setFont(Runner.main);
			g.drawString(show, 50, Runner.WINY - 60);
		}
	}
	
	public void newText(String n, String t, boolean m) {
		Player.cunk = 0;
		exist = true;
		name = n;
		text = t;
		move = m;
		chars = 0;
		frames = 0;
		done = false;
	}
}
