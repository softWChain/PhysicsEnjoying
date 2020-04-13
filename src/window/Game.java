package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



public class Game extends Canvas implements Runnable,MouseMotionListener,MouseListener{
	
	public static int WIDTH = 600;
	public static int HEIGHT = 500;
	
	private Window window;
	private boolean running = false;
	private Thread thread;
	private float x = 300,y =25;
	private float velX,velY;
	private int width = 30,height =30;
	private double gravity = 0.98;
	private double energyloss = .65;
	private double dt = .3;
	
	
	
	public Game(){
		setFocusable(true);
		window = new Window(WIDTH,HEIGHT,this);
		
	}
	
	public void init(){
		
		//x=250;
		//y=250;
		velX=6;
		//velY=6;
		
		

		addMouseListener(this);
		addMouseMotionListener(this);
	
	}
	
	public void tick(){
		
		x +=velX;
		y +=velY;
		
		if(x<0 && velX<0) velX = (float) (-velX -2);
		if(x>WIDTH - width - 1&& velX>0) velX = (float) (-velX +2); 
		if(y>HEIGHT - height -1){
			y = HEIGHT - height;
			velY *=energyloss; 
			velY = -velY;
		}else{
			//velocity formul
			velY += gravity*dt;
			//position formul
			y += velY*dt + 0.5*gravity*dt*dt;		
		}
	
	}
	public void render(){
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval((int) x, (int) y, width, height);
		

		

		
		bs.show();
		g.dispose();
		
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
	
	
		
		
	}
	


	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}
	

	
	public void run(){
		init();
		
		int FPS = 60;
		double targetFPS = 1000000000 / FPS;
		double delta = 0;
		
		long lastTime = System.nanoTime();
		long now;
		long timer = System.currentTimeMillis();
		
		int ticks=0;
		int updates = 0;
		
		while(running){
			
			now = System.nanoTime();
			delta +=(now - lastTime ) / targetFPS;
			lastTime = now;
			
			if(delta>=1){
				tick();
				ticks++;
				delta--;
			}
			render();
			updates++;
			
			if(System.currentTimeMillis() - timer >= 1000){
				timer += 1000;
				System.out.println("FPS : " + ticks + "  - UPDATES : " + updates);
				ticks = 0;
				updates = 0;
			}
			
			
		}
		
		stop();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this,"ThreadGame");
		thread.start();
		
	}
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String args[]){
		new Game().start();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	

}
