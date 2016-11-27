import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class CustomWars extends Canvas implements Runnable{
	private static final long serialVersionUID = 7506117823203158228L;
	
	boolean RUNNING;

	private static String NAME = "CustomWars";
	
	ArrayList<Entity> ENTITY = new ArrayList<>();
	
	static CustomWars GAME = new CustomWars();

    static int WIDTH = 800;
    static int HEIGHT = 600;

	private Render RENDER = new Render(this);
    private Update UPDATE = new Update(this);

    private int FACTION_GREEN = 100;
    private int FACTION_ORANGE = 100;

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
			ENTITY.add(new Entity(new Random().nextInt(WIDTH) + 2, new Random().nextInt(HEIGHT) + 2, 6, 3, Color.GREEN));
		}
		for(int i = 0; i < FACTION_ORANGE; i++){
			ENTITY.add(new Entity(new Random().nextInt(WIDTH) + 2, new Random().nextInt(HEIGHT) + 2, 6, 3, Color.ORANGE));
		}
	}
}
