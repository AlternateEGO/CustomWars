import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D

internal class Render {
    private var render = System.currentTimeMillis()

    private val tick = 20

    fun render() {
        if (System.currentTimeMillis() - render >= 1000 / tick) {
            render = System.currentTimeMillis()
            val bs = CustomWars.GAME.bufferStrategy
            if (bs == null) {
                CustomWars.GAME.createBufferStrategy(2)
                CustomWars.GAME.requestFocus()
                return
            }
            val graphics = bs.drawGraphics as Graphics2D
            graphics.color = Color.BLACK
            graphics.fillRect(0, 0, CustomWars.WIDTH, CustomWars.HEIGHT)
            CustomWars.entity.stream().filter({ e -> e.target != null && e is EntityLaser }).filter({ e -> Point2D.distance(e.x, e.y, e.target!!.x, e.target!!.y) <= e.radiusAttack && e.target!!.life && e.life }).filter({ e -> e.target!!.hp > 0 }).forEach { e ->
                graphics.color = e.faction
                graphics.draw(Line2D.Double(e.x, e.y, e.target!!.x, e.target!!.y))
            }
            for (e in CustomWars.entity) {
                if (e.life) e.render(graphics)
            }
            for (e in CustomWars.bullet) {
                if (e.life) e.render(graphics)
            }
            var green = 0
            var orange = 0
            for (e in CustomWars.entity) {
                if (e.life)
                    if (e.faction === Color.GREEN) {
                        green++
                    } else
                        orange++
            }
            graphics.color = Color.GREEN
            graphics.drawString("Green = $green", 700, 18)
            graphics.color = Color.ORANGE
            graphics.drawString("Orange = $orange", 693, 33)
            graphics.color = Color.WHITE
            graphics.drawString("Entity = ${CustomWars.entity.size}", 705, 48)
            graphics.drawString("Bullet = ${CustomWars.bullet.size}", 703, 63)
            graphics.dispose()
            bs.show()
        }
    }
}
