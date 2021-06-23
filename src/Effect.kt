import java.awt.Graphics2D

internal open class Effect(open var bullet: Bullet?) {
    var time = 0
    var life = true
    var radius = 0f
    var x = 0.0
    var y = 0.0
    open fun render(graphics: Graphics2D) {}
    open fun update() {
        if (life) {
            time--
        }
        if (time < 0) life = false
    }
}