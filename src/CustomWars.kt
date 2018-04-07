import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.util.ArrayList

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
        for (i in 0 until 15) {
            entity.add(EntityLaser(Color.GREEN,0,WIDTH / 2))
            entity.add(EntityLaser(Color.ORANGE, WIDTH / 2, WIDTH))
        }
        for (i in 0 until 50) {
            entity.add(EntityGunner(Color.GREEN,0,WIDTH / 2))
            entity.add(EntityGunner(Color.ORANGE, WIDTH / 2, WIDTH))
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

        private val render = Render()
        private val update = Update(GAME)

        @JvmStatic
        fun main(args: Array<String>) {
            GAME.preferredSize = Dimension(WIDTH, HEIGHT)
            val frame = JFrame(CustomWars.NAME)
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

