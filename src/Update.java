import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

class Update{
    private long UPDATE = System.currentTimeMillis();
    private CustomWars GAME = null;

    private int TICK = 50;
    
    ArrayList<Float> REPUTATION = new ArrayList<>();

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
		                            e.LAST_DAMAGE = System.currentTimeMillis();
		                            e.TARGET.LAST_DAMAGE = System.currentTimeMillis();
		                            if(e.TARGET.HP <= 0){
			                            e.TARGET.LIFE = false;
			                        	e.TARGET = null;
		                            }
		                        }else{
		                        	e.TARGET.LIFE = false;
		                        	e.TARGET = null;
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
                ArrayList<Entity> GREEN = new ArrayList<>();
                for(Entity e : GAME.ENTITY){
                	if(e.FACTION == Color.GREEN) GREEN.add(e);
                }
                Collections.sort(GREEN, new EntityComparator());
                REPUTATION.add(GREEN.get(0).REPUTATION);
                float sr = 0;
                for(float f : REPUTATION){
                	sr += f;
                }
                System.out.println(sr / REPUTATION.size());
                ArrayList<Entity> entity = new ArrayList<>();
                for(int i = 0; i < 8; i++){
                	for(int j = 0; j < 10; j++){
                		Entity e0 = GREEN.get(j);
                		Entity e1 = e0;
                		while(e0 == e1){
                			e1 = GREEN.get(new Random().nextInt(10));
                		}
                		float par0;
                		float par1;
                		float par2;
                		float par3;
                		float par4;
                		boolean b = new Random().nextBoolean();
                		if(b){
                			par0 = e0.MAX_HP;
                		}else par0 = e1.MAX_HP;
                		b = new Random().nextBoolean();
                		if(b){
                			par1 = e0.SPEED;
                		}else par1 = e1.SPEED;
                		b = new Random().nextBoolean();
                		if(b){
                			par2 = e0.RADIUS_DETECT;
                		}else par2 = e1.RADIUS_DETECT;
                		b = new Random().nextBoolean();
                		if(b){
                			par3 = e0.RADIUS_ATTACK;
                		}else par3 = e1.RADIUS_ATTACK;
                		b = new Random().nextBoolean();
                		if(b){
                			par4 = e0.DAMAGE;
                		}else par4 = e1.DAMAGE;
                		entity.add(new Entity(par0, par1, par2, par3, par4, Color.GREEN));
                	}
                }
                
                for(int i = GAME.ENTITY.size() - 1; i >= 0; i--){
                	GAME.ENTITY.remove(i);
                }
                
                GAME.ENTITY = entity;
                
                for(int i = 0; i < GAME.FACTION_GREEN - 80; i++){
                	GAME.ENTITY.add(new Entity(Color.GREEN));
        		}
        		for(int i = 0; i < GAME.FACTION_ORANGE; i++){
        			GAME.ENTITY.add(new Entity(Color.ORANGE));
        		};
                GAME.RUNNING = true;
            }
        }
    }
    
    public class EntityComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e0, Entity e1){
        	Float rep0 = e0.REPUTATION;
        	Float rep1 = e1.REPUTATION;
            return rep1.compareTo(rep0);
        }
    }
}
