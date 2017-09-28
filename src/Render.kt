import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D
import java.awt.image.BufferStrategy

internal class Render(game: CustomWars?) {
	var RENDER = System.currentTimeMillis()
	var GAME: CustomWars? = null
	var TICK = 20

	init {
		GAME = game
	}

	fun render() {
		if (System.currentTimeMillis() - RENDER >= 1000 / TICK) {
			RENDER = System.currentTimeMillis()
			val bs = GAME!!.getBufferStrategy()
			if (bs == null) {
				GAME!!.createBufferStrategy(2)
				GAME!!.requestFocus()
				return
			}
			val graphics = bs.getDrawGraphics() as Graphics2D
			graphics.setColor(Color.BLACK)
			graphics.fillRect(0, 0, GAME!!.getWidth(), GAME!!.getHeight())
			GAME!!.ENTITY!!.stream().filter({ e -> e!!.TARGET != null }).filter({ e -> Point2D.distance(e!!.X, e.Y, e.TARGET!!.X, e.TARGET!!.Y) <= e.RADIUS_ATTACK && e.TARGET!!.LIFE }).filter({ e -> e!!.TARGET!!.HP > 0 }).forEach({ e ->
				graphics.setColor(e!!.COLOR_DRAW)
				graphics.draw(Line2D.Double(e.X, e.Y, e.TARGET!!.X, e.TARGET!!.Y))
			})
			for (e in GAME!!.ENTITY.orEmpty()) {
				if (e.LIFE) e.render(graphics)
			}
			var green = 0
			var orange = 0
			for (e in GAME!!.ENTITY.orEmpty()) {
				if (e.LIFE)
					if (e.FACTION === Color.GREEN) {
						green++
					} else
						orange++
			}
			graphics.setColor(Color.WHITE)
			graphics.drawString("Green Wins = " + CustomWars.GREEN, 20, 18)
			graphics.drawString("Orange Wins = " + CustomWars.ORANGE, 20, 29)
			graphics.drawString("Green = " + green, 700, 18)
			graphics.drawString("Orange = " + orange, 693, 29)
			graphics.dispose()
			bs.show()
		}
	}
}