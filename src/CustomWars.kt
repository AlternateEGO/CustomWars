import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.util.*
import javax.swing.JFrame
import javax.swing.WindowConstants

class CustomWars : Canvas(), Runnable {
    private fun start() {
        Thread(this).start()
    }

    override fun run() {
        init()

        while (true) {
            update.update()
            render.render()
        }
    }

    private fun init() {
        for (i in 0 until 35) {
            entity.add(EntityLaser(Color.GREEN, 0, WIDTH / 2))
            entity.add(EntityLaser(Color.ORANGE, WIDTH / 2, WIDTH))
        }
        for (i in 0 until 60) {
            entity.add(EntityGunner(Color.GREEN, 0, WIDTH / 2))
            entity.add(EntityGunner(Color.ORANGE, WIDTH / 2, WIDTH))
        }
        for (i in 0 until 33) {
            entity.add(EntityRocket(Color.GREEN, 0, WIDTH / 2))
            entity.add(EntityRocket(Color.ORANGE, WIDTH / 2, WIDTH))
        }
        for (i in 0 until 3) {
            entity.add(EntityMortar(Color.GREEN, 0, WIDTH / 2))
            entity.add(EntityMortar(Color.ORANGE, WIDTH / 2, WIDTH))
        }
        for (i in 0 until 25) {
            entity.add(EntitySniper(Color.GREEN, 0, WIDTH / 2))
            entity.add(EntitySniper(Color.ORANGE, WIDTH / 2, WIDTH))
        }
        for (i in 0 until 6) {
            entity.add(EntityShield(Color.GREEN, 0, WIDTH / 2))
            entity.add(EntityShield(Color.ORANGE, WIDTH / 2, WIDTH))
        }
    }

    companion object {
        private const val serialVersionUID = 7506117823203158228L

        internal var GAME = CustomWars()

        private const val NAME = "Custom Wars"

        internal const val WIDTH = 800
        internal const val HEIGHT = 600

        internal var entity = ArrayList<Entity>()
        internal var bullet = ArrayList<Bullet>()
        internal var effect = ArrayList<Effect>()

        private val render = Render()
        private val update = Update(GAME)

        @JvmStatic
        fun main(args: Array<String>) {
            GAME.preferredSize = Dimension(WIDTH, HEIGHT)
            val frame = JFrame(NAME)
            frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            frame.layout = BorderLayout()
            frame.add(GAME, BorderLayout.CENTER)
            frame.pack()
            frame.isResizable = false
            frame.isVisible = true
            GAME.start()
        }
    }
}

