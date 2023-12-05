object Day03 : AdventOfCodeSolver(day = 3) {
    override fun part1(input: List<String>): String {
        val heatMap = makeHeatMapForSymbolAdjacentPositions(input)
        val validNums = mutableListOf<Int>()
        for ((i, line) in input.withIndex()) {
            var num = ""
            var isNextToSymbol = false
            for ((j, ch) in line.withIndex()) {
                if (ch.isDigit()) {
                    num += ch
                    if (heatMap[i][j] > 0) {
                        isNextToSymbol = true
                    }
                } else if (num.isNotBlank()) {
                    if (isNextToSymbol) {
                        validNums.add(num.toInt())
                        isNextToSymbol = false
                    }
                    num = ""
                }
            }
            if (num.isNotBlank() && isNextToSymbol) {
                validNums.add(num.toInt())
            }
        }
        val sum = validNums.reduce { a, b -> a + b }
        return sum.toString()
    }

    override fun part2(input: List<String>): String {
        val gearRatios = mutableListOf<Int>()
        val numberFinder = makeNumberFinder(input)
        for ((i, line) in input.withIndex()) {
            for ((j, ch) in line.withIndex()) {
                if (ch != '*') continue
                val validPositions = getValidAdjacentPositions(i, j, rowRange = input.indices, columnRange = line.indices)
                val adjacentNums: MutableSet<Pair<Int, IntRange>> = mutableSetOf()
                for ((x, y) in validPositions) {
                    val pair = numberFinder[x][y] ?: continue
                    adjacentNums.add(pair)
                }
                if (adjacentNums.count() == 2) {
                    gearRatios.add(adjacentNums.fold(1) { product, (num, _) -> product * num })
                }
            }
        }
        val sum = gearRatios.reduce { a, b -> a + b }
        return sum.toString()
    }

    private fun makeNumberFinder(input: List<String>): Array<Array<Pair<Int, IntRange>?>> {
        val findingGrid = Array(input.size) {
            Array<Pair<Int, IntRange>?>(input[it].length) { null }
        }
        for ((i, line) in input.withIndex()) {
            var num = ""
            var numStartingIndex = -1
            for ((j, ch) in line.withIndex()) {
                if (ch.isDigit()) {
                    if (num.isBlank()) {
                        numStartingIndex = j
                    }
                    num += ch
                } else if (num.isNotBlank()) {
                    val number = num.toInt()
                    val range = numStartingIndex..<j
                    for (k in range) {
                        findingGrid[i][k] = number to range
                    }
                    num = ""
                }
            }
            if (num.isNotBlank()) {
                val number = num.toInt()
                val range = numStartingIndex..line.lastIndex
                for (k in range) {
                    findingGrid[i][k] = number to range
                }
            }
        }
        return findingGrid
    }

    private fun makeHeatMapForSymbolAdjacentPositions(input: List<String>): Array<IntArray> {
        val heatMap = Array(input.size) {
            IntArray(input[it].length)
        }
        for ((i, line) in input.withIndex()) {
            for ((j, ch) in line.withIndex()) {
                if (ch == '.' || ch.isDigit()) continue
                val validPositions = getValidAdjacentPositions(i, j, rowRange = input.indices, columnRange = line.indices)
                for ((ii, jj) in validPositions) {
                    heatMap[ii][jj] += 1
                }
            }
        }
        return heatMap
    }

    private fun getValidAdjacentPositions(i: Int, j: Int, rowRange: IntRange, columnRange: IntRange): List<Pair<Int, Int>> {
        val directions = listOf(
                -1 to 0,    // left
                1 to 0,     // right
                0 to -1,    // up
                0 to 1,     // down
                -1 to -1,   // top-left
                -1 to 1,    // top-right
                1 to -1,    // bottom-left
                1 to 1      // bottom-right
        )
        return directions
                .mapNotNull {
                    val x = it.first + i
                    val y = it.second + j
                    if (x in rowRange && y in columnRange) (x to y) else null
                }
    }
}

fun main() {
    Day03.solve("4361", "467835")
}