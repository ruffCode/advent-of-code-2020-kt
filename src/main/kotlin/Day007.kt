object Day007 {
    private val testInput = """
        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.
    """.trimIndent().trim().split(newLine)

    private val input = PuzzleInput("day007.txt").readText().trim().split(newLine)

    private val regex = Regex("""(.bags)|(.bag)|(.[0-9].)|(\.)""")


    private fun List<String>.toBags(): Bags = associate {
        val (first, second) = it.replace(regex, "").trim()
            .split("contain")
        first.trim() to second.split(",").toSet()
    }

    private fun Bags.canContain(bagName: String, result: Set<String> = emptySet()): Set<String> =
        filterKeys { it !in result }.filterValues { it.contains(bagName) }.keys.run {
            val acc = this + result
            acc + flatMap {
                canContain(it, acc)
            }
        }


    fun partOne(): Int = input.toBags().canContain("shiny gold").size

    @JvmStatic
    fun main(args: Array<String>) {
        println(partOne())
    }


}
private typealias Bags = Map<String, Set<String>>
