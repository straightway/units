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

/**
 * Interface for quantities having a scale which might be corrected to get
 * a valid SI base quantity.
 */
interface Scalable {
    /**
     * The scale of the quantity's unit.
     */
    val scale: UnitScale

    /**
     * The correction of the scale of the base SI unit.
     * Some units do not have 'uni' as base scale. This is especially true for masses
     * with the SI unit kilogram.
     */
    val siScaleCorrection: UnitScale get() = uni

    /**
     * Shift of the derived unit value compared to the default SI unit.
     * The value shift is added to SI unit value to get the derived value.
     * Examples: Celsius, Fahrenheit
     */
    val valueShift: Number get() = 0

    /**
     * Define a factor for the magnitude of the unit.
     * E.g. for the non metric units (pounds, feet, etc.).
     */
    val baseMagnitude: Number get() = 1
}