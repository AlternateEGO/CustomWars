package bullets

import Bullet
import CustomWars
import Entity
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D

internal class BulletSniper(entity: Entity, target: Entity) : Bullet(entity, target) {
    companion object {
        internal const val DEFAULT_SPEED_BASE = 27.6
        internal const val DEFAULT_SPEED_SLOW = 0.01
        internal const val DEFAULT_SPEED_ACCELERATION_SLOW = 1.0 + DEFAULT_SPEED_SLOW
    }

    private var xPath = true
    private var yPath = true

    override fun render(graphics: Graphics2D) {
        if (life) {
            graphics.color = Color.PINK
            graphics.draw(Line2D.Double(x, y, x, y))
        }
    }

    override fun update() {
        CustomWars.entity.stream().filter { e -> entity.faction !== e.faction && e.life }.filter { e -> Point2D.distance(x, y, e.x, e.y) <= e.diameter!! + speed / 2 }.forEach { e ->
            if (life) {
                if (e.hp > 0) {
                    e.hp = e.hp.minus(entity.damage)
                    speedSlow()
                    if (e.hp <= 0) {
                        e.life = false
                    }
                } else {
                    e.life = false
                }
            }
        }
        move()
    }

    override fun move() {
        val distance = Point2D.distance(x, y, xTarget, yTarget)
        val distanceX = Point2D.distance(x, 0.0, xTarget, 0.0)
        val distanceY = Point2D.distance(0.0, y, 0.0, yTarget)
        val speedX = distanceX / distance
        val speedY = distanceY / distance
        if (xPath) {
            x -= speedX * speed
        } else
            x += speedX * speed
        if (yPath) {
            y -= speedY * speed
        } else y += speedY * speed
        speedSlow()
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
        xPath = x > target.x
        yPath = y > target.y
    }
}