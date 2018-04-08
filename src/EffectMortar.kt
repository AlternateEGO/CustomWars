import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D

internal class EffectMortar(override var bullet: Bullet) : Effect(bullet) {
    override fun render(graphics: Graphics2D) {
        if (life) {
            val c = Color(Color.BLUE.red, Color.BLUE.green, Color.BLUE.blue, time)
            val a = bullet.entity.radiusDamage
            graphics.color = c
            graphics.draw(Ellipse2D.Double(x - a / 2, y - a / 2, a.toDouble(), a.toDouble()))
            graphics.fillOval((x - a / 2).toInt(), (y - a / 2).toInt(), a.toInt(), a.toInt())
        }
    }

    override fun update() {
        if (life) {
            CustomWars.entity.stream().filter({ e -> bullet.entity.faction !== e.faction && e.life }).filter({ e -> Point2D.distance(x, y, e.x, e.y) <= bullet.entity.radiusDamage }).forEach { e ->
                if (life) {
                    if (e.hp > 0) {
                        e.hp = e.hp.minus(bullet.entity.pereodicDamage)
                        if (e.hp <= 0) {
                            e.life = false
                        }
                    } else {
                        e.life = false
                    }
                }
            }
            time--
        }
        if (time < 0) life = false
    }

    init {
        time = 255
        x = bullet.x
        y = bullet.y
    }
}

