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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import straightway.numbers.times
import straightway.testing.flow.Equal
import straightway.testing.flow.EqualWithin
import straightway.testing.flow.expect
import straightway.testing.flow.is_
import straightway.testing.flow.to_

class TemperatureTest {

    private companion object {
        val diffOneFahrenheit = (1[fahrenheit] - 0[fahrenheit])[fahrenheit]
    }

    @Test
    fun equals_celsius_kelvin1() =
            assertTrue(273.15[kelvin] == 0[celsius])

    @Test
    fun equals_celsius_kelvin2() =
            assertTrue(373.15[kelvin] == 100[celsius])

    @Test
    fun equals_kelvin_celsius1() =
            assertTrue(0[celsius] == 273.15[kelvin])

    @Test
    fun equals_kelvin_celsius2() =
            assertTrue(100[celsius] == 373.15[kelvin])

    @Test
    fun equals_fahrenheit_kelvin1() =
            assertTrue(273.15[kelvin] == 32[fahrenheit])

    @Test
    fun equals_fahrenheit_kelvin2() =
            assertTrue(373.15[kelvin] == 212[fahrenheit])

    @Test
    fun equals_kelvin_fahrenheit1() =
            assertTrue(32[fahrenheit] == 273.15[kelvin])

    @Test
    fun equals_kelvin_fahrenheit2() =
            assertTrue(212[fahrenheit] == 373.15[kelvin])

    @Test
    fun comparison_kelvin_celsius() =
            assertTrue(0[kelvin] < 0[celsius])

    @Test
    fun comparison_celsius_kelvin() =
            assertTrue(0[celsius] > 0[kelvin])

    @Test
    fun toString_kelvin() =
            assertEquals("K", kelvin.toString())

    @Test
    fun toString_kelvin_scaled() =
            assertEquals("kK", kilo(kelvin).toString())

    @Test
    fun toString_celsius() =
            assertEquals("°C", celsius.toString())

    @Test
    fun toString_celsius_scaled() =
            assertEquals("k°C", kilo(celsius).toString())

    @Test
    fun toString_fahrenheit() =
            assertEquals("°F", fahrenheit.toString())

    @Test
    fun toString_fahrenheit_scaled() =
            assertEquals("k°F", kilo(fahrenheit).toString())

    @Test
    fun id_celsius() = assertEquals(kelvin.id, celsius.id)

    @Test
    fun id_fahrenheit() = assertEquals(kelvin.id, celsius.id)

    @Test
    fun conversion_kelvin_celsius() =
            assertEquals(273.15[kelvin], 0[celsius][kelvin])

    @Test
    fun conversion_celsius_kelvin() =
            assertEquals(0.0[celsius], 273.15[kelvin][celsius])

    @Test
    fun conversion_kelvin_fahrenheit() =
            assertEquals(273.15[kelvin], 32[fahrenheit][kelvin])

    @Test
    fun conversion_fahrenheit_kelvin() =
            assertEquals(32.0, 273.15[kelvin][fahrenheit].value.toDouble(), 1e-6)

    @Test
    fun `add two fahrenheit`() =
        Assertions.assertEquals(2[fahrenheit], 1[fahrenheit] + diffOneFahrenheit)

    @Test
    fun `subtract two fahrenheit`() =
            Assertions.assertEquals(1[fahrenheit], 2[fahrenheit] - diffOneFahrenheit)

    @Test
    fun `multiply two fahrenheit`() =
            expect((0[fahrenheit] * 0[fahrenheit]).value is_ EqualWithin(1e-6)
                           to_ 0[fahrenheit][kelvin].value * 0[fahrenheit][kelvin].value)

    @Test
    fun `unit of two fahrenheit product`() =
            expect((1[fahrenheit] * 1[fahrenheit]).unit is_ Equal to_ kelvin*kelvin)

    @Test
    fun `divide fahrenheit by fahrenheit`() =
            Assertions.assertEquals(1[one], 1[fahrenheit] / 1[fahrenheit])
}