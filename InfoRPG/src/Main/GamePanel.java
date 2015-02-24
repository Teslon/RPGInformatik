package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel 
	implements Runnable, KeyListener{
	
	// Dimensionen
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 1;
	
	
	
	// Hauptthread und Loopint.
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	
	// Implementierung von BuffImg.
	private BufferedImage image;
	private Graphics2D g;
	
	
	// GameStaes Implemtierung
	private GameStateManager gsm;
	
	// Dimensionen und Thread handling
	public GamePanel() {
		super();
		setPreferredSize(
			new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	
	// Init Methode
	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	
	// Start Methode
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// Game Loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	// Update Methode
	private void update() {
		gsm.update();
	}
	
	
	// Draw Methode
	private void draw() {
		gsm.draw(g);
	}
	
	
	// DrawToScreen
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	
	
	// Tasten Anschlag
	public void keyTyped(KeyEvent key) {}
	// Tasten Geschlagen
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	// Tasten Erschlagen
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

}
















