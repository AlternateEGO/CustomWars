import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Entity{
	public double X;
	public double Y;
	public double SPEED;
	public double PATH_X;
	public double PATH_Y;
	public Double DIAMETER;
	public Color FACTION;
	public Color COLOR_DRAW;
	public float MAX_HP;
	public float HP;
	public int RADIUS_ATTACK;
	public float DAMAGE;
	
	public Entity TARGET;
	
	public Entity(int x, int y, double d, int s, Color c){
		HP = new Random().nextInt(2000) + 3000;
		MAX_HP = HP;
		RADIUS_ATTACK = new Random().nextInt(30) + 15;
		DAMAGE = new Random().nextInt(7) + 10;
		X = x;
		Y = y;
		SPEED = s;
		PATH_X = x;
		PATH_Y = y;
		DIAMETER = d;
		FACTION = c;
	}
	
	public void render(Graphics2D g){
		g.setColor(COLOR_DRAW);
		g.draw(new Ellipse2D.Double(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER));
		g.fillOval(((Double)(X - DIAMETER / 2)).intValue(), ((Double)(Y - DIAMETER / 2)).intValue(), DIAMETER.intValue(), DIAMETER.intValue());
	}
	
	public void update(){
		try{
			COLOR_DRAW = new Color(FACTION.getRed(), FACTION.getGreen(), FACTION.getBlue(), (int)(HP / MAX_HP * 100 * 255 / 100));
		}catch(IllegalArgumentException e){
			COLOR_DRAW = new Color(FACTION.getRed(), FACTION.getGreen(), FACTION.getBlue(), 0);
		}
		move();
		for(Entity e : CustomWars.GAME.ENTITY){
			if(this != e && this.FACTION != e.FACTION){
				if(Point2D.distance(X, Y, e.X, e.Y) <= RADIUS_ATTACK){
					if(TARGET != null){
						if(Point2D.distance(X, Y, e.X, e.Y) < Point2D.distance(X, Y, TARGET.X, Y)){
							TARGET = e;
						}
					}else{
						TARGET = e;
					}
				}
			}
		}
	}
	
	public void move(){
		for(int i = 1; i <= SPEED; i++){
			if(TARGET != null){
				double distance = Point2D.distance(X, Y, TARGET.X, TARGET.Y);
				double distance_x = Point2D.distance(X, 0, TARGET.X, 0);
				double distance_y = Point2D.distance(0, Y, 0, TARGET.Y);
				double speed_x = distance_x / distance;
				double speed_y = distance_y / distance;
				if(distance > RADIUS_ATTACK - 1){
					if(X < TARGET.X){
						X += speed_x * SPEED;
					}else X -= speed_x;
					if(Y < TARGET.Y){
						Y += speed_y;
					}else Y -= speed_y;
				}else{
					if(X > TARGET.X){
						X += speed_x * SPEED;
					}else X -= speed_x;
					if(Y > TARGET.Y){
						Y += speed_y;
					}else Y -= speed_y;
				}
			}else{
				double distance = Point2D.distance(X, Y, PATH_X, PATH_Y);
				if(distance < 1){
					PATH_X = new Random().nextInt(800) + 1;
					PATH_Y = new Random().nextInt(600) + 1;
					distance = Point2D.distance(X, Y, PATH_X, PATH_Y);
				}
				double distance_x = Point2D.distance(X, 0, PATH_X, 0);
				double distance_y = Point2D.distance(0, Y, 0, PATH_Y);
				double speed_x = distance_x / distance;
				double speed_y = distance_y / distance;
				if(X < PATH_X){
					X += speed_x * SPEED;
				}else X -= speed_x;
				if(Y < PATH_Y){
					Y += speed_y;
				}else Y -= speed_y;
			}
		}
	}
}
