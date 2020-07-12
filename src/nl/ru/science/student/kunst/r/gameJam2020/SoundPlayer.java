package nl.ru.science.student.kunst.r.gameJam2020;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {

	private String fileName;
	private boolean loop;
	
	private Clip clip;
	
	public SoundPlayer(String fileName, boolean loop) {
		this.fileName = fileName;
		this.loop = loop;
	}
	
	public void playSound() {
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(new File("res" + File.separator + "sounds" + File.separator + fileName));
			clip = AudioSystem.getClip();
			clip.open(in);
			if (loop) {
				((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15);
				clip.loop(Integer.MAX_VALUE);
			}
			else {
				clip.start();
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void setMuted(boolean muted) {
		BooleanControl mute = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		mute.setValue(muted);
	}

}
