import java.util.*

fun main() {

    data class Node(val point: Point, var distance: Int) : Comparable<Node> {
        override operator fun compareTo(other: Node): Int {
            return this.distance.compareTo(other.distance)
        }
    }

    fun solve(width: Int, height: Int,
              shortestDistanceQueue: PriorityQueue<Node>, riskLevelGrid: MutableMap<Point, Int>): Int {

        shortestDistanceQueue.remove(Node(Point(0, 0), Int.MAX_VALUE))
        shortestDistanceQueue.add(Node(Point(0, 0), 0))
        val visited = mutableMapOf<Point, Int>()

        Point(0, 0).getAdjacentSides()
        riskLevelGrid[Point(0, 0)]
        var currentNode: Node
        while (!visited.contains(Point(width - 1, height - 1))) {
            currentNode = shortestDistanceQueue.poll()

            currentNode.point.getAdjacentSides()
                .filter { riskLevelGrid.contains(it) && !visited.contains(it) }.map {
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
        return visited.get(Point(width - 1, height - 1))!!
    }

    fun part1(input: List<String>): Int {
        val riskLevelGrid = input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Point(x, y) to it.digitToInt() } }.toMap().toMutableMap()
        val shortestDistanceQueue: PriorityQueue<Node> = PriorityQueue()

        shortestDistanceQueue.addAll(input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Node(Point(x, y), Int.MAX_VALUE) } })

        val width = input[0].length
        val height = input.size

        return solve(width, height, shortestDistanceQueue, riskLevelGrid)
    }

    fun part2(input: List<String>): Int {

        val digits = listOf(1,2,3,4,5,6,7,8,9)

        fun getRotatedDigit(x: Int, y: Int, digit: Int) : Int {
            var newIndex = digit - 1 + x + y
            if (newIndex >= digits.size) {
                newIndex %= digits.size
            }
            return digits[newIndex]
        }

        var width = input[0].length
        var height = input.size

        val riskLevelGrid: MutableMap<Point, Int> = mutableMapOf()
        val shortestDistanceQueue: PriorityQueue<Node> = PriorityQueue()

        for (i in 0..4) {
            for (j in 0..4) {
                riskLevelGrid.putAll(input.flatMapIndexed { y , line -> line.mapIndexed { x, it -> Point(x + width * i, y + height * j) to getRotatedDigit(i, j, it.digitToInt()) } }.toMap())
                shortestDistanceQueue.addAll(input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Node(Point(x + width * i, y + height * j), Int.MAX_VALUE) } })
            }
        }

        return solve(width*5, height*5, shortestDistanceQueue, riskLevelGrid)
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
