import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldStartWith

fun testException(message: String, block: () -> Unit) {
    val exception = shouldThrow<IllegalArgumentException>(block)
    exception.message shouldStartWith message
}
