import java.util.*
import kotlin.collections.HashSet

fun main() {

    fun part1(input: List<String>): Int {
        val riskLevelGrid = input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Point(x, y) to it.digitToInt() } }.toMap().toMutableMap()
        val shortestDistanceGrid = input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Point(x, y) to Int.MAX_VALUE } }.toMap().toMutableMap()
        shortestDistanceGrid[Point(0,0)] = 0
        val visited = HashSet<Point>()

        val width = input[0].length
        val height = input.size
        Point(0,0).getAdjacentSides()
        riskLevelGrid[Point(0,0)]
        var currentNode: Point
        while (!visited.contains(Point(width-1, height-1))) {

            //select the unvisited node that is marked with the smallest tentative distance, set it as the new current node
            currentNode = shortestDistanceGrid.entries.filter { !visited.contains(it.key) }.minByOrNull { it.value }!!.key

            // For the current node, consider all of its unvisited neighbors and calculate their tentative distances
            // through the current node. Compare the newly calculated tentative distance to the current assigned value
            // and assign the smaller one. For example, if the current node A is marked with a distance of 6, and the edge
            // connecting it with a neighbor B has length 2, then the distance to B through A will be 6 + 2 = 8.
            // If B was previously marked with a distance greater than 8 then change it to 8. Otherwise, the current value will be kept.
            currentNode.getAdjacentSides().filter { riskLevelGrid.contains(it) && !visited.contains(it) }.map {
                val tentativeDistance = shortestDistanceGrid[currentNode]!! + riskLevelGrid[it]!!
                if (tentativeDistance < shortestDistanceGrid[it]!!) {
                    shortestDistanceGrid[it] = tentativeDistance
                }
            }

            //When we are done considering all of the unvisited neighbors of the current node, mark the current node as visited and
            // remove it from the unvisited set. A visited node will never be checked again.
            visited.add(currentNode)
        }
        return shortestDistanceGrid[Point(width-1, height-1)]!!
    }

    data class Node(val point: Point, var distance: Int) : Comparable<Node> {
        override operator fun compareTo(other: Node): Int {
            return this.distance.compareTo(other.distance)
        }
    }

    fun part2(input: List<String>): Int {
        val riskLevelGrid: MutableMap<Point, Int> = mutableMapOf()

        val digits = listOf(1,2,3,4,5,6,7,8,9)
        var width = input[0].length
        var height = input.size

        fun getRotatedDigit(x: Int, y: Int, digit: Int) : Int {
            var newIndex = digit - 1 + x + y
            if (newIndex >= digits.size) {
                newIndex %= digits.size
            }
            return digits[newIndex]
        }

        val shortestDistanceQueue: PriorityQueue<Node> = PriorityQueue()

        for (i in 0..4) {
            for (j in 0..4) {
                riskLevelGrid.putAll(input.flatMapIndexed { y , line -> line.mapIndexed { x, it -> Point(x + width * i, y + height * j) to getRotatedDigit(i, j, it.digitToInt()) } }.toMap())
                shortestDistanceQueue.addAll(input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Node(Point(x + width * i, y + height * j), Int.MAX_VALUE) } })
            }
        }

        width *= 5
        height *= 5

        shortestDistanceQueue.remove(Node(Point(0,0), Int.MAX_VALUE))
        shortestDistanceQueue.add(Node(Point(0,0), 0))
        val visited = mutableMapOf<Point, Int>()

        Point(0,0).getAdjacentSides()
        riskLevelGrid[Point(0,0)]
        var currentNode: Node
        while (!visited.contains(Point(width-1, height-1))) {
            currentNode = shortestDistanceQueue.poll()

            currentNode.point.getAdjacentSides().filter { riskLevelGrid.contains(it) && !visited.contains(it) }.map {
                val adjacentNode = shortestDistanceQueue.first { node -> node.point == it }
                val tentativeDistance = currentNode.distance + riskLevelGrid[it]!!
                if (tentativeDistance < adjacentNode.distance) {
                    shortestDistanceQueue.remove(adjacentNode)
                    shortestDistanceQueue.add(Node(adjacentNode.point, tentativeDistance))
                }
            }

//            if (currentNode.point.x%10 == 0 && currentNode.point.y%10 == 0) {
//                println("distance ${currentNode.point.x + currentNode.point.y} ")
//            }
            visited.put(currentNode.point, currentNode.distance)
        }
        return visited.get(Point(width-1, height-1))!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    val testInput2 = readInput("Day15_test2")
    check(part1(testInput2) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
