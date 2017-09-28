import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.util.ArrayList
import javax.swing.JFrame
import javax.swing.WindowConstants

class CustomWars : Canvas(), Runnable {
	internal var RUNNING: Boolean = false
	internal var ENTITY: ArrayList<Entity>? = ArrayList()
	internal var RENDER = Render(this)
	internal var UPDATE = Update(this)
	internal var FACTION_GREEN = 100
	internal var FACTION_ORANGE = 100
	
	override fun run(){
		init()
		while (RUNNING) {
			UPDATE.update()
			RENDER.render()
		}
	}
	
	private fun start() {
		RUNNING = true
		Thread(this).start()
	}

	internal fun init() {
		for (i in 0..FACTION_GREEN - 1) {
			ENTITY!!.add(Entity(Color.GREEN))
		}
		for (i in 0..FACTION_ORANGE - 1) {
			ENTITY!!.add(Entity(Color.ORANGE))
		}
	}

	companion object {
		private val serialVersionUID = 7506117823203158228L
		internal var GAME: CustomWars? = CustomWars()
		val NAME = "CustomWars"
		internal var WIDTH = 800
		internal var HEIGHT = 600
		internal var DEFAULT_MAX_HP = 3000.toDouble()
		internal var DEFAULT_SPEED = 3.toDouble()
		internal var DEFAULT_RADIUS_DETECT = 45.toDouble()
		internal var DEFAULT_RADIUS_ATTACK = 15.toDouble()
		internal var DEFAULT_DAMAGE = 12.toDouble()
		internal var DEFAULT_DIAMETER = 6.toDouble()
		internal var STATS = 15
		internal var MAX_HP_POINT = 150.toDouble()
		internal var SPEED_POINT = 0.4.toDouble()
		internal var RADIUS_DETECT_POINT = 1.2.toDouble()
		internal var RADIUS_ATTACK_POINT = 0.6.toDouble()
		internal var DAMAGE_POINT = 1.2f
		internal var GREEN = 0
		internal var ORANGE = 0
		fun main() {
			GAME!!.setPreferredSize(Dimension(WIDTH, HEIGHT))
			val frame = JFrame(CustomWars.NAME)
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
			frame.setLayout(BorderLayout())
			frame.add(GAME, BorderLayout.CENTER)
			frame.pack()
			frame.setResizable(false)
			frame.setVisible(true)
			GAME!!.start()
		}
	}
}