package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Map;

public class HUD {
	
	public HUD() {
		
	}
	
	public void render(Graphics g, Map<Integer, Integer> availableControls, Map<Integer, Integer> initialControls) {
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		
		g.setColor(new Color(255, 255, 255));
		
		g.drawString(KeyEvent.getKeyText(KeyEvent.VK_CONTROL), 15, 30);
		FontMetrics fm = g.getFontMetrics();
		String amount;
		if (availableControls.get(KeyEvent.VK_CONTROL) < 0) {
			amount = "" + (char) 0x221E;
		}
		else {
			amount = availableControls.get(KeyEvent.VK_CONTROL).toString();
		}
		g.drawString(amount, 110 - fm.stringWidth(amount), 30);
		
		int j = 1;
		for (int i = 0; i < availableControls.size(); i++) {
			g.setColor(new Color(255, 255, 255));
			int key = (int) availableControls.keySet().toArray()[i];
			if (key == KeyEvent.VK_CONTROL) {
				j = 0;
				continue;
			}
			g.drawString(KeyEvent.getKeyText(key), 15, 30 + 30 * (i+j));
			g.drawString(availableControls.get(key).toString(), 110 - fm.stringWidth(availableControls.get(key).toString()), 30 + 30 * (i+j));
			g.setColor(new Color(0, 255, 0, 200));
			g.fillRect(120, 30 + 30 * (i+j) - fm.getAscent(), 100 * availableControls.get(key) / initialControls.get(key), fm.getHeight());
		}
		
	}

}
