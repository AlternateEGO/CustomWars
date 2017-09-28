import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.Random

internal class Entity {
	var X = 0.toDouble()
	var Y = 0.toDouble()
	var SPEED = 0.toDouble()
	var PATH_X = 0.toDouble()
	var PATH_Y = 0.toDouble()
	var DIAMETER = 0.toDouble()
	var FACTION : Color?
	var COLOR_DRAW: Color? = null
	var MAX_HP = 0.toDouble()
	var HP = 0.toDouble()
	var RADIUS_ATTACK = 0.toDouble()
	var RADIUS_DETECT = 0.toDouble()
	var DAMAGE = 0.toDouble()
	var LIFE = true
	var TARGET: Entity? = null
	var REPUTATION = 0.toDouble()
	var LAST_DAMAGE = System.currentTimeMillis()

	constructor(color: Color?) {
		X = Random().nextInt(CustomWars.WIDTH).toDouble()
		Y = Random().nextInt(CustomWars.HEIGHT).toDouble()
		PATH_X = X
		PATH_Y = Y
		HP = CustomWars.DEFAULT_MAX_HP
		MAX_HP = CustomWars.DEFAULT_MAX_HP
		RADIUS_DETECT = CustomWars.DEFAULT_RADIUS_DETECT
		RADIUS_ATTACK = CustomWars.DEFAULT_RADIUS_ATTACK
		DAMAGE = CustomWars.DEFAULT_DAMAGE
		SPEED = CustomWars.DEFAULT_SPEED
		DIAMETER = CustomWars.DEFAULT_DIAMETER
		FACTION = color
		for (i in 0..CustomWars.STATS) {
			var rand = Random().nextInt(5)
			when (rand) {
				0 -> {
					MAX_HP += CustomWars.MAX_HP_POINT
					SPEED += CustomWars.SPEED_POINT
					RADIUS_DETECT += CustomWars.RADIUS_DETECT_POINT
					RADIUS_ATTACK += CustomWars.RADIUS_ATTACK_POINT
					DAMAGE += CustomWars.DAMAGE_POINT
				}
				1 -> {
					SPEED += CustomWars.SPEED_POINT
					RADIUS_DETECT += CustomWars.RADIUS_DETECT_POINT
					RADIUS_ATTACK += CustomWars.RADIUS_ATTACK_POINT
					DAMAGE += CustomWars.DAMAGE_POINT
				}
				2 -> {
					RADIUS_DETECT += CustomWars.RADIUS_DETECT_POINT
					RADIUS_ATTACK += CustomWars.RADIUS_ATTACK_POINT
					DAMAGE += CustomWars.DAMAGE_POINT
				}
				3 -> {
					RADIUS_ATTACK += CustomWars.RADIUS_ATTACK_POINT
					DAMAGE += CustomWars.DAMAGE_POINT
				}
				4 -> DAMAGE += CustomWars.DAMAGE_POINT
			}
		}
	}

	constructor(hp: Double, speed: Double, radius_detect: Double, radius_attack: Double, damage: Double, color: Color?) {
		X = Random().nextInt(CustomWars.WIDTH).toDouble()
		Y = Random().nextInt(CustomWars.HEIGHT).toDouble()
		PATH_X = X
		PATH_Y = Y
		HP = hp
		MAX_HP = hp
		SPEED = speed
		RADIUS_DETECT = radius_detect
		RADIUS_ATTACK = radius_attack
		DAMAGE = damage
		DIAMETER = CustomWars.DEFAULT_DIAMETER
		FACTION = color
	}

	fun render(graphics: Graphics2D?) {
		graphics!!.setColor(COLOR_DRAW)
		graphics.draw(Ellipse2D.Double(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER))
		graphics.fillOval((X - DIAMETER / 2).toInt(), (Y - DIAMETER / 2).toInt(), DIAMETER.toInt(), DIAMETER.toInt())
	}

	fun update() {
		if (System.currentTimeMillis() - LAST_DAMAGE > 10000) {
			X = Random().nextInt(CustomWars.WIDTH).toDouble()
			Y = Random().nextInt(CustomWars.HEIGHT).toDouble()
		}
		try {
			COLOR_DRAW = Color(FACTION!!.getRed(), FACTION!!.getGreen(), FACTION!!.getBlue(), (HP / MAX_HP * 100f * 255f / 100).toInt())
		} catch (e: IllegalArgumentException) {
			COLOR_DRAW = Color(FACTION!!.getRed(), FACTION!!.getGreen(), FACTION!!.getBlue(), 255)
		}
		CustomWars.GAME!!.ENTITY!!.stream().filter({ e -> this !== e && this.FACTION !== e!!.FACTION && e!!.LIFE }).filter({ e -> Point2D.distance(X, Y, e!!.X, e.Y) <= RADIUS_DETECT }).forEach({ e ->
			if (TARGET != null) {
				if (Point2D.distance(X, Y, e!!.X, e.Y) < Point2D.distance(X, Y, TARGET!!.X, Y)) {
					TARGET = e
				}
			} else {
				TARGET = e
			}
		})
		if (TARGET != null)
			if (Point2D.distance(X, Y, TARGET!!.X, TARGET!!.Y) > RADIUS_DETECT) {
				TARGET = null
			}
		move()
	}

	private fun move() {
		if (TARGET != null) {
			var distance = Point2D.distance(X, Y, TARGET!!.X, TARGET!!.Y)
			var distance_x = Point2D.distance(X, 0.toDouble(), TARGET!!.X, 0.toDouble())
			var distance_y = Point2D.distance(0.toDouble(), Y, 0.toDouble(), TARGET!!.Y)
			var speed_x = distance_x / distance
			var speed_y = distance_y / distance
			if (distance <= RADIUS_ATTACK - 2 || HP / MAX_HP <= 0.1) {
				if (X > TARGET!!.X) {
					X += speed_x * SPEED
				} else
					X -= speed_x * SPEED
				if (Y > TARGET!!.Y) {
					Y += speed_y * SPEED
				} else
					Y -= speed_y * SPEED
			} else {
				if (X < TARGET!!.X) {
					X += speed_x * SPEED
				} else
					X -= speed_x * SPEED
				if (Y < TARGET!!.Y) {
					Y += speed_y * SPEED
				} else
					Y -= speed_y * SPEED
			}
		} else {
			var distance = Point2D.distance(X, Y, PATH_X, PATH_Y)
			if (distance < 1) {
				PATH_X = Random().nextInt(CustomWars.WIDTH).toDouble()
				PATH_Y = Random().nextInt(CustomWars.HEIGHT).toDouble()
				distance = Point2D.distance(X, Y, PATH_X, PATH_Y)
			}
			var distance_x = Point2D.distance(X, 0.toDouble(), PATH_X, 0.toDouble())
			var distance_y = Point2D.distance(0.toDouble(), Y, 0.toDouble(), PATH_Y)
			var speed_x = distance_x / distance
			var speed_y = distance_y / distance
			if (X < PATH_X) {
				if (distance_x > speed_x * SPEED) {
					X += speed_x * SPEED
				} else
					X += distance_x
			} else {
				if (distance_x > speed_x * SPEED) {
					X -= speed_x * SPEED
				} else
					X -= distance_x
			}
			if (Y < PATH_Y) {
				if (distance_y > speed_y * SPEED) {
					Y += speed_y * SPEED
				} else
					Y += distance_y
			} else {
				if (distance_y > speed_y * SPEED) {
					Y -= speed_y * SPEED
				} else
					Y -= distance_y
			}
		}
	}
}