fun main() {
    fun part1(input: List<String>): Int {
        var ourLanternFish = input[0].split(",").map { it.toInt() }.toMutableList()
        (1 .. 80).map {
            val newLanternFish = mutableListOf<Int>()
            val iterate = ourLanternFish.listIterator()
            while (iterate.hasNext()) {
                val oldValue = iterate.next()
                if (oldValue > 0) {
                    iterate.set(oldValue - 1)
                } else {
                    iterate.set(6)
                    newLanternFish.add(8)
                }
            }
            ourLanternFish.addAll(newLanternFish)
        }

        return ourLanternFish.size
    }

    fun part2(input: List<String>): Long {
        var ourLanternFishMap = input[0].split(",").groupBy { it }.map { it.key.toInt() to it.value.size.toLong() }.toMap()

        (1 .. 256).map {
            val newLanternFishMap = hashMapOf<Int, Long>()
            ourLanternFishMap.map {
                if (it.key > 0) {
                    newLanternFishMap[it.key - 1] = it.value + newLanternFishMap.getOrDefault(it.key-1,0)
                } else {
                    newLanternFishMap[6] = it.value + newLanternFishMap.getOrDefault(6,0)
                    newLanternFishMap[8] = it.value
                }
            }
            ourLanternFishMap = newLanternFishMap
            println(ourLanternFishMap.map { it.value }.sum())
        }

        return ourLanternFishMap.map { it.value }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
