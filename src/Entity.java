import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

class Entity{
	double X;
	double Y;
	private float SPEED;
	private double PATH_X;
	private double PATH_Y;
	Float DIAMETER;
	Color FACTION;
	Color COLOR_DRAW;
	float MAX_HP;
	float HP;
	float RADIUS_ATTACK;
	float RADIUS_DETECT;
	float DAMAGE;
	
	boolean LIFE = true;
	
	Entity TARGET;
	
	float REPUTATION;
	
	Entity(int x, int y, Color color){
		HP = CustomWars.DEFAULT_MAX_HP;
		MAX_HP = CustomWars.DEFAULT_MAX_HP;
        RADIUS_ATTACK = CustomWars.DEFAULT_RADIUS_ATTACK;
        RADIUS_DETECT = CustomWars.DEFAULT_RADIUS_DETECT;
		DAMAGE = CustomWars.DEFAULT_DAMAGE;
		X = x;
		Y = y;
		SPEED = CustomWars.DEFAULT_SPEED;
		PATH_X = x;
		PATH_Y = y;
		DIAMETER = CustomWars.DEFAULT_DIAMETER;
		FACTION = color;
		
		for(int i = 0; i <= CustomWars.STATS; i++){
			int rand = new Random().nextInt(5);
			switch(rand){
				case 0: MAX_HP += CustomWars.MAX_HP_POINT;
				case 1: SPEED += CustomWars.SPEED_POINT;
				case 2: RADIUS_DETECT += CustomWars.RADIUS_DETECT_POINT;
				case 3: RADIUS_ATTACK += CustomWars.RADIUS_ATTACK_POINT;
				case 4: DAMAGE += CustomWars.DAMAGE_POINT;
			}
		}
	}
	
	void render(Graphics2D graphics){
        graphics.setColor(COLOR_DRAW);
        graphics.draw(new Ellipse2D.Double(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER));
        graphics.fillOval(((Double)(X - DIAMETER / 2)).intValue(), ((Double)(Y - DIAMETER / 2)).intValue(), DIAMETER.intValue(), DIAMETER.intValue());
	}
	
	void update(){
		if(!LIFE) return;
		try{
			COLOR_DRAW = new Color(FACTION.getRed(), FACTION.getGreen(), FACTION.getBlue(), (int)(HP / MAX_HP * 100 * 255 / 100));
		}catch(IllegalArgumentException e){
			COLOR_DRAW = new Color(FACTION.getRed(), FACTION.getGreen(), FACTION.getBlue(), 255);
		}
		CustomWars.GAME.ENTITY.stream().filter(e -> this != e && this.FACTION != e.FACTION && e.LIFE).filter(e -> Point2D.distance(X, Y, e.X, e.Y) <= RADIUS_DETECT).forEach(e -> {
			if(TARGET != null){
				if (Point2D.distance(X, Y, e.X, e.Y) < Point2D.distance(X, Y, TARGET.X, Y)) {
					TARGET = e;
				}
			}else{
				TARGET = e;
			}
		});
		if(TARGET != null) if(Point2D.distance(X, Y, TARGET.X, TARGET.Y) > RADIUS_DETECT){
			TARGET = null;
		}
		move();
	}
	
	private void move(){
        if(TARGET != null){
            double distance = Point2D.distance(X, Y, TARGET.X, TARGET.Y);
            double distance_x = Point2D.distance(X, 0, TARGET.X, 0);
            double distance_y = Point2D.distance(0, Y, 0, TARGET.Y);
            double speed_x = distance_x / distance;
            double speed_y = distance_y / distance;
            if(distance <= RADIUS_ATTACK - 2 || HP / MAX_HP <= 0.1){
                if(X > TARGET.X){
                    X += speed_x * SPEED;
                }else X -= speed_x * SPEED;
                if(Y > TARGET.Y){
                    Y += speed_y * SPEED;
                }else Y -= speed_y * SPEED;
            }else{
                if(X < TARGET.X){
                    X += speed_x * SPEED;
                }else X -= speed_x * SPEED;
                if(Y < TARGET.Y){
                    Y += speed_y * SPEED;
                }else Y -= speed_y * SPEED;
            }
            if(X < 0){
    		    X = 0;
            }else if(X > CustomWars.WIDTH) X = CustomWars.WIDTH;
            if(Y < 0){
                Y = 0;
            }else if(Y > CustomWars.HEIGHT) Y = CustomWars.HEIGHT;
        }else{
            double distance = Point2D.distance(X, Y, PATH_X, PATH_Y);
            if(distance < 1){
                PATH_X = new Random().nextInt(CustomWars.WIDTH);
                PATH_Y = new Random().nextInt(CustomWars.HEIGHT);
                distance = Point2D.distance(X, Y, PATH_X, PATH_Y);
            }
            double distance_x = Point2D.distance(X, 0, PATH_X, 0);
            double distance_y = Point2D.distance(0, Y, 0, PATH_Y);
            double speed_x = distance_x / distance;
            double speed_y = distance_y / distance;
            if(X < PATH_X){
            	if(distance_x > speed_x * SPEED){
            		X += speed_x * SPEED;
            	}else X += distance_x;
            }else{
            	if(distance_x > speed_x * SPEED){
            		X -= speed_x * SPEED;
            	}else X -= distance_x;
            }
            if(Y < PATH_Y){
            	if(distance_y > speed_y * SPEED){
            		Y += speed_y * SPEED;
            	}else Y += distance_y;
            }else{
            	if(distance_y > speed_y * SPEED){
            		Y -= speed_y * SPEED;
            	}else Y -= distance_y;
            }
        }
    }
}
