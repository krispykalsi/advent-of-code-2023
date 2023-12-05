abstract class AdventOfCodeSolver(private val day: Int) {
    protected open fun part1(input: List<String>): String = "Not Implemented"
    protected open fun part2(input: List<String>): String = "Not Implemented"

    fun solve(part1TestExpectedOutput: String? = null, part2TestExpectedOutput: String? = null) {
        val dayPadded = day.toString().padStart(2, '0')
        val testInput = readInput("Day${dayPadded}_test")
        part1TestExpectedOutput?.let {
            check(actual = part1(testInput), expected = it)
        }
        part2TestExpectedOutput?.let {
            check(actual = part2(testInput), expected = it)
        }
        val input = readInput("Day$dayPadded")
        print("Part 1 - ${part1(input)}\n")
        print("Part 2 - ${part2(input)}\n")
    }

    private fun check(actual: String, expected: String) {
        check(actual == expected) {
            "Expected: $expected, Actual: $actual"
        }
    }
}