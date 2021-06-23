import entitys.EntityLaser
import entitys.EntityRepair
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
            graphics.fillRect(0, 0, CustomWars.WIDTH + 200 + 10, CustomWars.HEIGHT + 10)
            CustomWars.entity.stream().filter { e -> e.target != null && e is EntityLaser }.filter { e -> Point2D.distance(e.x, e.y, e.target!!.x, e.target!!.y) <= e.radiusInteraction && e.target!!.life && e.life }.filter { e -> e.target!!.hp > 0 }.forEach { e ->
                graphics.color = Color.RED
                graphics.draw(Line2D.Double(e.x, e.y, e.target!!.x, e.target!!.y))
            }
            CustomWars.entity.stream().filter { e -> e.target != null && e is EntityRepair }.filter { e -> Point2D.distance(e.x, e.y, e.target!!.x, e.target!!.y) <= e.radiusInteraction && e.target!!.life && e.life }.filter { e -> e.target!!.hp > 0 }.forEach { e ->
                graphics.color = Color.BLUE
                graphics.draw(Line2D.Double(e.x, e.y, e.target!!.x, e.target!!.y))
            }
            for (e in CustomWars.effect) {
                if (e.life) e.render(graphics)
            }
            for (e in CustomWars.bullet) {
                if (e.life) e.render(graphics)
            }
            for (e in CustomWars.entity) {
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
            graphics.color = Color.WHITE
            graphics.drawLine(800,0, 800, 600)
            graphics.color = Color.GREEN
            graphics.drawString("Green = $green", 805, 18)
            graphics.color = Color.ORANGE
            graphics.drawString("Orange = $orange", 805, 33)
            graphics.color = Color.WHITE
            graphics.drawString("Entity = ${CustomWars.entity.size + CustomWars.bullet.size + CustomWars.effect.size}", 905, 48)
            graphics.drawString("Bullet = ${CustomWars.bullet.size}", 805, 63)
            graphics.drawString("Effect = ${CustomWars.effect.size}", 805, 78)
            graphics.dispose()
            bs.show()
        }
    }
}
