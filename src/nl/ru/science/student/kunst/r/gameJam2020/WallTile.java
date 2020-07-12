package nl.ru.science.student.kunst.r.gameJam2020;

public class WallTile extends Tile {

	public WallTile() {
		sprite = new Sprite("wall");
	}
	
	@Override
	public void tick() {
	}

	@Override
	public boolean isBarrier() {
		return true;
	}

	@Override
	public boolean isTarget() {
		return false;
	}

}
