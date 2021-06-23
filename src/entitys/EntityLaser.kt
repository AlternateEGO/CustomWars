package entitys

import CustomWars
import Entity
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.*

internal class EntityLaser(color: Color, xMin: Int, xMax: Int) : Entity(color) {
    companion object {
        internal const val DEFAULT_MAX_HP = 3000f
        internal const val DEFAULT_SPEED_MAX = 4.8f
        internal const val DEFAULT_SPEED_ACCELERATION = 0.34f
        internal const val DEFAULT_RADIUS_ATTACK = 35f
        internal const val DEFAULT_DAMAGE = 12f
        internal const val DEFAULT_DIAMETER = 8f
    }

    override fun render(graphics: Graphics2D) {
        graphics.color = faction
        graphics.draw(Ellipse2D.Double(x - diameter!! / 2, y - diameter!! / 2, diameter!!.toDouble(), diameter!!.toDouble()))
        graphics.fillOval((x - diameter!! / 2).toInt(), (y - diameter!! / 2).toInt(), diameter!!.toInt(), diameter!!.toInt())
    }

    override fun update() {
        CustomWars.entity.stream().filter { e -> this !== e && this.faction !== e.faction && e.life }.forEach { e ->
            if (target != null) {
                if (Point2D.distance(x, y, e.x, e.y) < Point2D.distance(x, y, target!!.x, target!!.y)) {
                    target = e
                }
            } else {
                target = e
            }
        }
        moveEnemy()
        if (target != null) {
            if (target!!.life) {
                if (Point2D.distance(x, y, target!!.x, target!!.y) <= radiusInteraction) {
                    if (target!!.hp > 0) {
                        target!!.hp = target!!.hp.minus(damage)
                        if (target!!.hp <= 0) {
                            target!!.life = false
                            target = null
                        }
                    } else {
                        target!!.life = false
                        target = null
                    }
                }
            } else {
                target = null
            }
        }
    }

    init {
        x = Random().nextInt(xMax - xMin).toDouble() + xMin
        y = Random().nextInt(CustomWars.HEIGHT).toDouble()
        pathX = x
        pathY = y
        hp = DEFAULT_MAX_HP
        maxHP = DEFAULT_MAX_HP
        radiusInteraction = DEFAULT_RADIUS_ATTACK
        damage = DEFAULT_DAMAGE
        speedAcceleration = DEFAULT_SPEED_ACCELERATION
        speedMax = DEFAULT_SPEED_MAX
        diameter = DEFAULT_DIAMETER
    }
}