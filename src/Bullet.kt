import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D

internal open class Bullet(var entity: Entity, private var target: Entity){
    private var x = entity.x
    private var y = entity.y
    private var xTarget = target.x
    private var yTarget = target.y
    private var speed = 3.6f
    var life = true
    private var xPath = true
    private var yPath = true

    fun render(graphics: Graphics2D){
        if(life) {
            graphics.color = entity.faction
            graphics.draw(Line2D.Double(x, y, x, y))
        }
    }

    fun update() {
        CustomWars.entity.stream().filter({ e -> entity.faction !== e.faction && e.life }).filter({ e -> Point2D.distance(x, y, e.x, e.y) <= e.diameter!! }).forEach {
            if(life){
                if (target.hp > 0) {
                    target.hp = target.hp.minus(entity.damage)
                    if (target.hp <= 0) {
                        target.life = false
                    }
                } else {
                    target.life = false
                }
            }
            this.life = false
        }
        move()
    }

    private fun move(){
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

        if(x > CustomWars.WIDTH) life = false
        if(x < 0) life = false
        if(y > CustomWars.HEIGHT) life = false
        if(y < 0) life = false
    }

    init{
        xPath = x > target.x
        yPath = y > target.y
    }
}