import java.io.File
import java.lang.Math.abs
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

//private fun readInt() = readln().toInt() // single int
//private fun readStrings() = readln().split(" ") // list of strings
//private fun readInts() = readStrings().map { it.toInt() } // list of ints

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

data class Point(val x: Int, val y: Int) {

    companion object {
        val ORIGIN = Point(0, 0)
    }

    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    fun move(direction: Direction, distance: Int = 1) = when (direction) {
        Direction.EAST -> Point(x + distance, y)
        Direction.WEST -> Point(x - distance, y)
        Direction.NORTH -> Point(x, y - distance)
        Direction.SOUTH -> Point(x, y + distance)
    }

    fun manhattanDistanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)

    fun getAdjacentSides(): List<Point> = listOf(
        Point(x, y - 1), Point(x - 1, y), Point(x + 1, y), Point(x, y + 1),
    )

    fun getAdjacent(): List<Point> = listOf(
        Point(x - 1, y - 1), Point(x, y - 1), Point(x + 1, y - 1),
        Point(x - 1, y), Point(x + 1, y),
        Point(x - 1, y + 1), Point(x, y + 1), Point(x + 1, y + 1),
    )

    fun isAdjacentSide(other: Point) = manhattanDistanceTo(other) == 1

}

enum class Turn {
    RIGHT, LEFT;

    override fun toString() = when (this) {
        RIGHT -> "→"
        LEFT -> "←"
    }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun rotate(turn: Turn, times: Int): Direction {
        return (0 until (times % 4)).toList().fold(this) { acc, _ -> acc.rotate(turn) }
    }

    fun rotate(turn: Turn): Direction {
        return if (turn == Turn.RIGHT) when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        } else when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }
    }

    fun reverse() = when (this) {
        NORTH -> SOUTH
        SOUTH -> NORTH
        WEST -> EAST
        EAST -> WEST
    }

    override fun toString() = when (this) {
        NORTH -> "↑"
        EAST -> "→"
        SOUTH -> "↓"
        WEST -> "←"
    }
}
