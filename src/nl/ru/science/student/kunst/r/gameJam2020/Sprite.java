package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	public static final int PIXEL_SIZE = 4;
	
	protected BufferedImage image;
	
	public Sprite(BufferedImage image) {
		this.image = image;
	}
	
	public Sprite(String file) {
		try {
			image = ImageIO.read(new File("res" + File.separator + "sprites" + File.separator + file +".png"));
		}
		catch (IOException e) {
			try {
				image = ImageIO.read(new File("res" + File.separator + "sprites" + File.separator + "null_sprite.png"));
			}
			catch (IOException f) {
				f.printStackTrace();
				image = new BufferedImage(World.TILE_WIDTH/PIXEL_SIZE, World.TILE_HEIGHT/PIXEL_SIZE, BufferedImage.TYPE_3BYTE_BGR);
			}
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, image.getWidth() * PIXEL_SIZE, image.getHeight() * PIXEL_SIZE, null);
	}
	
}
