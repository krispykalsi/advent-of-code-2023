abstract class AdventOfCodeSolver(private val day: Int) {
    protected open fun part1(input: List<String>): String = "Not Implemented"
    protected open fun part2(input: List<String>): String = "Not Implemented"

    fun solve(part1TestExpectedOutput: String = "", part2TestExpectedOutput: String = "") {
        val dayPadded = day.toString().padStart(2, '0')
        val testInput = readInput("Day${dayPadded}_test")
        val part1TestOutput = part1(testInput)
        check(actual = part1TestOutput, expected = part1TestExpectedOutput)
        val part2TestOutput = part2(testInput)
        check(actual = part2TestOutput, expected = part2TestExpectedOutput)
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