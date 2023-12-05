private typealias GameSet = Map<String, Int>

private data class Game(
        val id: Int,
        val sets: List<GameSet>
)

object Day02 : AdventOfCodeSolver(2) {
    override fun part1(input: List<String>): String {
        val loadedBag = mapOf(
                "red" to 12,
                "green" to 13,
                "blue" to 14,
        )
        val games = parse(input)
        val possibleGames = games.filter { game ->
            for (set in game.sets) {
                for (cube in set.keys) {
                    if ((set[cube] ?: 0) > (loadedBag[cube] ?: 0)) {
                        return@filter false
                    }
                }
            }
            true
        }
        val sumOfIds = possibleGames.fold(0) { sum, game -> sum + game.id }
        return sumOfIds.toString()
    }

    override fun part2(input: List<String>): String {
        val colors = listOf("red", "green", "blue")
        val games = parse(input)
        var sum = 0
        for (game in games) {
            val minGameSet = colors.associateWith { 0 }.toMutableMap()
            for (set in game.sets) {
                for ((color, quantity) in set) {
                    if ((minGameSet[color] ?: 0) < quantity) {
                        minGameSet[color] = quantity
                    }
                }
            }
            val power = minGameSet.values.fold(1) { product, cubes ->
                product * (if (cubes != 0) cubes else 1)
            }
            sum += power
        }
        return sum.toString()
    }

    private fun parse(input: List<String>): List<Game> {
        return input.map { game ->
            val id = Regex("^Game (\\d+)").find(game)!!.groupValues[1].toInt()
            val sets = game
                    .substring(7)
                    .split(';')
                    .map { sets ->
                        Regex("\\d+ \\w+")
                                .findAll(sets)
                                .associate { set ->
                                    val cube = set.value.split(' ')
                                    cube[1] to cube[0].toInt()
                                }
                    }
            Game(id, sets)
        }
    }
}

fun main() {
    Day02.solve("8", "2286")
}