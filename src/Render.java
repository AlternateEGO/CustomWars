import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;

class Render{
    private long RENDER = System.currentTimeMillis();
    private CustomWars GAME = null;

    private int TICK = 20;

    Render(CustomWars game){
        GAME = game;
    }

    void render(){
        if(System.currentTimeMillis() - RENDER >= 1000 / TICK){
            RENDER = System.currentTimeMillis();
            BufferStrategy bs = GAME.getBufferStrategy();
            if (bs == null){
                GAME.createBufferStrategy(2);
                GAME.requestFocus();
                return;
            }
            Graphics2D graphics = (Graphics2D) bs.getDrawGraphics();
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, GAME.getWidth(), GAME.getHeight());
            GAME.ENTITY.stream().filter(e -> e.TARGET != null).filter(e -> Point2D.distance(e.X, e.Y, e.TARGET.X, e.TARGET.Y) <= e.RADIUS_ATTACK).filter(e -> e.TARGET.HP > 0).forEach(e -> {
                graphics.setColor(e.COLOR_DRAW);
                graphics.draw(new Line2D.Double(e.X, e.Y, e.TARGET.X, e.TARGET.Y));
            });
            for(Entity e : GAME.ENTITY){
                e.render(graphics);
            }
            int green = 0;
            int orange = 0;
            for(Entity e : GAME.ENTITY){
                if(e.FACTION == Color.GREEN){
                    green++;
                }else orange++;
            }
            graphics.setColor(Color.WHITE);
            graphics.drawString("Green = " + green, 700, 18);
            graphics.drawString("Orange = " + orange, 693, 29);
            graphics.dispose();
            bs.show();
        }
    }
}
