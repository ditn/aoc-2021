import java.util.*

fun main() {

    fun part1(input: List<String>): Int = input
        .parseInput()
        .groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        )
        .mapValues { it.value.sum() }
        .run {
            var depth = 0

            depth -= getOrDefault(Direction.Up, 0)
            depth += getOrDefault(Direction.Down, 0)

            depth * getOrDefault(Direction.Forward, 0)
        }

    fun part2(input: List<String>): Int = input
        .parseInput()
        .fold(Result()) { acc, (direction, amount) ->
            when (direction) {
                Direction.Up -> acc.aim -= amount
                Direction.Down -> acc.aim += amount
                Direction.Forward -> {
                    acc.position += amount
                    acc.depth += acc.aim * amount
                }
            }

            acc
        }
        .run(Result::compute)

    val dayTwo = readInput("Day02")
    val testInput = readInput("Day02_test")

    // Part 1
    check(part1(dayTwo) == 150)
    check(part1(testInput) == 2272262)

    // Part 2
    check(part2(dayTwo) == 900)
    check(part2(testInput) == 2134882034)
}

private data class Result(
    var depth: Int = 0,
    var aim: Int = 0,
    var position: Int = 0,
) {
    fun compute(): Int = depth * position
}

private enum class Direction {
    Up,
    Down,
    Forward;
}

private fun List<String>.parseInput() = map {
    val (direction, count) = it.split(" ")

    Direction.valueOf(direction.capitalise()) to count.toInt()
}

private fun String.capitalise(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

