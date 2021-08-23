object Day05 {
    private val sample = listOf("FBFBBFFRLR")

    private val input = PuzzleInput("day05.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val passes = input.readLines()

        println(
            """
            part1: ${partOne(passes)}
            part2: ${partTwo(passes)}
            """.trimIndent()
        )
    }

    fun partOne(passes: List<String>): Int =
        passes.toSeatIds().maxOf { it }

    fun partTwo(passes: List<String>): Int {
        val seatIds = passes.toSeatIds()
        val max = seatIds.maxOf { it }
        val min = seatIds.minOf { it }
        return (min..max).toList().single { it !in seatIds }
    }

    internal fun getSeatId(boardingPass: String): Int {
        var rows = (0..127).toList()
        var columns = (0..7).toList()

        boardingPass.forEach {
            val rowsToDrop = rows.size / 2
            val columnsToDrop = columns.size / 2
            when (it) {
                'F' -> rows = rows.dropLast(rowsToDrop)
                'B' -> rows = rows.drop(rowsToDrop)
                'R' -> columns = columns.drop(columnsToDrop)
                'L' -> columns = columns.dropLast(columnsToDrop)
            }
        }

        return rows.single() * 8 + columns.single()
    }

    private fun List<String>.toSeatIds(): List<Int> = this.map(::getSeatId)
}
