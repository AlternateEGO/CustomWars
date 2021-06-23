package bullets

import Bullet
import CustomWars
import Entity
import Update
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D

internal class BulletRocket(entity: Entity, target: Entity) : Bullet(entity, target) {
    companion object {
        internal const val DEFAULT_SPEED_BASE = 3.4
        internal const val DEFAULT_SPEED_SLOW = 0.0
        internal const val DEFAULT_SPEED_ACCELERATION_SLOW = 1.0
    }

    override fun render(graphics: Graphics2D) {
        if (life) {
            graphics.color = Color.PINK
            graphics.draw(Line2D.Double(x, y, x + 3, y))
            graphics.draw(Line2D.Double(x + 3, y, x + 3, y + 3))
            graphics.draw(Line2D.Double(x + 3, y + 3, x, y + 3))
            graphics.draw(Line2D.Double(x, y + 3, x, y))
        }
    }

    override fun update() {
        if (time >= maxTime) life = false
        if (life) {
            CustomWars.entity.stream().filter { e -> entity.faction !== e.faction && e.life }.filter { e -> Point2D.distance(x, y, e.x, e.y) <= e.diameter!! }.forEach { e ->
                if (e.hp > 0) {
                    e.hp = e.hp.minus(entity.damage)
                    if (e.hp <= 0) {
                        e.life = false
                    }
                } else {
                    e.life = false
                }
                life = false
            }
        }
        if (life) {
            CustomWars.entity.stream().filter { e -> entity.faction !== e.faction && e.life && target != e }.forEach { e ->
                if (e.life) {
                    if (target.life){
                        if (Point2D.distance(x, y, e.x, e.y) < Point2D.distance(x, y, target.x, target.y)) {
                            target = e
                        }
                    } else {
                        target = e
                    }
                } else target = e
            }
            if(!target.life) life = false
            move()
        }
        time++
    }

    override fun move() {
        val distance = Point2D.distance(x, y, target.x, target.y)
        val distanceX = Point2D.distance(x, 0.0, target.x, 0.0)
        val distanceY = Point2D.distance(0.0, y, 0.0, target.y)
        val speedX = distanceX / distance
        val speedY = distanceY / distance
        if (x > target.x) {
            x -= speedX * speed
        } else
            x += speedX * speed
        if (y > target.y) {
            y -= speedY * speed
        } else y += speedY * speed
        if (speed < speedBase) {
            speed *= 1.025
        }
        if (x > CustomWars.WIDTH * 2) life = false
        if (x < -CustomWars.WIDTH) life = false
        if (y > CustomWars.HEIGHT * 2) life = false
        if (y < -CustomWars.HEIGHT) life = false
    }

    init {
        speedBase = DEFAULT_SPEED_BASE
        speed = 0.3
        speedSlow = DEFAULT_SPEED_SLOW
        speedAccelerationSlow = DEFAULT_SPEED_ACCELERATION_SLOW
        maxTime = 5 * Update.tick
    }
}