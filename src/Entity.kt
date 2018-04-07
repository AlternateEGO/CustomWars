import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.util.Random

internal open class Entity(color: Color) {
    var x: Double = 0.toDouble()
    var y: Double = 0.toDouble()
    var speed: Float = 0.toFloat()
    var pathX: Double = 0.toDouble()
    var pathY: Double = 0.toDouble()
    var diameter: Float? = null
    var faction: Color = color
    var maxHP: Float = 0.toFloat()
    var hp: Float = 0.toFloat()
    var radiusAttack: Float = 0.toFloat()
    var radiusDetect: Float = 0.toFloat()
    var damage: Float = 0.toFloat()
    var lastDamage = 0

    var life = true

    var target: Entity? = null

    open fun render(graphics: Graphics2D) { }

    open fun update() { }

    fun move() {
        if (target != null) {
            val distance = Point2D.distance(x, y, target!!.x, target!!.y)
            val distanceX = Point2D.distance(x, 0.0, target!!.x, 0.0)
            val distanceY = Point2D.distance(0.0, y, 0.0, target!!.y)
            val speedX = distanceX / distance
            val speedY = distanceY / distance
            if (distance <= radiusAttack - 2) {
                if (x > target!!.x) {
                    x += speedX * speed
                } else
                    x -= speedX * speed
                if (y > target!!.y) {
                    y += speedY * speed
                } else
                    y -= speedY * speed
            } else {
                if (x < target!!.x) {
                    x += speedX * speed
                } else
                    x -= speedX * speed
                if (y < target!!.y) {
                    y += speedY * speed
                } else
                    y -= speedY * speed
            }
        } else {
            var distance = Point2D.distance(x, y, pathX, pathY)
            if (distance < 1) {
                pathX = Random().nextInt(CustomWars.WIDTH).toDouble()
                pathY = Random().nextInt(CustomWars.HEIGHT).toDouble()
                distance = Point2D.distance(x, y, pathX, pathY)
            }
            val distanceX = Point2D.distance(x, 0.0, pathX, 0.0)
            val distanceY = Point2D.distance(0.0, y, 0.0, pathY)
            val speedX = distanceX / distance
            val speedY = distanceY / distance
            if (x < pathX) {
                x += if (distanceX > speedX * speed) {
                    speedX * speed
                } else
                    distanceX
            } else {
                x -= if (distanceX > speedX * speed) {
                    speedX * speed
                } else
                    distanceX
            }
            if (y < pathY) {
                y += if (distanceY > speedY * speed) {
                    speedY * speed
                } else
                    distanceY
            } else {
                y -= if (distanceY > speedY * speed) {
                    speedY * speed
                } else
                    distanceY
            }
        }
        if(x > CustomWars.WIDTH) x = CustomWars.WIDTH.toDouble()
        if(x < 0) x = 0.0
        if(y > CustomWars.HEIGHT) y = CustomWars.HEIGHT.toDouble()
        if(y < 0) y = 0.0
    }
}
