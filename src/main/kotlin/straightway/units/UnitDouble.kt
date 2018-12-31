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

/**
 * Combine a double value with a unit.
 */
data class UnitDouble<TQuantity : Quantity>(
        override val value: Double,
        override val unit: TQuantity
) : UnitValue<TQuantity> {

    override val baseValue: Double by lazy {
        value * unit.siScale.magnitude.toDouble() * unit.baseMagnitude.toDouble() +
                unit.valueShift.toDouble()
    }

    override operator fun get(newUnit: TQuantity) = UnitDouble(
            (baseValue - newUnit.valueShift.toDouble()) *
                    newUnit.siScale.reciprocal.magnitude.toDouble() /
                    newUnit.baseMagnitude.toDouble(), newUnit)

    override operator fun rangeTo(endInclusive: UnitValue<TQuantity>) =
            UnitValueRange(this, endInclusive)

    operator fun unaryMinus() = UnitDouble(-value, unit)

    operator fun unaryPlus() = this

    @Suppress("UNCHECKED_CAST")
    operator fun plus(other: UnitDouble<TQuantity>) =
            (baseValue + other.baseValue)[other.unit.baseQuantity as TQuantity]

    @Suppress("UNCHECKED_CAST")
    operator fun minus(other: UnitDouble<TQuantity>) =
            (baseValue - other.baseValue)[other.unit.baseQuantity as TQuantity]

    @Suppress("UNCHECKED_CAST")
    operator fun <TOtherQuantity : Quantity> times(other: UnitDouble<TOtherQuantity>) =
            (baseValue * other.baseValue)[unit.baseQuantity as TQuantity *
                                          other.unit.baseQuantity as TOtherQuantity]

    operator fun times(x: Double) = UnitNumber(value * x, unit)

    @Suppress("UNCHECKED_CAST")
    operator fun <TOtherQuantity : Quantity> div(other: UnitDouble<TOtherQuantity>) =
            (baseValue / other.baseValue)[unit.baseQuantity as TQuantity /
                                          other.unit.baseQuantity as TOtherQuantity]

    operator fun div(x: Double) = UnitNumber(value / x, unit)

    override fun toString() =
            "$value $unit".trimEnd()

    override fun equals(other: Any?) =
            other is UnitValue<*> &&
                    other.unit.id == unit.id &&
                    other.baseValue.toDouble().compareTo(baseValue) == 0

    override fun hashCode() =
            value.hashCode() xor unit.hashCode()

    override fun compareTo(other: UnitValue<TQuantity>) =
            baseValue.compareTo(other.baseValue.toDouble())

    companion object {
        const val serialVersionUID = 1L
    }
}

/**
 * Create a UnitDouble by combining a double with a given unit in square brackets.
 */
operator fun <TQuantity : Quantity> Double.get(unit: TQuantity): UnitDouble<TQuantity> =
        UnitDouble(this, unit)

/**
 * Convert the given unit double to a value with another compatible unit.
 * This can be useful to assign a simplified unit when using arithmetic operators
 * with unit doubles.
 */
operator fun <TQuantity1 : Quantity, TQuantity2 : Quantity>
        UnitDouble<TQuantity1>.get(unit: TQuantity2): UnitDouble<TQuantity2> =
        if (this.unit.id != unit.id) throw Panic("Incompatible units: $unit for $this ")
        else (baseValue - unit.valueShift.toDouble())[unit.withScale(unit.siScaleCorrection)][unit]
                as UnitDouble<TQuantity2>

operator fun <TQuantity : Quantity> Double.times(other: UnitDouble<TQuantity>) =
        other * this

operator fun <TQuantity : Quantity> Double.div(other: UnitDouble<TQuantity>) =
        UnitDouble(this / other.value, Reciprocal(other.unit))