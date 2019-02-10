import java.awt.Color
import java.awt.Graphics2D

internal open class Bullet(var entity: Entity, var target: Entity) {
    var x = entity.x
    var y = entity.y
    var xTarget = target.x
    var yTarget = target.y
    var speed = 0.toDouble()
    var life = true
    var time = 0
    var maxTime = 0
    var faction: Color? = null

    open fun render(graphics: Graphics2D) {}

    open fun update() {}

    open fun move() {}
}