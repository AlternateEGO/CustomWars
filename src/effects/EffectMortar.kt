package effects

import Bullet
import CustomWars
import Effect
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D

internal class EffectMortar(bullet: Bullet) : Effect(bullet) {

    override var bullet: Bullet? = bullet

    init {
        time = 70
        x = bullet.x
        y = bullet.y
        radius = 0f
    }

    override fun render(graphics: Graphics2D) {
        if (life) {
            radius = bullet!!.entity.radiusDamage
            graphics.color = Color(Color.RED.red, Color.RED.green, Color.RED.blue, time)
            graphics.draw(Ellipse2D.Double(x - radius / 2, y - radius / 2, radius.toDouble(), radius.toDouble()))
            graphics.fillOval((x - radius / 2).toInt(), (y - radius / 2).toInt(), radius.toInt(), radius.toInt())
        }
    }

    override fun update() {
        if (life) {
            CustomWars.entity.stream().filter { e -> bullet!!.entity.faction !== e.faction && e.life }.filter { e -> Point2D.distance(x, y, e.x, e.y) <= bullet!!.entity.radiusDamage }.forEach { e ->
                if (life) {
                    if (e.hp > 0) {
                        e.hp = e.hp.minus(bullet!!.entity.pereodicDamage)
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

}

