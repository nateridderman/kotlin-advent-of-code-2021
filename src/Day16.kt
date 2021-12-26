fun main() {

    var totalVersion = 0

    fun parseLiteral(bits: MutableList<Char>): MutableList<Char> {
        var numberDigits = String()
        var returnList = bits
        while (returnList.removeAt(0) == '1') {
            numberDigits += returnList.take(4).joinToString("")
            returnList = returnList.subList(4, returnList.size)
        }
        numberDigits += returnList.take(4).joinToString("")
        //println(numberDigits.toInt(2))
        return returnList.subList(4, returnList.size)
    }

    fun parsePacket(origBits: MutableList<Char>): MutableList<Char> {
        var bits = origBits
        val version = bits.take(3).joinToString("").toInt(2)
        totalVersion += version
        bits = bits.subList(3, bits.size)
        val typeId = bits.take(3).joinToString("").toInt(2)
        bits = bits.subList(3, bits.size)
        if (typeId == 4) {
            bits = parseLiteral(bits)
        } else {
            val lengthTypeId = bits.removeAt(0)
            if (lengthTypeId == '0') {
                //then the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
                val bitsInSubPackets = bits.take(15).joinToString("").toInt(2)
                bits = bits.subList(15, bits.size)
                val expectedSize = bits.size - bitsInSubPackets
                while (bits.size > expectedSize) {
                    //parse another character
                    bits = parsePacket(bits)
                }
            } else { //eq '1'
                //then the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet.
                val numOfSubPackets = bits.take(11).joinToString("").toInt(2)
                bits = bits.subList(11, bits.size)
                repeat(numOfSubPackets) {
                    bits = parsePacket(bits)
                }
            }
        }
        return bits
    }

    fun part1(input: List<String>): Int {
        totalVersion = 0
        var bits = input[0].toCharArray()
            .map { it.digitToInt(16).toString(2).padStart(4, '0')}
            .joinToString("")
        var bitsList = mutableListOf<Char>()
        bitsList.addAll(bits.toCharArray().toList())
//        while (bitsList.isNotEmpty()) {
            bitsList = parsePacket(bitsList)
//        }
        return totalVersion
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day16_test")
    check(part1(testInput) == 6)
    testInput = readInput("Day16_test1")
    check(part1(testInput) == 16)

    val input = readInput("Day16")
    println(part1(input))
//    println(part2(input))
}
