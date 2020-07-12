package nl.ru.science.student.kunst.r.gameJam2020;

public class LetterTile extends Tile {

	public LetterTile(char letter) {
		sprite = new Sprite("letter"+letter);
	}

	@Override
	public boolean isBarrier() {
		return false;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isTarget() {
		// TODO Auto-generated method stub
		return false;
	}

}
