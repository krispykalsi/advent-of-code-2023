private data class Race(val time: Long, val recordDistance: Long) {
    private fun willWin(holdDuration: Long): Boolean {
        val timeLeft = time - holdDuration
        val distTraveled = timeLeft * holdDuration
        return distTraveled > recordDistance
    }

    fun findWinningWays(): Long {
        var winnningWays = 0L
        for (i in 1..<time) {
            if (willWin(holdDuration = i)) {
                winnningWays += 1
            }
        }
        return winnningWays
    }
}

object Day06 : AdventOfCodeSolver(day = 6) {
    override fun part1(input: List<String>): String {
        val races = parsePart1(input)
        var product = 1L
        for (race in races) {
            val winnningWays = race.findWinningWays()
            if (winnningWays != 0L) {
                product *= winnningWays
            }
        }
        return product.toString()
    }

    override fun part2(input: List<String>): String {
        val races = parsePart2(input)
        var product = 1L
        for (race in races) {
            val winnningWays = race.findWinningWays()
            if (winnningWays != 0L) {
                product *= winnningWays
            }
        }
        return product.toString()
    }

    private fun parsePart1(input: List<String>): List<Race> {
        val timeAndDists = input.map { line ->
            line.split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        }
        return List(timeAndDists[0].size) { i -> Race(timeAndDists[0][i], timeAndDists[1][i]) }
    }

    private fun parsePart2(input: List<String>): List<Race> {
        val timeAndDist = input.map { line ->
            line.split(":")[1].trim().split("\\s+".toRegex()).joinToString("").toLong()
        }
        return listOf(Race(timeAndDist[0], timeAndDist[1]))
    }
}

fun main() {
    Day06.solve("288", "71503")
}