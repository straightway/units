/*
 * ***************************************************************************
 * Copyright 2016 github.com/straightway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  ***************************************************************************
 *
 */
package straightway.units

import straightway.numbers.div
import straightway.numbers.times

class Mass constructor(
        scale: UnitScale,
        symbol: String,
        override val siScaleCorrection: UnitScale,
        override val baseMagnitude: Number)
    : QuantityBase(symbol, scale, { Mass(it, symbol, siScaleCorrection, baseMagnitude) })

val gramm = Mass(uni, "g", kilo, 1)
val ton = Mass(uni, "t", uni, 1_000)
val poundAv = Mass(uni, "lb. av.", uni, 0.45359237)
val ounceAv = Mass(uni, "oz. av.", uni, poundAv.baseMagnitude / 16)
val poundAp = Mass(uni, "lb. ap.", uni, poundAv.baseMagnitude * 144 / 175)
val ounceAp = Mass(uni, "oz. ap.", uni, poundAp.baseMagnitude / 12)
val pound = Mass(uni, "lbs", uni, poundAv.baseMagnitude)
val ounce = Mass(uni, "oz", uni, pound.baseMagnitude / 16)
