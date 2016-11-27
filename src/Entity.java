import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

class Entity{
	double X;
	double Y;
	private double SPEED;
	private double PATH_X;
	private double PATH_Y;
	Double DIAMETER;
	Color FACTION;
	Color COLOR_DRAW;
	float MAX_HP;
	float HP;
	int RADIUS_ATTACK;
	float DAMAGE;
	
	Entity TARGET;
	
	Entity(int x, int y, double diameter, int speed, Color color){
		HP = new Random().nextInt(2000) + 3000;
		MAX_HP = HP;
        RADIUS_ATTACK = new Random().nextInt(30) + 15;
		DAMAGE = new Random().nextInt(7) + 10;
		X = x;
		Y = y;
		SPEED = speed;
		PATH_X = x;
		PATH_Y = y;
		DIAMETER = diameter;
		FACTION = color;
	}
	
	void render(Graphics2D graphics){
        graphics.setColor(COLOR_DRAW);
        graphics.draw(new Ellipse2D.Double(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER));
        graphics.fillOval(((Double)(X - DIAMETER / 2)).intValue(), ((Double)(Y - DIAMETER / 2)).intValue(), DIAMETER.intValue(), DIAMETER.intValue());
	}
	
	void update(){
		try{
			COLOR_DRAW = new Color(FACTION.getRed(), FACTION.getGreen(), FACTION.getBlue(), (int)(HP / MAX_HP * 100 * 255 / 100));
		}catch(IllegalArgumentException e){
			COLOR_DRAW = new Color(FACTION.getRed(), FACTION.getGreen(), FACTION.getBlue(), 0);
		}
		if(X < -100){
		    X = new Random().nextInt(CustomWars.WIDTH) + 2;
        }else if(X > CustomWars.WIDTH + 100) X = new Random().nextInt(CustomWars.WIDTH) + 2;
        if(Y < -100){
            Y = new Random().nextInt(CustomWars.HEIGHT) + 2;
        }else if(Y > CustomWars.HEIGHT + 100) X = new Random().nextInt(CustomWars.HEIGHT) + 2;
		move();
		CustomWars.GAME.ENTITY.stream().filter(e -> this != e && this.FACTION != e.FACTION).filter(e -> Point2D.distance(X, Y, e.X, e.Y) <= RADIUS_ATTACK).forEach(e -> {
			if (TARGET != null) {
				if (Point2D.distance(X, Y, e.X, e.Y) < Point2D.distance(X, Y, TARGET.X, Y)) {
					TARGET = e;
				}
			} else {
				TARGET = e;
			}
		});
	}
	
	private void move(){
        if(TARGET != null){
            double distance = Point2D.distance(X, Y, TARGET.X, TARGET.Y);
            double distance_x = Point2D.distance(X, 0, TARGET.X, 0);
            double distance_y = Point2D.distance(0, Y, 0, TARGET.Y);
            double speed_x = distance_x / distance;
            double speed_y = distance_y / distance;
            if(distance <= RADIUS_ATTACK - 3 || HP / MAX_HP <= 0.1){
                if(X > TARGET.X){
                    X += speed_x * SPEED;
                }else X -= speed_x;
                if(Y > TARGET.Y){
                    Y += speed_y;
                }else Y -= speed_y;
            }else{
                if(X < TARGET.X){
                    X += speed_x * SPEED;
                }else X -= speed_x;
                if(Y < TARGET.Y){
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
