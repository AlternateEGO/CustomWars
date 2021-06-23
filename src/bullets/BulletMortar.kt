package bullets

import Bullet
import CustomWars
import Entity
import effects.EffectMortar
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D

internal class BulletMortar(entity: Entity, target: Entity) : Bullet(entity, target) {
    companion object {
        internal const val DEFAULT_SPEED_BASE = 8.0
        internal const val DEFAULT_SPEED_SLOW = 0.034
        internal const val DEFAULT_SPEED_ACCELERATION_SLOW = 1.0 + DEFAULT_SPEED_SLOW
    }

    override fun render(graphics: Graphics2D) {
        if (life) {
            graphics.color = Color.PINK
            graphics.draw(Ellipse2D.Double(x - 12, y - 12, 6.toDouble(), 6.toDouble()))
        }
    }

    override fun update() {
        if (life) {
            val distance = Point2D.distance(x, y, xTarget, yTarget)
            val distanceX = Point2D.distance(x, 0.0, xTarget, 0.0)
            val distanceY = Point2D.distance(0.0, y, 0.0, yTarget)
            val speedX = distanceX / distance
            val speedY = distanceY / distance
            if (distance <= speedX + 5 || distance <= speedY + 5) {
                CustomWars.effect.add(EffectMortar(this))
                CustomWars.entity.stream().filter { e -> entity.faction !== e.faction && e.life }.filter { e -> Point2D.distance(x, y, e.x, e.y) <= entity.radiusDamage }.forEach { e ->
                    if (life) {
                        if (e.hp > 0) {
                            e.hp = e.hp.minus(entity.damage)
                            if (e.hp <= 0) {
                                e.life = false
                            }
                        } else {
                            e.life = false
                        }
                    }
                }
                life = false
            }
        }
        if (life) {
            move()
        }
    }

    override fun move() {
        val distance = Point2D.distance(x, y, xTarget, yTarget)
        val distanceX = Point2D.distance(x, 0.0, xTarget, 0.0)
        val distanceY = Point2D.distance(0.0, y, 0.0, yTarget)
        val speedX = distanceX / distance
        val speedY = distanceY / distance
        if (x > xTarget) {
            x -= speedX * speed
        } else
            x += speedX * speed
        if (y > yTarget) {
            y -= speedY * speed
        } else y += speedY * speed
        if ( speed < speedBase * 0.01) {
            CustomWars.effect.add(EffectMortar(this))
            CustomWars.entity.stream().filter { e -> entity.faction !== e.faction && e.life }.filter { e -> Point2D.distance(x, y, e.x, e.y) <= entity.radiusDamage }.forEach { e ->
                if (life) {
                    if (e.hp > 0) {
                        e.hp = e.hp.minus(entity.damage)
                        if (e.hp <= 0) {
                            e.life = false
                        }
                    } else {
                        e.life = false
                    }
                }
            }
            life = false
        }

        if(distance > Point2D.distance(baseX, baseY, xTarget, yTarget) / 1.2) {
            speed *= 1 - speedSlow
            speedSlow *= speedAccelerationSlow
        } else {
            speed *= 1 + speedSlow
            speedSlow *= speedAccelerationSlow
        }

        if (x > CustomWars.WIDTH) life = false
        if (x < 0) life = false
        if (y > CustomWars.HEIGHT) life = false
        if (y < 0) life = false
    }

    init {
        speedBase = DEFAULT_SPEED_BASE
        speed = speedBase
        speedSlow = DEFAULT_SPEED_SLOW
        speedAccelerationSlow = DEFAULT_SPEED_ACCELERATION_SLOW
    }
}