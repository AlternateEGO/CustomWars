import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.*

internal class EntityShield(color: Color, xMin: Int, xMax: Int) : Entity(color) {
    companion object {
        internal const val DEFAULT_MAX_HP = 10000f
        internal const val DEFAULT_SPEED = 0.2f
        internal const val DEFAULT_RADIUS_ATTACK = 10f
        internal const val DEFAULT_DIAMETER = 8f
        internal const val DEFAULT_MAGAZINE = 15
        internal const val DEFAULT_MAX_MAGAZINE = 15
        internal const val DEFAULT_MAGAZINE_RELOAD = 15
    }

    override fun render(graphics: Graphics2D) {
        graphics.color = faction
        graphics.draw(Ellipse2D.Double(x - diameter!! / 2, y - diameter!! / 2, diameter!!.toDouble(), diameter!!.toDouble()))
        graphics.fillOval((x - diameter!! / 2).toInt(), (y - diameter!! / 2).toInt(), diameter!!.toInt(), diameter!!.toInt())
    }

    override fun update() {
        CustomWars.entity.stream().filter { e -> this !== e && this.faction == e.faction && e.life && e !is EntityShield }.forEach { e ->
            if (target != null) {
                if (Point2D.distance(x, y, e.x, e.y) < Point2D.distance(x, y, target!!.x, target!!.y)) {
                    target = e
                }
            } else {
                target = e
            }
        }
        move()
        if (magazine > 0) {
            CustomWars.bullet.stream().filter { e -> this.faction !== e.faction && e.life }.forEach { e ->
                if (magazine > 0) {
                    if (Point2D.distance(x, y, e.x, e.y) < 70) {
                        magazine--
                        CustomWars.effect.add(EffectShield(e))
                        e.life = false
                    }
                }
            }
        }
        if (magazine < maxMagazine) currentReload++
        if (currentReload >= magazineReload) {
            currentReload = 0
            magazine++
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
        speed = DEFAULT_SPEED
        diameter = DEFAULT_DIAMETER
        magazine = DEFAULT_MAGAZINE
        maxMagazine = DEFAULT_MAX_MAGAZINE
        magazineReload = DEFAULT_MAGAZINE_RELOAD
    }
}