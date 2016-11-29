import java.awt.Color;
import java.awt.geom.Point2D;

class Update{
    private long UPDATE = System.currentTimeMillis();
    private CustomWars GAME = null;

    private int TICK = 35;

    Update(CustomWars game){
        GAME = game;
    }

    void update(){
        if(System.currentTimeMillis() - UPDATE >= 1000 / TICK) {
            UPDATE = System.currentTimeMillis();
            for(Entity e : GAME.ENTITY){
            	if(e.LIFE){
	                e.update();
	                if(e.TARGET != null){
	                	if(e.TARGET.LIFE){
		                    if(Point2D.distance(e.X, e.Y, e.TARGET.X, e.TARGET.Y) <= e.RADIUS_ATTACK){
		                        if(e.TARGET.HP > 0){
		                        	if(e.DAMAGE > e.TARGET.HP){
		                        		e.REPUTATION += e.TARGET.HP;
		                                e.TARGET.REPUTATION -= e.TARGET.HP;
		                        	}else{
		                        		e.REPUTATION += e.DAMAGE;
		                                e.TARGET.REPUTATION -= e.DAMAGE;
		                        	}
		                            e.TARGET.HP -= e.DAMAGE;
		                            if(e.TARGET.HP <= 0){
			                            e.TARGET.LIFE = false;
			                        	e.TARGET = null;
		                            }
		                        }else{
		                        }
		                    }
	                	}else{
	                		e.TARGET = null;
	                	}
	                }
            	}
            }
            int green = 0;
            int orange = 0;
            for(Entity e : GAME.ENTITY)
            	if(e.LIFE) if(e.FACTION == Color.GREEN){
            			green++;
                    }else orange++;
            if(green == 0 || orange == 0){
                GAME.RUNNING = false;
                if(green > orange){
                    CustomWars.GREEN++;
                }else CustomWars.ORANGE++;
                for(int i = GAME.ENTITY.size() - 1; i >= 0; i--){
                	GAME.ENTITY.remove(i);
                }
                GAME.init();
                GAME.RUNNING = true;
            }
        }
    }
}
