import java.awt.Graphics2D

internal open class Effect(open var bullet: Bullet?) {
    var time = 0
    var life = true
    var x = 0.toDouble()
    var y = 0.toDouble()
    open fun render(graphics: Graphics2D) {}
    open fun update() {
        if (life) {
            time--
        }
        if (time < 0) life = false
    }
}