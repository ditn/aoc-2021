fun main() {

    fun part1(input: List<String>): Int {
        val gamma = input.computeGamma()
        val epsilon = input.computeEpsilon()

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val o2Generator = input.computeO2Generator()
        val co2Scrubber = input.computeCo2Scrubber()

        return o2Generator * co2Scrubber
    }

    val dayThree = readInput("Day03")
    val testInput = readInput("Day03_test")

    // Part 1
    check(part1(dayThree) == 198)
    check(part1(testInput) == 3958484)

    // Part 2
    check(part2(dayThree) == 230)
    check(part2(testInput) == 1613181)
}

private fun List<String>.parseList(mostCommon: Boolean): Int =
    List(first().length) { index -> toBinaryChar(index, mostCommon) }
        .binaryListToInt()

private fun List<String>.toBinaryChar(index: Int, findMax: Boolean): Char = map { it[index] }
    .let { if (it.count { char -> char == '1' } > it.size / 2) findMax else !findMax }
    .toChar()

private fun List<String>.computeBy(mostCommon: Boolean): Int {
    val mutable = toMutableList()
    val output = mutableListOf<Char>()

    for (index in 0 until first().length) {
        if (mutable.size == 1) {
            output.add(mutable.first()[index])
            continue
        }

        val digit = mutable
            .map { it[index] }
            .groupBy { it }
            .values
            .toList()
            .let { lists ->
                if (lists.size > 1 && lists.first().size == lists[1].size) {
                    mostCommon.toChar()
                } else {
                    lists.aggregate(mostCommon)
                }
            }

        output.add(digit)

        mutable.removeIf { it[index] != digit }
    }

    return output.binaryListToInt()
}

private fun List<Char>.binaryListToInt(): Int = joinToString("")
    .let { Integer.parseInt(it, 2) }

private fun List<List<Char>>.aggregate(mostCommon: Boolean): Char = if (mostCommon) {
    maxByOrNull(List<Char>::size)!!.first()
} else {
    minByOrNull(List<Char>::size)!!.first()
}

private fun Boolean.toChar(): Char = if (this) '1' else '0'

private fun List<String>.computeGamma(): Int = parseList(mostCommon = true)
private fun List<String>.computeEpsilon(): Int = parseList(mostCommon = false)
private fun List<String>.computeO2Generator(): Int = computeBy(mostCommon = true)
private fun List<String>.computeCo2Scrubber(): Int = computeBy(mostCommon = false)