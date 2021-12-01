fun main() {

    fun part1(input: List<String>): Int = input.map(String::toInt)
        .sumIncreases()

    fun part2(input: List<String>): Int = input.map(String::toInt)
        .windowed(size = 3)
        .map(List<Int>::sum)
        .sumIncreases()


    val dayOne = readInput("Day01")
    val testInput = readInput("Day01_test")

    // Part 1
    check(part1(dayOne) == 7)
    check(part1(testInput) == 1752)

    // Part 2
    check(part2(dayOne) == 5)
    check(part2(testInput) == 1781)
}

private fun List<Int>.sumIncreases() = foldIndexed(0) { index, acc, i ->
    if (index == 0) return@foldIndexed 0

    if (i > this[index - 1]) acc + 1 else acc
}
