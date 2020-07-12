package nl.ru.science.student.kunst.r.gameJam2020;

public class FloorTile extends Tile {

	public FloorTile() {
		sprite = new Sprite("floor");
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBarrier() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTarget() {
		// TODO Auto-generated method stub
		return false;
	}

}
