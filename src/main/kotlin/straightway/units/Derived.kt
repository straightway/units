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
 * This file contains definitions for common quantities. Prefer using these definitions
 * over defining own quantities or just use the 'random' results of unit arithmetic (see
 * samples for explanation).
 */

@file:Suppress("unused", "MagicNumber")

package straightway.units

import straightway.numbers.times

val gravityAcceleration = 9.80665[meter / square(second)]

typealias AbsorbedDose = Product<Square<Length>, Reciprocal<Square<Time>>>
val gray: AbsorbedDose = square(meter) / square(second) withSymbol "Gy"

typealias Acceleration = Product<Length, Reciprocal<Square<Time>>>

typealias Angle = Product<Length, Reciprocal<Length>>
val radian: Angle = meter / meter withSymbol "rad"
val degree: Angle = Length("mPI/180", uni, Math.PI / 180.0) / meter withSymbol "°"

typealias Area = Square<Length>
val are: Area = square(Length("dam", uni, 10)) withSymbol "a"
val hectare: Area = square(Length("hm", uni, 100)) withSymbol "ha"

typealias Bandwidth = Product<AmountOfData, Reciprocal<Time>>

typealias CatalyticActivity = Product<AmountOfSubstance, Reciprocal<Time>>
val katal = mol / second withSymbol "kat"

typealias Capacitance = Product<
        Product<Product<Square<Time>, Square<Time>>, Square<ElectricCurrent>>,
        Reciprocal<Product<Mass, Square<Length>>>>
val farad: Capacitance = square(second) * square(second) * square(ampere) /
        (kilo(gram) * square(meter)) withSymbol "F"

typealias ElectricCharge = Product<ElectricCurrent, Time>
val coulomb: ElectricCharge = ampere * second withSymbol "C"

typealias ElectricResistance =
        Product<Product<Mass, Square<Length>>,
                Reciprocal<Product<Cubic<Time>, Square<ElectricCurrent>>>>
val ohm: ElectricResistance = (kilo(gram) * square(meter) / (cubic(second) * square(ampere)))
        .withSymbol("Ω")

typealias ElectricalConductance =
        Product<Product<Cubic<Time>, Square<ElectricCurrent>>,
                Reciprocal<Product<Mass, Square<Length>>>>
val siemens = cubic(second) * square(ampere) / (kilo(gram) * square(meter)) withSymbol "S"

typealias Energy = Product<Product<Mass, Square<Length>>, Reciprocal<Square<Time>>>
val joule: Energy = kilo(gram) * square(meter) / square(second) withSymbol "J"

typealias EquivalentDose = Product<Square<Length>, Reciprocal<Square<Time>>>
val sievert: EquivalentDose = square(meter) / square(second) withSymbol "Sv"

typealias Force = Product<Product<Mass, Length>, Reciprocal<Square<Time>>>
val newton: Force = (kilo(gram) * meter / square(second)) withSymbol "N"
val poundForce: Force = Mass(
        uni,
        "lbs",
        uni,
        poundAv.baseMagnitude * gravityAcceleration.value) * meter / square(second) withSymbol "lbf"

typealias Frequency = Reciprocal<Time>
val hertz: Frequency = Reciprocal(second) withSymbol "Hz"

typealias Illuminance = Product<LuminousIntensity, Reciprocal<Square<Length>>>
val lux: Illuminance = candela / square(meter) withSymbol "lx"

typealias Inductance =
        Product<Product<Mass, Square<Length>>,
                Reciprocal<Product<Square<Time>, Square<ElectricCurrent>>>>
val henry: Inductance = (kilo(gram) * square(meter) / (square(second) * square(ampere)))
        .withSymbol("H")

typealias LuminousFlux =
        Product<LuminousIntensity, Product<Square<Length>,
                Reciprocal<Square<Length>>>>
val lumen: LuminousFlux = (candela * (square(meter) / square(meter))).withSymbol("lm")

typealias MagneticFlux =
        Product<Product<Mass, Square<Length>>,
                Reciprocal<Product<Square<Time>, ElectricCurrent>>>
val weber: MagneticFlux = kilo(gram) * square(meter) / (square(second) * ampere) withSymbol "Wb"

typealias MagneticFluxDensity = Product<Mass, Reciprocal<Product<Square<Time>, ElectricCurrent>>>
val tesla: MagneticFluxDensity = kilo(gram) / (square(second) * ampere) withSymbol "T"

typealias Power = Product<Product<Mass, Square<Length>>, Reciprocal<Cubic<Time>>>
val watt: Power = kilo(gram) * square(meter) / cubic(second) withSymbol "W"

typealias Pressure = Product<Mass, Reciprocal<Product<Length, Square<Time>>>>
val pascal: Pressure = kilo(gram) / (meter * square(second)) withSymbol "Pa"
val bar: Pressure = pascal withBaseMagnitude 1e5 withSymbol "bar"
val psi: Pressure = poundForce / square(inch) normalizedToTypeOf pascal withSymbol "psi"

typealias Radioactivity = Reciprocal<Time>
val becquerel: Radioactivity = Reciprocal(second) withSymbol "Bq"

typealias Speed = Product<Length, Reciprocal<Time>>
val knot: Speed = (nauticalMile / hour) withSymbol "knot"

typealias SolidAngle = Product<Square<Length>, Reciprocal<Square<Length>>>
val steradian = square(meter) / square(meter) withSymbol "sr"

typealias Voltage =
        Product<Product<Mass, Square<Length>>,
                Reciprocal<Product<Cubic<Time>, ElectricCurrent>>>
val volt: Voltage = kilo(gram) * square(meter) / (cubic(second) * ampere) withSymbol "V"

typealias Volume = Cubic<Length>
val liter: Volume = cubic(Length("dm", uni, 0.1)) withSymbol "l"
