abstract class AdventOfCodeSolver(private val day: Int) { 
    protected open fun part1(input: List<String>): String = "Not Implemented"
    protected open fun part2(input: List<String>): String = "Not Implemented"
    
    fun solve(testOutput: String) {
        val dayPadded = day.toString().padStart(2, '0')
        val testInput = readInput("Day${dayPadded}_test")
        check(part1(testInput) == testOutput)
        val input = readInput("Day$dayPadded")
        print("Part 1 - ${part1(input)}\n")
        print("Part 2 - ${part2(input)}\n")
    }
}