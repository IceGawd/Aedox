

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	private Player play = null;
	
	public Keyboard(Player p) {
		super();
		play = p;
	}
	public void keyTyped(KeyEvent e) {
//		System.out.println("What");
	}

	public void keyPressed(KeyEvent e) {
//		System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
		play.key(useKeys(e));
	}

	public void keyReleased(KeyEvent e) {
//		System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
		play.off(remKeys(e));
	}
	
	public int remKeys(KeyEvent e) {
		String s = KeyEvent.getKeyText(e.getKeyCode());
		int ret = -1;
		if ((s.equals("W")) || (s.equals("Up"))) {
			ret = 1;
		}
		if ((s.equals("S")) || (s.equals("Down"))) {
			ret = 1;
		}
		if ((s.equals("D")) || (s.equals("Right"))) {
			ret = 0;
		}
		if ((s.equals("A")) || (s.equals("Left"))) {
			ret = 0;
		}
		if (s.equals("Space")) {
			ret = 2;
		}
		return ret;
	}
	public int[] useKeys(KeyEvent e) {
		String s = KeyEvent.getKeyText(e.getKeyCode());
		int[] array = {0, 0, 0, 0};
		if ((s.equals("W")) || (s.equals("Up"))) {
			array[1] = -1;
		}
		if ((s.equals("S")) || (s.equals("Down"))) {
			array[1] = 1;
		}
		if ((s.equals("D")) || (s.equals("Right"))) {
			array[0] = 1;
		}
		if ((s.equals("A")) || (s.equals("Left"))) {
			array[0] = -1;
		}
		if (s.equals("Space")) {
			array[2] = 1;
		}
		if (s.equals("H")) {
			array[3] = 1;
		}
		return array;
	}
}
