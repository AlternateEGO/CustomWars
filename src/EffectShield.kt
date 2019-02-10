import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D

internal class EffectShield(e: Bullet) : Effect(e) {

    init {
        time = 25
        x = e.x
        y = e.y
    }

    override fun render(graphics: Graphics2D) {
        if (life) {
            val c = Color(Color.WHITE.red, Color.WHITE.green, Color.WHITE.blue, time)
            val a = 15
            graphics.color = c
            graphics.draw(Ellipse2D.Double(x - a / 2, y - a / 2, a.toDouble(), a.toDouble()))
            graphics.fillOval((x - a / 2).toInt(), (y - a / 2).toInt(), a, a)
        }
    }
}

