import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.*

internal class EntityGunner(color: Color, xMin: Int, xMax: Int) : Entity(color) {
    companion object {
        internal const val DEFAULT_MAX_HP = 1700f
        internal const val DEFAULT_SPEED = 1.4f
        internal const val DEFAULT_RADIUS_ATTACK = 107f
        internal const val DEFAULT_DAMAGE = 112f
        internal const val DEFAULT_DIAMETER = 4f
        internal const val DEFAULT_SPEED_ATTACK = 50
    }

    override fun render(graphics: Graphics2D) {
        graphics.color = faction
        graphics.draw(Ellipse2D.Double(x - diameter!! / 2, y - diameter!! / 2, diameter!!.toDouble(), diameter!!.toDouble()))
        graphics.fillOval((x - diameter!! / 2).toInt(), (y - diameter!! / 2).toInt(), diameter!!.toInt(), diameter!!.toInt())
    }

    override fun update() {
        lastDamage += 1
        CustomWars.entity.stream().filter { e -> this !== e && this.faction !== e.faction && e.life }.forEach { e ->
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
                if (Point2D.distance(x, y, target!!.x, target!!.y) <= radiusAttack) {
                    if (target!!.hp > 0) {
                        if (lastDamage >= DEFAULT_SPEED_ATTACK) {
                            val bullet: Bullet = BulletGunner(this, target!!)
                            CustomWars.bullet.add(bullet)
                            gun(bullet)
                            lastDamage = 0
                        }
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
        radiusAttack = DEFAULT_RADIUS_ATTACK
        damage = DEFAULT_DAMAGE
        speed = DEFAULT_SPEED
        diameter = DEFAULT_DIAMETER
        lastDamage = DEFAULT_SPEED_ATTACK
    }
}