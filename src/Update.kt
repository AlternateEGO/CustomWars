import java.awt.Color
import java.awt.geom.Point2D
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.Random

internal class Update(game: CustomWars?) {
	var UPDATE = System.currentTimeMillis()
	var GAME: CustomWars? = game
	var TICK = 35
	var REPUTATION: ArrayList<Double>? = ArrayList()

	fun update() {
		if (System.currentTimeMillis() - UPDATE >= 1000 / TICK) {
			UPDATE = System.currentTimeMillis()
			for (e in GAME!!.ENTITY.orEmpty()) {
				if (e.LIFE) {
					e.update()
					if (e.TARGET != null) {
						if (e.TARGET!!.LIFE) {
							if (Point2D.distance(e.X, e.Y, e.TARGET!!.X, e.TARGET!!.Y) <= e.RADIUS_ATTACK) {
								if (e.TARGET!!.HP > 0) {
									if (e.DAMAGE > e.TARGET!!.HP) {
										e.REPUTATION = e.REPUTATION.plus(e.TARGET!!.HP)
										e.TARGET!!.REPUTATION = e.TARGET!!.REPUTATION.minus(e.TARGET!!.HP)
									} else {
										e.REPUTATION = e.REPUTATION.plus(e.DAMAGE)
										e.TARGET!!.REPUTATION = e.TARGET!!.REPUTATION.minus(e.DAMAGE)
									}
									e.TARGET!!.HP.minus(e.DAMAGE)
									e.LAST_DAMAGE = System.currentTimeMillis()
									e.TARGET!!.LAST_DAMAGE = System.currentTimeMillis()
									if (e.TARGET!!.HP <= 0) {
										e.TARGET!!.LIFE = false
										e.TARGET = null
									}
								} else {
									e.TARGET!!.LIFE = false
									e.TARGET = null
								}
							}
						} else {
							e.TARGET = null
						}
					}
				}
			}
			var green = 0
			var orange = 0
			for (e in GAME!!.ENTITY.orEmpty())
				if (e.LIFE)
					if (e.FACTION === Color.GREEN) {
						green++
					} else
						orange++
			if (green == 0 || orange == 0) {
				GAME!!.RUNNING = false
				if (green > orange) {
					CustomWars.GREEN++
				} else
					CustomWars.ORANGE++
				var GREEN = ArrayList<Entity>()
				for (e in GAME!!.ENTITY.orEmpty()) {
					if (e.FACTION === Color.GREEN) GREEN.add(e)
				}
				Collections.sort(GREEN, EntityComparator())
				REPUTATION!!.add(GREEN.get(0).REPUTATION)
				var sr = 0.toDouble()
				for (f in REPUTATION!!) {
					sr += f
				}
				System.out.println(sr / REPUTATION!!.size)
				var entity = ArrayList<Entity>()
				for (i in 0..7) {
					for (j in 0..9) {
						var e0 = GREEN.get(j)
						var e1 = e0
						while (e0 === e1) {
							e1 = GREEN.get(Random().nextInt(10))
						}
						var par0: Double
						var par1: Double
						var par2: Double
						var par3: Double
						var par4: Double
						var b = Random().nextBoolean()
						if (b) {
							par0 = e0.MAX_HP
						} else
							par0 = e1!!.MAX_HP
						b = Random().nextBoolean()
						if (b) {
							par1 = e0.SPEED
						} else
							par1 = e1!!.SPEED
						b = Random().nextBoolean()
						if (b) {
							par2 = e0.RADIUS_DETECT
						} else
							par2 = e1!!.RADIUS_DETECT
						b = Random().nextBoolean()
						if (b) {
							par3 = e0.RADIUS_ATTACK
						} else
							par3 = e1!!.RADIUS_ATTACK
						b = Random().nextBoolean()
						if (b) {
							par4 = e0.DAMAGE
						} else
							par4 = e1!!.DAMAGE
						entity.add(Entity(par0, par1, par2, par3, par4, Color.GREEN))
					}
				}
				for (i in GAME!!.ENTITY!!.size - 1 downTo 0) {
					GAME!!.ENTITY!!.removeAt(i)
				}
				GAME!!.ENTITY = entity
				for (i in 0..GAME!!.FACTION_GREEN - 80 - 1) {
					GAME!!.ENTITY!!.add(Entity(Color.GREEN))
				}
				for (i in 0..GAME!!.FACTION_ORANGE - 1) {
					GAME!!.ENTITY!!.add(Entity(Color.ORANGE))
				}
				GAME!!.RUNNING = true
			}
		}
	}

	inner class EntityComparator : Comparator<Entity> {
		override fun compare(e0: Entity?, e1: Entity?): Int {
			val rep0 = e0!!.REPUTATION
			val rep1 = e1!!.REPUTATION
			return rep1.compareTo(rep0)
		}
	}
}