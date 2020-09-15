import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D

internal class EffectShield(e: Bullet) : Effect(e) {

    init {
        time = 1
        x = e.x
        y = e.y
        radius = 15f
    }

    override fun render(graphics: Graphics2D) {
        if (life) {
            graphics.color = Color(Color.BLUE.red, Color.BLUE.green, Color.BLUE.blue, 160)
            graphics.draw(Ellipse2D.Double(x - radius / 2, y - radius / 2, radius.toDouble(), radius.toDouble()))
            graphics.fillOval((x - radius / 2).toInt(), (y - radius / 2).toInt(), radius.toInt(), radius.toInt())
        }
    }
}

