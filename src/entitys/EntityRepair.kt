package entitys

import CustomWars
import Entity
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.*

internal class EntityRepair(color: Color, xMin: Int, xMax: Int) : Entity(color) {
    companion object {
        internal const val DEFAULT_MAX_HP = 5000f
        internal const val DEFAULT_SPEED = 0.8f
        internal const val DEFAULT_RADIUS_INTERACTION = 35f
        internal const val DEFAULT_DAMAGE = 7f
        internal const val DEFAULT_DIAMETER = 8f
    }

    override fun render(graphics: Graphics2D) {
        graphics.color = faction
        graphics.draw(Ellipse2D.Double(x - diameter!! / 2, y - diameter!! / 2, diameter!!.toDouble(), diameter!!.toDouble()))
        graphics.fillOval((x - diameter!! / 2).toInt(), (y - diameter!! / 2).toInt(), diameter!!.toInt(), diameter!!.toInt())
    }

    override fun update() {
        CustomWars.entity.stream().filter { e -> this !== e && this.faction == e.faction && e.life && e.hp <= e.maxHP && e !is EntityRepair }.forEach { e ->
            if (target != null) {
                if (Point2D.distance(x, y, e.x, e.y) < Point2D.distance(x, y, target!!.x, target!!.y)) {
                    target = e
                }
            } else {
                target = e
            }
        }
        move()
        if (target != null) {
            if (target!!.life) {
                if (Point2D.distance(x, y, target!!.x, target!!.y) <= radiusInteraction) {
                    if (target!!.hp < target!!.maxHP) {
                        target!!.hp += damage
                        if (target!!.hp >= target!!.maxHP) {
                            target = null
                        }
                    } else {
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
        radiusInteraction = DEFAULT_RADIUS_INTERACTION
        damage = DEFAULT_DAMAGE
        speed = DEFAULT_SPEED
        diameter = DEFAULT_DIAMETER
    }
}