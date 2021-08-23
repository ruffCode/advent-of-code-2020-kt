object Day04 {
    private val input = PuzzleInput("day04-sample.txt")

    internal fun List<String>.getPassports(): List<PassportMap> {
        val result = mutableListOf<String>()

        val iterator = listIterator()

        var intermediate = arrayListOf<String>()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isNotEmpty()) {
                intermediate.add(next)
                if (!iterator.hasNext()) {
                    result.add(intermediate.joinToString(" "))
                }
            } else {
                result.add(intermediate.joinToString(" "))
                intermediate = arrayListOf()
            }
        }
        return result.mapNotNull { list1 ->
            val mp = mutableMapOf<String, String>()
            mp.putAll(
                list1.split(" ").map { s ->
                    val (k, v) = s.split(":")
                    k to v
                }
            )

            if (mp.isValid()) {
                mp
            } else null
        }
    }

    internal fun String.getPassportsBetter(): List<PassportMap> {
        return trim()
            .split("\n\n", "\r\n\r\n")
            .map { passportMapFromString(it) }.filter { it.isValid() }
    }

    internal fun checkPassport(input: List<String>): Int =
        input.getPassports().mapNotNull {
            runCatching { Passport.fromPassportMap(it) }
                .getOrNull()
        }.size

    internal fun checkPassportsBetter(input: String): Int =
        input.getPassportsBetter()
            .mapNotNull { runCatching { Passport.fromPassportMap(it) }.getOrNull() }.size

    @JvmStatic
    fun main(args: Array<String>) {
        println(
            """
            part1: ${checkPassport(input.readLines())}
            part2: ${checkPassportsBetter(input.readText())}
            """.trimIndent()
        )
    }
}

internal typealias PassportMap = Map<String, String>

internal fun PassportMap.isValid(): Boolean =
    this.keys.containsAll(listOf("iyr", "byr", "eyr", "hgt", "hcl", "ecl", "pid"))

internal fun passportMapFromString(input: String): PassportMap {
    return input.split(" ", "\n", "\r\n").associate {
        val (k, v) = it.split(":")
        k to v
    }
}

internal data class Passport(
    val iyr: IssueYear,
    val byr: BirthYear,
    val hgt: Height,
    val hcl: HairColor,
    val ecl: EyeColor,
    val pid: PassportId,
    val eyr: ExpYear,
) {
    companion object {
        fun fromPassportMap(passportMap: PassportMap): Passport {
            return Passport(
                iyr = IssueYear(passportMap.getValue("iyr").toInt()),
                byr = BirthYear(passportMap.getValue("byr").toInt()),
                hgt = Height(passportMap.getValue("hgt")),
                hcl = HairColor(passportMap.getValue("hcl")),
                ecl = EyeColor(passportMap.getValue("ecl")),
                pid = PassportId(passportMap.getValue("pid")),
                eyr = ExpYear(passportMap.getValue("eyr").toInt())
            )
        }
    }
}

@JvmInline
internal value class BirthYear(val value: Int) {
    init {
        require(value in 1920..2002) {
            "Birth year $value is not between 1920 and 2002"
        }
    }
}

@JvmInline
internal value class IssueYear(val value: Int) {
    init {
        require(value in 2010..2020) {
            "Issued year $value is not between 1920 and 2002"
        }
    }
}

@JvmInline
internal value class ExpYear(val value: Int) {
    init {
        require(value in 2020..2030) {
            "Exp year $value is not between 1920 and 2002"
        }
    }
}

@JvmInline
internal value class Height(val value: String) {

    init {
        require(
            when {
                value.endsWith("in") -> value.substringBefore("in").toInt() in 59..76
                value.endsWith("cm") -> value.substringBefore("cm").toInt() in 150..193
                else -> false
            }
        ) {
            "Height $value invalid"
        }
    }
}

@JvmInline
internal value class HairColor(val value: String) {
    init {
        val regex = Regex("""#([a-f,0-9]{6})""")
        require(value.matches(regex)) {
            "Hair color $value invalid"
        }
    }
}

@JvmInline
internal value class EyeColor(val value: String) {
    init {
        val validColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        require(value in validColors) {
            "Eye color $value not in $validColors"
        }
    }
}

@JvmInline
internal value class PassportId(val value: String) {
    init {
        require(value.matches(Regex("""^\d{9}?${'$'}"""))) {
            "Passport ID $value invalid"
        }
    }
}
