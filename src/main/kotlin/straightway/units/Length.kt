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

import straightway.numbers.times

/**
 * Magnitude for physical length.
 */
class Length constructor(symbol: String, scale: UnitScale, baseMagnitude: Number) :
        QuantityBase(symbol, scale, baseMagnitude, { Length(symbol, it, baseMagnitude) })

val meter = Length("m", uni, 1)
val inch = Length("\"", uni, 0.0254)
val foot = Length("ft", uni, 12 * inch.baseMagnitude)
val yard = Length("yd", uni, 3 * foot.baseMagnitude)
val mile = Length("mile", uni, 1760 * yard.baseMagnitude)
val nauticalMile = Length("NM", uni, 1852)