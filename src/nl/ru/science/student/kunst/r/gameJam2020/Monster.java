package nl.ru.science.student.kunst.r.gameJam2020;

import java.util.Random;

public class Monster extends GameObject {
	
	private Sprite[] sprites;
	private int activeSprite;
	private int spriteTimer;
	
	private boolean dying;
	private int timer;

	public Monster(double x, double y, World world) {
		super(x, y, world);
		
		x1 = 0;
		y1 = 0;
		x2 = World.TILE_WIDTH;
		y2 = World.TILE_HEIGHT;
		
		Sprite[] sprites = {new Sprite("monster1"), new Sprite("monster2"),
				new Sprite("monster_dying1"), new Sprite("monster_dying2"), new Sprite("monster_dying3"), new Sprite("monster_dying4"), new Sprite("monster_dying5")};
		this.sprites = sprites;
		sprite = sprites[0];
		activeSprite = 0;
		spriteTimer = 0;
	}
	
	@Override
	public void tick() {
		if (dying) {
			timer++;
			if (timer >= 3) {
				activeSprite++;
				timer -= 3;
				if (activeSprite > 6) {
					world.removeNonPlayer(this);
				}
				else {
					sprite = sprites[activeSprite];
				}
			}
		}
		else {
			super.tick();
			spriteTimer++;
			if (spriteTimer >= 60) {
				activeSprite = 1 - activeSprite;
				Random random = new Random();
				spriteTimer -= 50 + random.nextInt(21);
				sprite = sprites[activeSprite];
			}
		}
	}
	
	public void kill() {
		dying = true;
		activeSprite = 2;
		sprite = sprites[activeSprite];
	}

	public boolean isDying() {
		return dying;
	}

}
