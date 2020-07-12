package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Graphics;

public abstract class Tile {
	
	protected Sprite sprite = new Sprite("");
	
	public abstract boolean isBarrier();
	
	public abstract void tick();
	
	public void render(Graphics g, int x, int y) {
		sprite.draw(g, x, y);
	}

	public abstract boolean isTarget();

}
