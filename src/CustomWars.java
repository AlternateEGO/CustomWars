import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class CustomWars extends Canvas implements Runnable{
	private static final long serialVersionUID = 7506117823203158228L;
	
	boolean RUNNING;
	
	ArrayList<Entity> ENTITY = new ArrayList<>();
	
	static CustomWars GAME = new CustomWars();

	private Render RENDER = new Render(this);
    private Update UPDATE = new Update(this);
    
    private static String NAME = "CustomWars";
    
    static int WIDTH = 800;
    static int HEIGHT = 600;

    int FACTION_GREEN = 100;
    int FACTION_ORANGE = 100;
    
    static float DEFAULT_MAX_HP = 3000;
    static float DEFAULT_SPEED = 3;
    static float DEFAULT_RADIUS_DETECT = 45;
    static float DEFAULT_RADIUS_ATTACK = 15;
    static float DEFAULT_DAMAGE = 12;
    static float DEFAULT_DIAMETER = 6;
    
    static int STATS = 15;
    
    static float MAX_HP_POINT = 150;
    static float SPEED_POINT = 0.4f;
    static float RADIUS_DETECT_POINT = 1.2f;
    static float RADIUS_ATTACK_POINT = 0.6f;
    static float DAMAGE_POINT = 1.2f;
    
    static int GREEN = 0;
    static int ORANGE = 0;

    public static void main(String[] args){
		GAME.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JFrame frame = new JFrame(CustomWars.NAME);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(GAME, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		GAME.start();
	}
	
	private void start(){
		RUNNING = true;
		new Thread(this).start();
	}

	@Override
	public void run(){
		init();
		
		while(RUNNING){
            UPDATE.update();
            RENDER.render();
		}
	}
	
	void init(){
		for(int i = 0; i < FACTION_GREEN; i++){
			ENTITY.add(new Entity(Color.GREEN));
		}
		for(int i = 0; i < FACTION_ORANGE; i++){
			ENTITY.add(new Entity(Color.ORANGE));
		};
	}
}
