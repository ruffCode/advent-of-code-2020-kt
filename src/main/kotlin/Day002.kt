object Day002 {
    val sample = "1-3 b: cdefg"

    private val passwordList = PuzzleInput("day002.txt").readLines()

    @JvmStatic
    fun main(args: Array<String>) {
        println(partOne(passwordList))
        println(partOneBetter(passwordList))
        println(partTwo(passwordList))
        println(partTwoBetter(passwordList))
    }

    private fun lineToPassword(line: String): Password {
        return line.split(" ").let {
            val (min, max) = it.first().split("-").map(String::toInt)
            Password(
                range = min..max,
                letter = it[1].replace(":", "").first(),
                password = it.last()
            )
        }
    }

    internal fun partOne(input: List<String>): Int =
        input.map(::lineToPassword).count { it.isValid }

    internal fun partOneBetter(input: List<String>): Int =
        input.mapNotNull { Password(it) }.count { it.isValid }

    internal fun partTwo(input: List<String>): Int =
        input.map(::lineToPassword).count { it.isValidPart2 }

    internal fun partTwoBetter(input: List<String>): Int =
        input.mapNotNull { Password(it) }.count { it.isValidPart2Cleaner }

    private data class Password(
        val range: IntRange,
        val letter: Char,
        val password: String,
    ) {
        val isValid: Boolean by lazy {
            password.count { it == letter } in range
        }

        val isValidPart2: Boolean by lazy {
            val p1 = range.first - 1
            val p2 = range.last - 1
            (
                (password[p1] == letter && password[p2] != letter) ||
                    (password[p1] != letter) && password[p2] == letter
                )
        }
        val isValidPart2Cleaner: Boolean by lazy {
            (password[range.first - 1] == letter) xor (password[range.last - 1] == letter)
        }

        companion object {
            operator fun invoke(line: String): Password? {
                val regex = Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")
                return regex.matchEntire(line)
                    ?.destructured
                    ?.let { (start, end, letter, password) ->
                        Password(start.toInt()..end.toInt(), letter.single(), password)
                    }
            }
        }
    }
}

data class PasswordWithPolicy(
    val password: String,
    val range: IntRange,
    val letter: Char,
) {
    fun validatePartOne() =
        password.count { it == letter } in range

    fun validatePartTwo() =
        (password[range.first - 1] == letter) xor (password[range.last - 1] == letter)

    companion object {
        fun parse(line: String) = PasswordWithPolicy(
            password = line.substringAfter(": "),
            letter = line.substringAfter(" ").substringBefore(":").single(),
            range = line.substringBefore(" ").let {
                val (start, end) = it.split("-")
                start.toInt()..end.toInt()
            },
        )

        private val regex = Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")
        fun parseUsingRegex(line: String): PasswordWithPolicy =
            regex.matchEntire(line)!!
                .destructured
                .let { (start, end, letter, password) ->
                    PasswordWithPolicy(password, start.toInt()..end.toInt(), letter.single())
                }
    }
}
