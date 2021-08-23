object Day006 {

    private val input = PuzzleInput("day006.txt").readText().trim()

    @JvmStatic
    fun main(args: Array<String>) {
        println(partOne(input))
        println(partTwoWrong(input))
        println(partTwoCorrect(input))
    }

    internal fun partOne(input: String): Int =
        input.getGroups().sumOf { it.replace(newLine, "").toSet().size }

    internal fun partTwoWrong(input: String): Int =
        input.getGroups().sumOf { getSecondAnswerWrong(it.split(newLine)) }

    internal fun String.getGroups(): List<String> = split("$newLine$newLine")

    internal fun partTwoCorrect(input: String): Int =
        input.getGroups().map { it.split(newLine).map { s -> s.toSet() } }
            .sumOf { it.fold(it.first()) { a, b -> a intersect b }.count() }

    /**
     * This is really convoluted and does not work. See readme
     */

    internal fun getSecondAnswerWrong(list: List<String>): Int = when {
        list.size == 1 -> list.single().length
        list.toSet().size == 1 -> 1
        else -> list.flatMap { it.toList() }
            .let { charList ->
                charList
                    .associateWith { c -> charList.count { it == c } }
                    .filterValues { it == list.size }
            }.size
    }
}
