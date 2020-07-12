package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Graphics;

public class GoalTile extends Tile {

	public GoalTile() {
		sprite = new Sprite("goal");
	}
	
	@Override
	public boolean isBarrier() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isTarget() {
		return true;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g, int x, int y) {
		sprite.draw(g, x, y);
	}

}
