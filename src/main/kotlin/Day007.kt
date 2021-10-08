import kotlin.time.measureTimedValue

object Day007 {
    internal val testInput = """
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

    internal val testInputPart2 = """
        shiny gold bags contain 2 dark red bags.
        dark red bags contain 2 dark orange bags.
        dark orange bags contain 2 dark yellow bags.
        dark yellow bags contain 2 dark green bags.
        dark green bags contain 2 dark blue bags.
        dark blue bags contain 2 dark violet bags.
        dark violet bags contain no other bags.
    """.trimIndent().trim().split(newLine)
    private val input = PuzzleInput("day007.txt").readText().trim().split(newLine)
    private const val SG = "shiny gold"
    private val regex = Regex("""(.bags)|(.bag)|(\.)""")
    private val digitsReg = """\d+""".toRegex()

    private const val bagsReg = """bags?\.?"""

    internal fun List<String>.toBags(): Bags = associate {
        val (first, second) = it.replace(regex, "").trim()
            .split(" contain ")

        val containsBags = second.split(", ")
        if (containsBags.first().contains("no other")) first to emptyMap()
        else first.trim() to second.split(", ")
            .associate { v ->
                digitsReg.replace(v, "").trim() to digitsReg.findAll(v).first().value.toInt()
            }
    }

    // very inefficient
    private fun Bags.possibleColors(
        bagName: String,
        result: Set<String> = emptySet(),
    ): Set<String> {
        return filterKeys { it !in result }.filterValues { it.containsKey(bagName) }.keys.run {
            val acc = this + result
            acc + flatMap {
                possibleColors(it, acc)
            }
        }
    }

    internal fun Bags.total(bagName: String): Int {
        val bag = getOrDefault(bagName, emptyMap())

        if (bag.isEmpty()) return 0

        var total = 0
        for (b in bag) {
            total += b.value + b.value * total(b.key)
        }

        return total
    }

    private fun buildBagTreeOne(): Map<Color, Set<String>> {
        val rules = hashMapOf<Color, Rule>()
        input.forEach { line ->
            val (parent, allChildren) = line.replace(Regex("""\d+"""), "")
                .replace(Regex("""bags?\.?"""), "")
                .split("contain")
                .map { it.trim() }

            val childrenColors = allChildren.split(',').map { it.trim() }.toSet()

            for (childColor in childrenColors) {
                rules.compute(childColor) { _, current ->
                    if (current == null) setOf(parent)
                    else current + parent
                }
            }
        }

        return rules
    }

    private fun findContainersDFSOne(rules: Map<Color, Rule>): Set<Color> {
        var known = setOf(SG)
        var next = setOf(SG) + rules.getValue(SG)
        while (true) {
            val toFind = next - known
            if (toFind.isEmpty()) break
            known = known + next
            next = toFind.mapNotNull { rules[it] }.flatten().toSet()
        }
        return known - SG
    }

    private fun buildBagTreeTwo(): Map<Color, Rule> {
        val rules = hashMapOf<Color, Rule>()
        input.forEach { line ->
            val (parent, allChildren) = line.replace(Regex(bagsReg), "")
                .split("contain")
                .map { it.trim() }

            val rule = if (allChildren.contains("no other")) emptySet()
            else allChildren.split(',').map { it.trim() }.toSet()

            rules[parent] = rule
        }
        return rules
    }

    private fun Map<Color, Rule>.getChildrenCountBFS(color: Color): Int {
        val children = getOrDefault(color, setOf())

        if (children.isEmpty()) return 0

        var total = 0
        for (child in children) {
            val count = digitsReg.findAll(child).first().value.toInt()
            val bag = digitsReg.replace(child, "").trim()
            total += count + count * getChildrenCountBFS(bag)
        }
        return total
    }

    fun partOne(): Int = input.toBags().possibleColors(SG).size

    fun partTwo(): Int = input.toBags().total(SG)

    fun partOneTree(): Int {
        val rules: Map<Color, Rule> = buildBagTreeOne()
        val containers = findContainersDFSOne(rules)
        return containers.size
    }

    fun partTwoTree(): Int {
        val rules = buildBagTreeTwo()
        return rules.getChildrenCountBFS(SG)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        measureTimedValue {
            partOne()
        }.also {
            println("part one result: ${it.value}")
            println("took ${it.duration.inWholeMicroseconds}")
        }

        measureTimedValue {
            partOneTree()
        }.also {
            println("part one tree result: ${it.value}")
            println("took ${it.duration.inWholeMicroseconds}")
        }

        measureTimedValue {
            partTwo()
        }.also {
            println("part two result: ${it.value}")
            println("took ${it.duration.inWholeMicroseconds}")
        }

        measureTimedValue {
            partTwoTree()
        }.also {
            println("part two tree result: ${it.value}")
            println("took ${it.duration.inWholeMicroseconds}")
        }
    }
}

typealias Bags = Map<String, Container>

typealias Container = Map<String, Int>

typealias Color = String
typealias Rule = Set<String>
