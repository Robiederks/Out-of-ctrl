package nl.ru.science.student.kunst.r.gameJam2020;

public class Restarter implements Runnable {

	private String message;
	private Handler handler;
	
	public Restarter(String message, Handler handler) {
		this.message = message;
		this.handler = handler;
	}

	@Override
	public void run() {
		handler.restartLevel(message, true);
	}

}
