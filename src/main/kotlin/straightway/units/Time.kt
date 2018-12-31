/*
 * Copyright 2016 github.com/straightway
 *
 *  Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
@file:Suppress("MagicNumber")

package straightway.units

import straightway.numbers.minus
import straightway.numbers.times
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Magnitude for time.
 */
class Time constructor(
        symbol: String,
        scale: UnitScale,
        baseMagnitude: Number
) : QuantityBase(symbol, scale, baseMagnitude, { Time(symbol, it, baseMagnitude) }) {
    constructor(symbol: String, numberOfSeconds: Number) : this(symbol, uni, numberOfSeconds)
    override val baseQuantity: Quantity get() = second
    val numberOfSeconds get() = scale.magnitude * baseMagnitude
}

val second = Time("s", 1)
val minute = Time("min", 60)
val hour = Time("h", 60 * minute.numberOfSeconds.toInt())
val day = Time("d", 24 * hour.numberOfSeconds.toInt())
val week = Time("wk", 7 * day.numberOfSeconds.toInt())
val year = Time("a", 31558432.5504)

fun UnitValue<Time>.toDuration(): Duration {
    val seconds = this.baseValue.toLong()
    val nanos = ((this.baseValue - seconds) * 1_000_000_000).toLong()
    return Duration.ofSeconds(seconds, nanos)
}

operator fun LocalDateTime.plus(amount: UnitValue<Time>) =
        (this + amount.toDuration())!!

operator fun LocalDateTime.minus(amount: UnitValue<Time>) =
        (this - amount.toDuration())!!

operator fun LocalDateTime.minus(other: LocalDateTime): UnitValue<Time> =
        Duration.between(other, this).toTime()

fun Duration.toTime() = this.nano[nano(second)] + this.seconds[second]

private val zeroTime = LocalDateTime.of(0, 1, 1, 0, 0)
val UnitValue<Time>.absolute get() = zeroTime + this
val LocalDateTime.unitValue: UnitValue<Time> get() = this - zeroTime

fun LocalDate.at(time: UnitValue<Time>) = LocalDateTime.of(this, LocalTime.MIDNIGHT) + time