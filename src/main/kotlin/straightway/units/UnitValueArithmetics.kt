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

@Suppress("UNCHECKED_CAST")
operator fun <TNum1 : Number, TNum2 : Number, TQuantity : Quantity>
        UnitValue<TNum1, TQuantity>.plus(other: UnitValue<TNum2, TQuantity>) =
        (baseValue + other.baseValue)[other.unit.baseQuantity] as UnitValue<Number, TQuantity>

@Suppress("UNCHECKED_CAST")
operator fun <TNum1 : Number, TNum2 : Number, TQuantity : Quantity>
        UnitValue<TNum1, TQuantity>.minus(other: UnitValue<TNum2, TQuantity>) =
        (baseValue - other.baseValue)[other.unit.baseQuantity] as UnitValue<Number, TQuantity>

operator fun <TNum1 : Number, TNum2 : Number, TQuantity1 : Quantity, TQuantity2 : Quantity>
        UnitValue<TNum1, TQuantity1>.times(other: UnitValue<TNum2, TQuantity2>) =
        (baseValue * other.baseValue)[unit.baseQuantity * other.unit.baseQuantity]

@Suppress("UNCHECKED_CAST")
operator fun <TNum : Number, TQuantity : Quantity>
        TNum.times(other: UnitValue<TNum, TQuantity>) =
        UnitValue((this * other.value) as TNum, other.unit)

operator fun <TNum : Number, TQuantity : Quantity>
        UnitValue<TNum, TQuantity>.times(other: TNum) = other * this

operator fun <TNum1 : Number, TNum2 : Number, TQuantity1 : Quantity, TQuantity2 : Quantity>
        UnitValue<TNum1, TQuantity1>.div(other: UnitValue<TNum2, TQuantity2>) =
        (baseValue / other.baseValue)[unit.baseQuantity / other.unit.baseQuantity]

@Suppress("UNCHECKED_CAST")
operator fun <TNum : Number, TQuantity : Quantity>
        TNum.div(other: UnitValue<TNum, TQuantity>) =
        UnitValue((this / other.value) as TNum, Reciprocal(other.unit))

@Suppress("UNCHECKED_CAST")
operator fun <TNum : Number, TQuantity : Quantity>
        UnitValue<TNum, TQuantity>.div(other: TNum) =
        UnitValue((value / other) as TNum, unit)
