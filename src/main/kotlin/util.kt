import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

inline fun <T> measureTimedValueMinOf(times: Int = 5, block: () -> T): TimedValue<T> {
    return (1..times).map {
        measureTimedValue { block() }
    }.minByOrNull { it.duration } ?: error("no min")
}
