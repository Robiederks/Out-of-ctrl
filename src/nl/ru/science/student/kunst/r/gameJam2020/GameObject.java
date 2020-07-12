package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	
	//Borders inside tile
	protected int x1;
	protected int y1;
	protected int x2;
	protected int y2;
	
	protected Sprite sprite = new Sprite("");
	
	protected World world;
	
	public GameObject(double x, double y, World world) {
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		this.world = world;
	}
	
	public void tick() {
		x += vx;
		if (world.getTile((int) Math.round(x + x1)/World.TILE_WIDTH,
				(int) Math.round(y + y1)/World.TILE_HEIGHT).isBarrier() ||
				world.getTile((int) Math.round(x + x1)/World.TILE_WIDTH,
				(int) Math.round(y + y2 - 1)/World.TILE_HEIGHT).isBarrier()) {
			x = (x + World.TILE_WIDTH) - (x % World.TILE_WIDTH) - x1;
		}
		if (world.getTile((int) Math.round(x + x2 - 1)/World.TILE_WIDTH,
				(int) Math.round(y + y1)/World.TILE_HEIGHT).isBarrier() ||
				world.getTile((int) Math.round(x + x2 - 1)/World.TILE_WIDTH,
				(int) Math.round(y + y2 - 1)/World.TILE_HEIGHT).isBarrier()) {
			x -= x % World.TILE_WIDTH - World.TILE_WIDTH + x2;
		}
		y += vy;
		if (world.getTile((int) Math.round(x + x1)/World.TILE_WIDTH,
				(int) Math.round(y + y1)/World.TILE_HEIGHT).isBarrier() ||
				world.getTile((int) Math.round(x + x2 - 1)/World.TILE_WIDTH,
				(int) Math.round(y + y1)/World.TILE_HEIGHT).isBarrier()) {
			y = (y + World.TILE_HEIGHT) - (y % World.TILE_HEIGHT) - y1;
		}
		if (world.getTile((int) Math.round(x + x1)/World.TILE_WIDTH,
				(int) Math.round(y + y2 - 1)/World.TILE_HEIGHT).isBarrier() ||
				world.getTile((int) Math.round(x + x2 - 1)/World.TILE_WIDTH,
				(int) Math.round(y + y2 - 1)/World.TILE_HEIGHT).isBarrier()) {
			y -= y % World.TILE_HEIGHT - World.TILE_HEIGHT + y2;
		}
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		sprite.draw(g, (int) Math.round(x) - xOffset + x1, (int) Math.round(y) - yOffset + y1);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) Math.round(x) + x1, (int) Math.round(y) + y1, x2 - x1, y2 - y1);
	}

}
