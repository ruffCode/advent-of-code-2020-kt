import kotlin.time.measureTimedValue

// all credit to @SebastianAigner
object Day008Idiomatic {
    val input = PuzzleInput("day008.txt").readText().split(newLine)

    @JvmStatic
    fun main(args: Array<String>) {
        val inst = input.toInstr()
        println(
            measureTimedValue {
                execute(inst)
            }.duration.inWholeMicroseconds
        )

        println(
            measureTimedValue {
                executeMutably(inst)
            }.duration.inWholeMicroseconds
        )

        println(
            measureTimedValue {
                Day008.partOne()
            }.duration.inWholeMicroseconds
        )

        println("part two")

        println(
            measureTimedValue {
                generateAllMutations(inst)
                    .map { mod -> execute(mod) }
                    .first { state -> state.ip !in inst.indices }
            }.also {
                it.duration.inWholeMilliseconds
            }
        )

        println(
            measureTimedValue {
                generateAllMutations(inst)
                    .map { mod -> executeMutably(mod) }
                    .first { state -> state.ip !in inst.indices }
            }.also {
                it.duration.inWholeMilliseconds
            }
        )

        println(
            measureTimedValue {
                Day008.partTwo()
            }.also {
                it.duration.inWholeMilliseconds
            }
        )
    }

    private fun execute(instructions: List<Inst>): MachineState {
        var state = MachineState(0, 0)
        val executedIndices = mutableSetOf<Int>()

        while (state.ip in instructions.indices) {
            val nextInstruction = instructions[state.ip]
            state = nextInstruction.action(state)
            if (state.ip in executedIndices) return state
            executedIndices += state.ip
        }
        return state
    }

    private fun List<String>.toInstr(): List<Inst> = map(::Inst)

    private fun Inst(s: String): Inst {
        val (first, second) = s.split(" ")
        val arg = second.toInt()
        return when (first) {
            "acc" -> Acc(arg)
            "jmp" -> Jmp(arg)
            "nop" -> Nop(arg)
            else -> error("Invalid input")
        }
    }

    private fun generateAllMutations(instructions: List<Inst>): Sequence<List<Inst>> = sequence {
        for ((index, instruction) in instructions.withIndex()) {
            val newProgram = instructions.toMutableList()

            newProgram[index] = when (instruction) {
                is Acc -> continue
                is Jmp -> Nop(instruction.value)
                is Nop -> Jmp(instruction.value)
            }
            yield(newProgram)
        }
    }

    private fun executeMutably(instructions: List<Inst>): MachineState {
        var idx = 0
        var acc = 0
        val executedIndices = mutableSetOf<Int>()
        while (idx in instructions.indices) {
            when (val nextInstr = instructions[idx]) {
                is Acc -> {
                    acc += nextInstr.value
                    idx++
                }
                is Jmp -> idx += nextInstr.value
                is Nop -> idx++
            }
            if (idx in executedIndices) break
            executedIndices += idx
        }
        return MachineState(idx, acc)
    }
}

private data class MachineState(val ip: Int, val acc: Int)

private sealed class Inst(val action: (MachineState) -> MachineState)

private class Nop(val value: Int) : Inst({ MachineState(it.ip + 1, it.acc) })

private class Jmp(val value: Int) : Inst({ MachineState(it.ip + value, it.acc) })

private class Acc(val value: Int) : Inst({ MachineState(it.ip + 1, it.acc + value) })
