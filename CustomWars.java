import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class CustomWars extends Canvas implements Runnable{
	private static final long serialVersionUID = 7506117823203158228L;
	
	private boolean RUNNING;
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	public static String NAME = "CustomWars";
	
	public ArrayList<Entity> ENTITY = new ArrayList<>();
	
	public static CustomWars GAME = new CustomWars();
	
	public static void main(String[] args){
		GAME.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JFrame frame = new JFrame(CustomWars.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(GAME, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		GAME.start();
	}
	
	public void start(){
		RUNNING = true;
		new Thread(this).start();
	}

	@Override
	public void run(){
		long delta_render = 0;
		long delta_update = 0;
		
		init();
		
		while(RUNNING){
			if(System.currentTimeMillis() - delta_render >= 50){
				delta_render = System.currentTimeMillis();
				update();
			}
			if(System.currentTimeMillis() - delta_update >= 20){
				delta_update = System.currentTimeMillis();
				render();
			}
		}
	}
	
	public void init(){
		for(int i = 0; i <= 30; i++){
			ENTITY.add(new Entity(new Random().nextInt(800) + 1, new Random().nextInt(600) + 1, 6, 3, Color.GREEN));
		}
		for(int i = 0; i <= 30; i++){
			ENTITY.add(new Entity(new Random().nextInt(800) + 1, new Random().nextInt(600) + 1, 6, 3, Color.ORANGE));
		}
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(Entity e : ENTITY){
			if(e.TARGET != null){
				if(Point2D.distance(e.X, e.Y, e.TARGET.X, e.TARGET.Y) <= e.RADIUS_ATTACK){
					if(e.TARGET.HP > 0){
						g.setColor(e.COLOR_DRAW);
						g.draw(new Line2D.Double(e.X, e.Y, e.TARGET.X, e.TARGET.Y));
					}
				}
			}
		}
		for(Entity e : ENTITY){
			e.render(g);
		}
		int green = 0;
		int orange = 0;
		for(Entity e : ENTITY){
			if(e.FACTION == Color.GREEN){
				green++;
			}else orange++;
		}
		g.setColor(Color.WHITE);
		g.drawString("Green = " + green, 700, 18);
		g.drawString("Orange = " + orange, 693, 29);
		g.dispose();
		bs.show();
		if(green == 0 || orange == 0) RUNNING = false;
	}
	
	public void update(){
		for(Entity e : ENTITY){
			e.update();
			if(e.TARGET != null){
				if(Point2D.distance(e.X, e.Y, e.TARGET.X, e.TARGET.Y) <= e.RADIUS_ATTACK){
					if(e.TARGET.HP > 0){
						e.TARGET.HP -= e.DAMAGE;
						if(e.TARGET.HP <= 0){
							e.DIAMETER++;
							e.RADIUS_ATTACK++;
							e.MAX_HP += 250;
							e.HP += 250;
						}
					}
				}
			}
		}
		for(int i = ENTITY.size() - 1; i >= 0; i--){
			if(ENTITY.get(i).HP <= 0){
				for(Entity e : ENTITY) if(e.TARGET == ENTITY.get(i)) e.TARGET = null;
				ENTITY.remove(i);
			}
		}
	}
}
