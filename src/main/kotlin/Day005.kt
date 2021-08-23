object Day005 {
    private val sample = listOf("FBFBBFFRLR")

    private val input = PuzzleInput("day005.txt")

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

    // Solution from https://github.com/kotlin-hands-on/advent-of-code-2020/blob/master/src/day05/day5.kt
    // by Svetlana Isakova

    fun alternateSolution() {
        val seatIDs = input.readLines()
            .map(String::toSeatID)
        val maxID = seatIDs.maxOrNull()!!
        println("Max seat ID: $maxID")

        val occupiedSeatsSet = seatIDs.toSet()
        fun isOccupied(seat: Int) = seat in occupiedSeatsSet
        val mySeat = (1..maxID).find { index ->
            !isOccupied(index) && isOccupied(index - 1) && isOccupied(index + 1)
        }
        println("My seat ID: $mySeat")
    }
}

internal fun String.toSeatID(): Int =
    replace("B", "1").replace("F", "0")
        .replace("R", "1").replace("L", "0")
        .toInt(radix = 2)
