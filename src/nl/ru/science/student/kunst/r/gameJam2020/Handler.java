package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Handler {
	
	public static final int NUMBER_OF_LEVELS = 9;
	
	private int level;
	
	private World world;
	private World nextWorld;
	
	private boolean loadingNewLevel;
	private boolean newLevelReady;
	private String loadingPrompt;
	
	private int promptVisible = 0;
	
	public Handler() {
		level = 1;
		world = new World(this, level);
	}
	
	public void tick() {
		if (!loadingNewLevel) {
			world.tick();
		}
		else if (newLevelReady && promptVisible <= 0) {
			world = nextWorld;
			loadingNewLevel = false;
			newLevelReady = false;
		}
		else {
			promptVisible--;
		}
	}
	
	public void render(Graphics g) {
		world.render(g);
		if (loadingNewLevel) {
			g.setColor(new Color(0,0,0,127));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			FontMetrics fm = g.getFontMetrics();
			g.drawString(loadingPrompt, (Game.WIDTH - fm.stringWidth(loadingPrompt))/2, (Game.HEIGHT - fm.getHeight())/2 + fm.getAscent());
		}
	}

	public void keyPressed(int key) {
		if (!loadingNewLevel) {
			if (key == KeyEvent.VK_R) {
				if (level > 2) {
					restartLevel("Restarting level...", false);
				}
			}
			else {
				world.keyPressed(key);
			}
		}
	}

	public void keyReleased(int key) {
		if (!loadingNewLevel) {
			world.keyReleased(key);
		}
	}

	public void nextLevel() {
		loadingPrompt = "Loading new level...";
		promptVisible = 0;
		loadingNewLevel = true;
		if (level == NUMBER_OF_LEVELS) {
			level = 1;
		}
		else {
			level++;
		}
		nextWorld = new World(this, level);
		newLevelReady = true;
	}
	
	public void restartLevel(String prompt, boolean forced) {
		loadingPrompt = prompt;
		if (forced) {
			promptVisible = 60;
		}
		loadingNewLevel = true;
		nextWorld = new World(this, level);
		newLevelReady = true;
	}

	public void goToLevel(int newLevel) {
		loadingPrompt = "Loading new level...";
		promptVisible = 0;
		loadingNewLevel = true;
		level = newLevel;
		nextWorld = new World(this, level);
		newLevelReady = true;
	}

}
