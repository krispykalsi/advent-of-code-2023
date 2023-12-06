import kotlin.math.min

private data class AlmanacPart1(
        val seeds: List<Long>,
        val maps: List<LocationMap>
)

private data class AlmanacPart2(
        val seedRanges: List<LongRange>,
        val maps: List<LocationMap>
)

private data class LocationMap(
        val mappings: List<MappingFragment>
) {
    val sortedMappings: List<MappingFragment> = mappings.sortedBy {
        it.source
    }.toList()

    fun findDestination(source: Long): Long {
        return sortedMappings.firstOrNull {
            source in it.sourceRange
        }?.let { fragment ->
            source + fragment.difference
        } ?: source
    }

    fun findDestinationFaster(source: Long): Long {
        return findClosestSourceRange(source)?.let { fragment ->
            source + fragment.difference
        } ?: source
    }

    private fun findClosestSourceRange(source: Long): MappingFragment? {
        var low = 0
        var high = sortedMappings.size - 1

        while (low <= high) {
            val middle = (low + high) / 2
            when {
                source < sortedMappings[middle].sourceRange.first -> high = middle - 1
                source > sortedMappings[middle].sourceRange.last -> low = middle + 1
                else -> return sortedMappings[middle]
            }
        }

        return null
    }
}

private data class MappingFragment(
        val source: Long,
        val destination: Long,
        val length: Long
) {
    val difference: Long = destination - source
    val sourceRange: LongRange = source..<source + length
}

object Day05 : AdventOfCodeSolver(day = 5) {
    override fun part1(input: List<String>): String {
        val almanac = parsePart1(input)
        var minValue = Long.MAX_VALUE
        for (seed in almanac.seeds) {
            var mapping = seed
            for (map in almanac.maps) {
                mapping = map.findDestination(mapping)
            }
            minValue = min(mapping, minValue)
        }
        return minValue.toString()
    }

    override fun part2(input: List<String>): String {
        val almanac = parsePart2(input)
        var minValue = Long.MAX_VALUE
        for (seedRange in almanac.seedRanges) {
            for (seed in seedRange) {
                var mapping = seed
                for (map in almanac.maps) {
                    mapping = map.findDestinationFaster(mapping)
                }
                minValue = min(mapping, minValue)
            }
        }
        return minValue.toString()
    }

    private fun parsePart1(input: List<String>): AlmanacPart1 {
        val seeds = input[0].split(":")[1].trim().split(" ").map { it.toLong() }
        val maps = parseMaps(input)
        return AlmanacPart1(seeds, maps)
    }

    private fun parsePart2(input: List<String>): AlmanacPart2 {
        val seeds = input[0].split(":")[1].trim().split(" ").map { it.toLong() }
        val seedRanges = mutableListOf<LongRange>()
        for (i in seeds.indices) {
            if (i % 2 != 0) continue
            seedRanges.add(seeds[i]..<seeds[i] + seeds[i + 1])
        }
        val maps = parseMaps(input)
        return AlmanacPart2(seedRanges, maps)
    }

    private fun parseMaps(input: List<String>): List<LocationMap> {
        val maps = mutableListOf<LocationMap>()
        var fragments = mutableListOf<MappingFragment>()
        for (line in input.slice(3..<input.size)) {
            if (line.isBlank() || !line[0].isDigit()) {
                if (fragments.isNotEmpty()) {
                    maps.add(LocationMap(fragments))
                    fragments = mutableListOf()
                }
                continue
            }
            val values = line.split(" ").map { it.toLong() }
            fragments.add(MappingFragment(values[1], values[0], values[2]))
        }
        maps.add(LocationMap(fragments))
        return maps
    }
}

fun main() {
    Day05.solve("35", "46")
}