import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

class Update{
    private long UPDATE = System.currentTimeMillis();
    private CustomWars GAME = null;

    private int TICK = 50;

    private int GREEN = 0;
    private int ORANGE = 0;

    Update(CustomWars game){
        GAME = game;
    }

    void update(){
        if(System.currentTimeMillis() - UPDATE >= 1000 / TICK) {
            UPDATE = System.currentTimeMillis();
            ArrayList<Entity> entity = GAME.ENTITY;
            for(Entity e : entity){
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
            for(int i = entity.size() - 1; i >= 0; i--){
                if(entity.get(i).HP <= 0){
                    for(Entity e : entity) if(e.TARGET == entity.get(i)) e.TARGET = null;
                    entity.remove(i);
                }
            }
            int green = 0;
            int orange = 0;
            for(Entity e : GAME.ENTITY)
                if(e.FACTION == Color.GREEN){
                    green++;
                }else orange++;
            if(green == 0 || orange == 0){
                GAME.RUNNING = false;
                if(green > orange){
                    GREEN++;
                }else ORANGE++;
                System.out.println("(GREEN = " + GREEN + ")(" + "ORANGE = " + ORANGE + ")");
                for(int i = entity.size() - 1; i >= 0; i--){
                    entity.remove(i);
                }
                GAME.init();
                GAME.RUNNING = true;
            }
        }
    }
}
