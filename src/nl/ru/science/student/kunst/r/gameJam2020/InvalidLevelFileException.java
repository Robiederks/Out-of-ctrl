package nl.ru.science.student.kunst.r.gameJam2020;

public class InvalidLevelFileException extends Exception {

	public InvalidLevelFileException(int level) {
		super("Invalid level file format for level " + level);
	}

	private static final long serialVersionUID = -3271816800413994520L;
	
	

}
