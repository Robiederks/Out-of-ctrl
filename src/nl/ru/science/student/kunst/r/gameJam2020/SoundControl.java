package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SoundControl extends MouseAdapter{

	private Game game;
	private DetailedSprite mutedSprite;
	private DetailedSprite unmutedSprite;
	
	public SoundControl(Game game) {
		this.game = game;
		mutedSprite = new DetailedSprite("music_off", 2);
		unmutedSprite = new DetailedSprite("music_on", 2);
	}
	
	public void render(Graphics g, boolean muted) {
		if (muted) {
			mutedSprite.draw(g, Game.WIDTH - 32, Game.HEIGHT - 32);
		}
		else {
			unmutedSprite.draw(g, Game.WIDTH - 32, Game.HEIGHT - 32);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() >= Game.WIDTH - 32 && e.getY() >= Game.HEIGHT - 32) {
			game.toggleMute();
		}
	}

}
