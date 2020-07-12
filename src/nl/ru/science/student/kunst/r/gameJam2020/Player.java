package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.event.KeyEvent;

public class Player extends GameObject {
	
	private Sprite[] sprites;
	private int activeSpriteGroup;
	private int activeSprite;
	private int spriteTimer;
	private int spriteDelay;
	
	private boolean acceptKeyInput;
	
	private int movementCooldown;
	
	private int facing;

	public Player(double x, double y, World world) {
		super(x, y, world);
		x1 = 3*World.TILE_WIDTH/16;
		y1 = 0;
		x2 = 13*World.TILE_WIDTH/16;
		y2 = World.TILE_WIDTH;
		
		activeSpriteGroup = 0;
		activeSprite = 0;
		Sprite[] sprites = {new Sprite("player_stand"), new Sprite("player_stand"),
							new Sprite("player_walkright1"), new Sprite("player_walkright2"),
							new Sprite("player_walkleft1"), new Sprite("player_walkleft2"),
							new Sprite("player_walkvertical1"), new Sprite("player_walkvertical2")};
		this.sprites = sprites;
		
		acceptKeyInput = true;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (activeSpriteGroup != (vx==0 ? (vy==0 ? 0 : 3) : (vx > 0 ? 1 : 2))) {
			spriteDelay++;
			if (spriteDelay >= 2) {
				spriteDelay = 0;
				activeSpriteGroup = vx==0 ? (vy==0 ? 0 : 3) : (vx > 0 ? 1 : 2);
			}
		}
		else {
			spriteDelay = 0;
		}
		
		movementCooldown--;
		if (movementCooldown == 0) {
			vx = 0;
			vy = 0;
			acceptKeyInput = true;
		}
		
		spriteTimer++;
		if (spriteTimer >= 20) {
			spriteTimer -= 20;
			activeSprite++;
			activeSprite %= 2;
		}
		
		sprite = sprites[activeSpriteGroup * 2 + activeSprite];
	}

	public boolean keyPressed(int key, int multiplier) {
		if (!acceptKeyInput) {
			return false;
		}
		switch(key) {
		case KeyEvent.VK_CONTROL:
			if (world.getTile((int) Math.round(x + x1)/World.TILE_WIDTH,
					(int) Math.round(y + y1)/World.TILE_HEIGHT).isTarget() ||
					world.getTile((int) Math.round(x + x1)/World.TILE_WIDTH,
					(int) Math.round(y + y2 - 1)/World.TILE_HEIGHT).isTarget() ||
					world.getTile((int) Math.round(x + x2 - 1)/World.TILE_WIDTH,
					(int) Math.round(y + y1)/World.TILE_HEIGHT).isTarget() ||
					world.getTile((int) Math.round(x + x2 - 1)/World.TILE_WIDTH,
					(int) Math.round(y + y2 - 1)/World.TILE_HEIGHT).isTarget()) {
				world.nextLevel();
			}
			else {
				world.resetControls();
			}
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			acceptKeyInput = false;
			vy = -4 * multiplier;
			movementCooldown = 16;
			activeSpriteGroup = 3;
			facing = 1;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			acceptKeyInput = false;
			vx = -4 * multiplier;
			movementCooldown = 16;
			activeSpriteGroup = 2;
			facing = 2;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			acceptKeyInput = false;
			vy = 4 * multiplier;
			movementCooldown = 16;
			activeSpriteGroup = 3;
			facing = 3;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			acceptKeyInput = false;
			vx = 4 * multiplier;
			movementCooldown = 16;
			activeSpriteGroup = 1;
			facing = 0;
			break;
		case KeyEvent.VK_SPACE:
			switch (facing) {
			case 0:
				world.addNonPlayer(new Projectile(x, y, 8, 0, multiplier * 64, world));
				break;
			case 1:
				world.addNonPlayer(new Projectile(x, y, 0, -8, multiplier * 64, world));
				break;
			case 2:
				world.addNonPlayer(new Projectile(x, y, -8, 0, multiplier * 64, world));
				break;
			case 3:
				world.addNonPlayer(new Projectile(x, y, 0, 8, multiplier * 64, world));
				break;
			}
			break;
		default:
			return false;
		}
		return true;
	}

	public void keyReleased(int key) {
		/*
		switch(key) {
		case KeyEvent.VK_UP:
			vy=0;
			break;
		case KeyEvent.VK_LEFT:
			vx=0;
			break;
		case KeyEvent.VK_DOWN:
			vy=0;
			break;
		case KeyEvent.VK_RIGHT:
			vx=0;
			break;
		}
		activeSpriteGroup = vx==0 ? (vy==0 ? 0 : 3) : (vx > 0 ? 1 : 2);
		*/
	}

}
