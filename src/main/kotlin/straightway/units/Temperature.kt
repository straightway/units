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

/**
 * Magnitude for temperature.
 */
data class Temperature
internal constructor(
        private val symbol: String,
        override val scale: UnitScale,
        private val factor: Number,
        override val valueShift: Number)
    : Quantity {
    override val id: QuantityId by lazy { "${this::class.hashCode()}" }
    override val siScaleCorrection by lazy { UnitScale(factor).reciprocal }
    override fun withScale(scale: UnitScale): Quantity =
            Temperature(symbol, scale, factor, valueShift)
    override fun toString() = "$scale$symbol"
}

val kelvin = Temperature("K", uni, 1, 0)
val celsius = Temperature("°C", uni, 1, 273.15)
val fahrenheit = Temperature("°F", uni, 5.0 / 9.0, 459.67 * 5.0 / 9.0)