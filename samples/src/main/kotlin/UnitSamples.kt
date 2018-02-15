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

import straightway.error.Panic
import straightway.units.Acceleration
import straightway.units.Length
import straightway.units.Speed
import straightway.units.Time
import straightway.units.UnitNumber
import straightway.units.UnitValue
import straightway.units.centi
import straightway.units.div
import straightway.units.get
import straightway.units.gram
import straightway.units.kelvin
import straightway.units.kilo
import straightway.units.meter
import straightway.units.milli
import straightway.units.minute
import straightway.units.pound
import straightway.units.second
import straightway.units.square
import straightway.units.times

@Suppress("LongMethod", "TooGenericExceptionCaught", "MagicNumber")
fun main(args: Array<String>) {

    // To have a number with an attached unit, use the index operator, e.g.:
    val temperature = 273[kelvin]
    println(temperature) // Out: 273 K

    // A scale can be attached to the unit using the scale's function call operator:
    val mass = 10[kilo(gram)]
    println(mass) // Out: 10 kg

    // You can use derived units just straight forward:
    val speed = 12[meter / second]
    println(speed) // Out: 12 m/s

    // Equally, you can do calculations with the unit values:
    val area = 10[meter] * 3[meter]
    println(area) // Out: 30 m²

    // Or you can do calculations with the unit values and scalar numbers:
    val halfArea = area / 2
    println(halfArea) // Out: 15 m²

    // Sometimes, an explicit normalization of the unit is required. See the definition of
    // the accelerationFromSpeedAndTime function for details.
    println(
            "acceleration for $speed and ${14[second]} is " +
                    "${accelerationFromSpeedAndTime(speed, 14[second])}")
    // Out: acceleration for 12 m/s and 14 s is 0.8571428571428571 m/s²

    // Unit conversion can also be used to convert between metric and non-metric units, or
    // to adapt the scale.
    println("${1[kilo(gram)]} is equal to ${1[kilo(gram)][pound]}")
    // Out: 1 kg is equal to 2.2046226218487757 lbs

    println("${1[meter]} is equal to ${1[meter][milli(meter)]}") // Out: 1 m is equal to 1000 mm

    // An explicit conversion to incompatible units raises an exception at runtime.
    try {
        3[second][kelvin]
        throw Panic("We should never reach this point as the conversion above already panics")
    } catch (ex: Exception) {
        println("3[second][kelvin] throws $ex")
        // Out: 3[second][kelvin] throws Panic: Incompatible units: K for 3 s
    }

    // It is possible to access the value and the base value of a UnitValue:
    println("The value of ${2[kilo(meter)]} is ${2[kilo(meter)].value}")
    // Out: The value of 2 km is 2

    println("The base value of ${2[kilo(meter)]} is ${2[kilo(meter)].baseValue}")
    // Out: The base value of 2 km is 2000

    // The special cases of the unit system are handled properly, especially for mass, for
    // which kg is the SI base unit and not g, and for time, for which the base unit is second!
    println("The base value of ${2[gram]} is ${2[gram].baseValue}")
    // Out: The base value of 2 g is 0.002

    println("The base value of ${2[minute]} is ${2[minute].baseValue}")
    // Out: The base value of 2 min is 120

    // It is possible to constrain the number type for a unit value. If you e.g. specify
    // Int as number type, all calculations are done with integer. Example:
    val intLength: UnitValue<Int, Length> = 15[meter]
    println("Half of $intLength is ${intLength / 2}") // Out: Half of 15 m is 7 m
    val scaledIntLength = intLength[centi(meter)]
    println("Half of $scaledIntLength is ${scaledIntLength / 2}") // Out: Half of 1500 cm is 750 cm

    // Scaling may change the type of the value:
    println("${intLength[kilo(meter)]}") // Out: 0.015 km
}

private fun accelerationFromSpeedAndTime(speed: UnitNumber<Speed>, time: UnitNumber<Time>):
        UnitNumber<Acceleration> {
    // Due to restrictions of the kotlin language, the product of units is unfortunately not
    // commutative, as it is in reality. But it is possible to convert 'compatible' units,
    // e.g. this statement line would throw an exception at runtime if the conversion is
    // not possible. In the case shown here, the unit is properly converted. The conversion
    // is necessary, because the predefined unit type 'Acceleration' is
    //   Product<Length, Reciprocal<Square<Time>>>
    // while the unit of 'speed / meter' is
    //   Product<Product<Length, Reciprocal<Time>>, Reciprocal<Time>>
    // Since these two types are formally different, a conversion is required to match the
    // function's return type.
    return (speed / time)[meter / square(second)]
}