package nl.ru.science.student.kunst.r.gameJam2020;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class World {

	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;
	
	Tile[][] map;
	ArrayList<GameObject> nonPlayers;
	ArrayList<GameObject> toAdd;
	ArrayList<GameObject> toRemove;
	Player player;
	
	private Handler handler;
	
	private HUD hud;
	
	private boolean bottomMessage;
	private String message;
	private int messageCounter;
	
	private int x;
	private int y;
	
	private Map<Integer, Integer> availableControls;
	private Map<Integer, Integer> initialControls;
	private Map<Integer, Integer> controlMultipliers;
	
	private int level;
	
	private boolean canRestart;
	
	public World(Handler handler, int level) {
		this.handler = handler;
		hud = new HUD();
		nonPlayers = new ArrayList<>();
		toAdd = new ArrayList<>();
		toRemove = new ArrayList<>();
		try {
			loadLevelFromFile(level);
		}
		catch (InvalidLevelFileException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.level = level;
	}
	
	private void loadLevelFromFile(int level) throws InvalidLevelFileException {
		File file = new File("res" + File.separator + "level"+level+".txt");
		Scanner reader;
		try {
			reader = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			throw new InvalidLevelFileException(level);
		}
		int width = reader.nextInt();
		int height = reader.nextInt();
		reader.nextLine();
		map = new Tile[width][height];
		for (int j = 0; j < height; j++) {
			String row = reader.nextLine();
			for (int i = 0; i < width; i++) {
				if (Character.isLowerCase(row.charAt(i)) || Character.isDigit(row.charAt(i)) || row.charAt(i)=='!') {
					map[i][j] = new LetterTile(row.charAt(i));
				}
				else {
					switch (row.charAt(i)) {
					case 'W':
						map[i][j] = new WallTile();
						break;
					case 'M':
						nonPlayers.add(new Monster(i * TILE_WIDTH, j * TILE_HEIGHT, this));
					case 'F':
						map[i][j] = new FloorTile();
						break;
					case 'T':
						map[i][j] = new GoalTile();
						break;
					default:
						reader.close();
						throw new InvalidLevelFileException(level);
					}
				}
			}
		}
		
		int playerX = TILE_WIDTH * reader.nextInt();
		int playerY = TILE_HEIGHT * reader.nextInt();
		
		player = new Player(playerX, playerY, this);
		
		reader.nextLine();
		
		message = reader.nextLine();
		while (message.endsWith("<...>")) {
			message += reader.nextLine();
		}
		
		bottomMessage = !"<NoMessage>".equals(message);
		
		availableControls = new HashMap<>();
		initialControls = new HashMap<>();
		controlMultipliers = new HashMap<>();
		while (reader.hasNext()) {
			int keyCode;
			try {
				keyCode = KeyEvent.class.getField("VK_"+reader.next()).getInt(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				reader.close();
				throw new InvalidLevelFileException(level);
			}
			int amount = reader.nextInt();
			
			availableControls.put(keyCode, amount);
			if (keyCode != KeyEvent.VK_CONTROL) {
				initialControls.put(keyCode, amount);	
			}
			int multiplier = reader.nextInt();
			controlMultipliers.put(keyCode, multiplier);
		}
		
		reader.close();
		
		updateWindowLocation();
	}

	private void updateWindowLocation() {
		int mapWidth = TILE_WIDTH * map.length;
		int mapHeight = TILE_HEIGHT * map[0].length;
		
		boolean tooNarrow = mapWidth < Game.WIDTH;
		boolean tooLow = mapHeight < Game.HEIGHT;
		
		if (tooNarrow) {
			x = (mapWidth - Game.WIDTH)/2;
		}
		else {
			x = Math.min(Math.max(0, (int) Math.round(player.getX()+TILE_WIDTH/2-Game.WIDTH/2)), mapWidth - Game.WIDTH);
		}
		
		if (tooLow) {
			y = (mapHeight - Game.HEIGHT)/2;
		}
		else {
			y = Math.min(Math.max(0, (int) Math.round(player.getY()+TILE_HEIGHT/2-Game.HEIGHT/2)), mapHeight - Game.HEIGHT);
		}
	}

	public void tick() {
		messageCounter++;
		for (GameObject object : nonPlayers) {
			object.tick();
		}
		player.tick();
		updateWindowLocation();
		
		for (GameObject object : nonPlayers) {
			if (player.getBounds().intersects(object.getBounds())) {
				if (object.getClass().equals(Monster.class)) {
					Monster monster = (Monster) object;
					if (!monster.isDying()) {
						Thread restart = new Thread(new Restarter("You hit a deadly monster.", handler));
						restart.start();
					}
				}
			}
			for (GameObject secondObject : nonPlayers) {
				if (secondObject.getBounds().intersects(object.getBounds())) {
					if (object.getClass().equals(Monster.class) && secondObject.getClass().equals(Projectile.class)) {
						Monster monster = (Monster) object;
						monster.kill();
					}
				}
			}
		}
		
		nonPlayers.removeAll(toRemove);
		toRemove.clear();
		nonPlayers.addAll(toAdd);
		toAdd.clear();
	}
	
	public void render(Graphics g) {
		int xOffset = x % TILE_WIDTH;
		int yOffset = y % TILE_HEIGHT;
		for (int i = x/TILE_WIDTH; i < (x + Game.WIDTH)/TILE_WIDTH + 1; i++) {
			for (int j = y/TILE_HEIGHT; j < (y + Game.HEIGHT)/TILE_HEIGHT + 1; j++) {
				try {
					map[i][j].render(g, (i-x/TILE_WIDTH)*TILE_WIDTH - xOffset, (j-y/TILE_HEIGHT)*TILE_HEIGHT - yOffset);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}
		
		for (GameObject object : nonPlayers) {
			object.render(g, x, y);
		}
		
		player.render(g, x, y);
		
		if (bottomMessage) {
			String[] message = this.message.split("<...>");
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			FontMetrics fm = g.getFontMetrics();
			int height = message.length * fm.getHeight() + 100;
			int width = 0;
			for (String line : message) {
				if (fm.stringWidth(line) > width) {
					width = fm.stringWidth(line);
				}
			}
			width += 100;
			
			int x = (Game.WIDTH - width)/2;
			int y = Game.HEIGHT - 25 - height;
			
			g.setColor(new Color(0, 0, 0, 127));
			g.fillRoundRect(x, y, width, height, 20, 20);
			g.setColor(Color.YELLOW);
			g.drawRoundRect(x, y, width, height, 20, 20);
			g.setColor(Color.WHITE);
			
			int charsPrinted = 0;
			for (int i = 0; i < message.length; i++) {
				String line = message[i].replaceAll("\\.", "."+ new String(new char[30]).replace('\0',(char) 0x200B));
				if (charsPrinted + line.length() <= messageCounter) {
					g.drawString(line, x + 50, y + 50 + fm.getHeight() * i + fm.getAscent());
					charsPrinted += line.length();
				}
				else {
					g.drawString(line.substring(0, messageCounter - charsPrinted), x + 50, y + 50 + fm.getHeight() * i + fm.getAscent());
					break;
				}
			}
		}
		
		hud.render(g, availableControls, initialControls);
	}

	public void keyPressed(int key) {
		if (availableControls.containsKey(key) && availableControls.get(key)!=0) {
			canRestart = true;
			if (player.keyPressed(key, controlMultipliers.get(key))) availableControls.put(key, availableControls.get(key) - 1);
		}
		if (availableControls.get(KeyEvent.VK_CONTROL) == 0) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (canRestart) {
				canRestart = false;
				handler.restartLevel("You ran out of control!", true);
			}
		}
	}

	public void keyReleased(int key) {
		player.keyReleased(key);
	}
	
	public Tile getTile(int x, int y) {
		return map[x][y];
	}

	public void nextLevel() {
		canRestart = false;
		if (level == 9) {
			handler.goToLevel((int) Math.round(player.getX()/TILE_WIDTH/2));
		}
		else {
			handler.nextLevel();
		}
	}
	
	public void resetControls() {
		for (int key : initialControls.keySet()) {
			availableControls.replace(key, initialControls.get(key));
		}
	}
	
	public void addNonPlayer(GameObject object) {
		toAdd.add(object);
	}
	
	public void removeNonPlayer(GameObject object) {
		toRemove.add(object);
	}
	
}
