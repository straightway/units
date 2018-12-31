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

package straightway.units

import straightway.numbers.div
import straightway.numbers.minus
import straightway.numbers.plus
import straightway.numbers.times
import straightway.numbers.unaryMinus

operator fun <TQuantity : Quantity> UnitValue<TQuantity>.unaryPlus() =
        this
operator fun <TQuantity : Quantity> UnitValue<TQuantity>.unaryMinus(): UnitValue<TQuantity> =
        (-value)[unit]

@Suppress("UNCHECKED_CAST")
operator fun <TQuantity : Quantity> UnitValue<TQuantity>.plus(other: UnitValue<TQuantity>) =
        (baseValue + other.baseValue)[other.unit.baseQuantity as TQuantity]

@Suppress("UNCHECKED_CAST")
operator fun <TQuantity : Quantity>
        UnitValue<TQuantity>.minus(other: UnitValue<TQuantity>) =
        (baseValue - other.baseValue)[other.unit.baseQuantity as TQuantity]

@Suppress("UNCHECKED_CAST")
operator fun <TQuantity1 : Quantity, TQuantity2 : Quantity>
        UnitValue<TQuantity1>.times(other: UnitValue<TQuantity2>) =
        (baseValue * other.baseValue)[unit.baseQuantity as TQuantity1 *
                                      other.unit.baseQuantity as TQuantity2]

operator fun <TNum : Number, TQuantity : Quantity> TNum.times(other: UnitValue<TQuantity>) =
        UnitValueImpl(this * other.value, other.unit)

operator fun <TQuantity : Quantity> UnitValue<TQuantity>.times(x: Number) =
        UnitValueImpl(value * x, unit)

@Suppress("UNCHECKED_CAST")
operator fun <TQuantity1 : Quantity, TQuantity2 : Quantity>
        UnitValue<TQuantity1>.div(other: UnitValue<TQuantity2>) =
        (baseValue / other.baseValue)[unit.baseQuantity as TQuantity1 /
                                      other.unit.baseQuantity as TQuantity2]

operator fun <TNum : Number, TQuantity : Quantity> TNum.div(other: UnitValue<TQuantity>) =
        UnitValueImpl(this / other.value, Reciprocal(other.unit))

operator fun <TQuantity : Quantity> UnitValue<TQuantity>.div(x: Number) =
        UnitValueImpl(value / x, unit)
