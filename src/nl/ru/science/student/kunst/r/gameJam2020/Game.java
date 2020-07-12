package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 9113002658121776445L;

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;
	
	private Thread thread;
	private State state;
	
	private Handler handler;
	
	private SoundControl soundControl;
	
	private ArrayList<SoundPlayer> sounds;
	private boolean muted;
	
	private enum State {
		GAME
	}
	
	public Game() {
		state = State.GAME;
		
		handler = new Handler();
		
		KeyInput keyInput = new KeyInput(this);
		addKeyListener(keyInput);
		
		sounds = new ArrayList<>();
		
		soundControl = new SoundControl(this);
		addMouseListener(soundControl);
		
		new Window(WIDTH, HEIGHT, "Out of ctrl", this);
		this.requestFocus();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		SoundPlayer music = new SoundPlayer("music.wav", true);
		music.playSound();
		sounds.add(music);
	}
	
	public void run() {
		double tps = 60;
		long lastTick = System.nanoTime();
		double tickLength = 1e9/tps;
		int frames = 0;
		long startTime = System.currentTimeMillis();
		while (true) {
			long now = System.nanoTime();
			long elapsedTime = now - lastTick;
			while (elapsedTime >= tickLength) {
				tick();
				lastTick += tickLength;
				elapsedTime = now - lastTick;
			}
			render();
			frames++;
			long currentTime = System.currentTimeMillis();
			long elapsed = currentTime - startTime;
			while (elapsed >= 1000) {
				System.out.println(frames + " FPS");
				frames = 0;
				startTime += 1000;
				elapsed = currentTime - startTime;
			}
		}
	}

	private void tick() {
		switch (state) {
		case GAME:
			handler.tick();
			break;
		default:
			break;
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		switch (state) {
		case GAME:
			handler.render(g);
			break;
		default:
			break;
		}
		
		soundControl.render(g, muted);
		
		g.dispose();
		bs.show();
	}

	public void keyPressed(int key) {
		handler.keyPressed(key);
	}

	public void keyReleased(int key) {
		handler.keyReleased(key);
	}
	
	public void toggleMute() {
		muted = !muted;
		for (SoundPlayer sound : sounds) {
			sound.setMuted(muted);
		}
	}
	
}
