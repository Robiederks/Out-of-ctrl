package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Graphics;

public class DetailedSprite extends Sprite {
	
	private int pixelSize;

	public DetailedSprite(String file, int pixelSize) {
		super(file);
		this.pixelSize = pixelSize;
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, image.getWidth() * pixelSize, image.getHeight() * pixelSize, null);
	}

}
