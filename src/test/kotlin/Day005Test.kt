import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Day005Test : ShouldSpec({
    val sample =
        listOf("FBFBBFFRLR" to 357, "BFFFBBFRRR" to 567, "FFFBBBFRRR" to 119, "BBFFBBFRLL" to 820)

    val seatIds = sample.map { it.first }

    context("Part One") {
        sample.forEach {
            should("return correct rowId for ${it.first} ") {
                Day005.getSeatId(it.first) shouldBe it.second
            }
        }
        should("return max rowId") {
            Day005.partOne(seatIds) shouldBe 820
        }
    }

    context("Part two") {
        should("return the only missing seat") {

            Day005.partTwo(
                listOf(
                    "FFFBBFBLRR",
                    "FFFBBFBRLL",
                    "FFFBBFBRLR",
                    "FFFBBFBRRR"
                )
            ) shouldBe 110
        }
        should("fail because more than one value returned") {
            val exception = shouldThrow<IllegalArgumentException> {
                Day005.partTwo(seatIds)
            }
            exception.message shouldBe "Collection contains more than one matching element."
        }
    }
})
