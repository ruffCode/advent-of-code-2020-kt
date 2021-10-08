object Day008 {
    internal val testInput = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent().split(newLine)

    val input = PuzzleInput("day008.txt").readText().split(newLine)

    fun List<String>.toInstructions(): List<Instruction> {
        return map {
            val (first, second) = it.split(" ")

            when (first) {
                "acc" -> Instruction.Acc(second.toInt())
                "jmp" -> Instruction.Jmp(second.toInt())
                else -> Instruction.NOp(second.toInt())
            }
        }
    }

    internal fun partOne(): Int {
        val instructions = input.toInstructions()
        return instructions.execute()
    }

    internal fun partTwo(): Int = InstructionRunner(input.toInstructions()).run()

    private fun List<Instruction>.execute(): Int {
        val executed = mutableSetOf<Int>()
        var acc = 0
        var idx = 0

        while (idx !in executed) {
            executed.add(idx)

            when (val curr = this[idx]) {
                is Instruction.Acc -> {
                    acc += curr.argument
                    idx++
                }
                is Instruction.NOp -> idx++
                is Instruction.Jmp -> idx += curr.argument
            }
        }
        return acc
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(partOne())
        println(partTwo())
    }
}

sealed class Instruction {
    abstract val argument: Int

    data class Acc(override val argument: Int) : Instruction()
    data class NOp(override val argument: Int) : Instruction()
    data class Jmp(override val argument: Int) : Instruction()
}

class InstructionRunner(private val instructions: List<Instruction>) {

    private var currentInstructions = instructions.toMutableList()

    private var currentRun = 0
    private val ops = instructions.mapIndexedNotNull { index, instruction ->
        if (instruction is Instruction.Jmp || instruction is Instruction.NOp) index
        else null
    }.toMutableList()

    private fun MutableList<Instruction>.switch(pos: Int): MutableList<Instruction> {
        val next = when (val curr = this[pos]) {
            is Instruction.Jmp -> Instruction.NOp(0)
            is Instruction.NOp -> Instruction.Jmp(curr.argument)
            else -> throw IllegalArgumentException("Instruction should not be Acc")
        }

        this[pos] = next
        return this
    }

    private fun next() {
        val op = ops[currentRun]
        currentInstructions = instructions.toMutableList().switch(op)
        currentRun++
    }

    fun run(): Int {
        var executionCount = 0
        var acc: Int

        while (true) {
            executionCount++
            acc = currentInstructions.execute()
            if (acc != 0) break
            if (executionCount > currentInstructions.size * 2) break
            next()
        }

        return acc
    }

    private fun List<Instruction>.execute(): Int {
        var acc = 0
        var idx = 0
        var numRun = 0

        while (true) {
            numRun++
            if (numRun >= this.size) {
                acc = 0
                break
            }
            if (idx == this.lastIndex) break
            when (val curr = this[idx]) {
                is Instruction.Acc -> {
                    acc += curr.argument
                    idx++
                }
                is Instruction.NOp -> idx++
                is Instruction.Jmp -> idx += curr.argument
            }
        }
        return acc
    }
}
