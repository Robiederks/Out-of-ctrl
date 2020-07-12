package nl.ru.science.student.kunst.r.gameJam2020;

public class Projectile extends GameObject {
	
	private double life;

	public Projectile(double x, double y, double vx, double vy, double life, World world) {
		super(x, y, world);
		if (Math.abs(vx) > Math.abs(vy)) {
			if (vx > 0) {
				sprite = new Sprite("bullet_right");
			}
			else {
				sprite = new Sprite("bullet_left");
			}
			
			x1 = 5*World.TILE_WIDTH/16;
			y1 = 3*World.TILE_HEIGHT/8;
			x2 = 11*World.TILE_WIDTH/16;
			y2 = 5*World.TILE_HEIGHT/8;
		}
		else {
			if (vx > 0) {
				sprite = new Sprite("bullet_down");
			}
			else {
				sprite = new Sprite("bullet_up");
			}
			
			x1 = 3*World.TILE_WIDTH/8;
			y1 = 5*World.TILE_HEIGHT/16;
			x2 = 5*World.TILE_WIDTH/8;
			y2 = 11*World.TILE_HEIGHT/16;
		}
		this.vx = vx;
		this.vy = vy;
		
		this.life = life;
	}
	
	@Override
	public void tick() {
		super.tick();
		life -= Math.sqrt(vx*vx+vy*vy);
		if (life <= 0) {
			world.removeNonPlayer(this);
		}
		
		
	}

}
