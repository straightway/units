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
@file:Suppress("UNCHECKED_CAST")

package straightway.units

import straightway.error.Panic
import straightway.numbers.compareTo
import straightway.numbers.div
import straightway.numbers.minus
import straightway.numbers.plus
import straightway.numbers.times
import straightway.numbers.unaryMinus
import java.io.Serializable

/**
 * Convenience type for unit values with Number.
 */
typealias UnitNumber<TQuantity> = UnitValue<*, TQuantity>

/**
 * Combine a numerical value with a unit.
 * Basic arithmetic is available for unit values, either with other unit values
 * or with scalars.
 */
data class UnitValue<TValue : Number, TQuantity : Quantity>(
        val value: TValue,
        val unit: TQuantity) : Comparable<UnitValue<*, TQuantity>>, Serializable {
    val baseValue get() = value * unit.siScale.magnitude * unit.baseMagnitude + unit.valueShift

    operator fun get(newUnit: TQuantity) = UnitValue(
            (baseValue - newUnit.valueShift) * newUnit.siScale.reciprocal.magnitude /
                    newUnit.baseMagnitude, newUnit)

    operator fun unaryMinus() = UnitValue(-value, unit)
    operator fun unaryPlus() = this

    override fun toString() =
            "$value $unit".trimEnd()

    override fun equals(other: Any?) =
            other is UnitValue<*, *> &&
                    other.unit.id == unit.id &&
                    other.baseValue.compareTo(baseValue) == 0

    override fun hashCode() =
            value.hashCode() xor unit.hashCode()

    override fun compareTo(other: UnitValue<*, TQuantity>) =
            baseValue.compareTo(other.baseValue)
}

/**
 * Create a UnitValue by combining a number with a given unit in square brackets.
 */
operator fun <TNum : Number, TQuantity : Quantity> TNum.get(unit: TQuantity) =
        UnitValue(this, unit)

/**
 * Convert the given unit value to a value with another compatible unit.
 * This can be useful to assign a simplified unit when using arithmetic operators
 * with unit values.
 */
operator fun <TQuantity1 : Quantity, TQuantity2 : Quantity>
        UnitNumber<TQuantity1>.get(unit: TQuantity2) =
        if (this.unit.id != unit.id) throw Panic("Incompatible units: $unit for $this ")
        else (baseValue - unit.valueShift)[unit.withScale(unit.siScaleCorrection)][unit]
                as UnitNumber<TQuantity2>
