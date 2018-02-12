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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SpeedTest {
    @Test
    fun knot_id() =
            assertEquals((meter / second).id, knot.id)

    @Test
    fun knot_toString() =
            assertEquals("knot", knot.toString())

    @Test
    fun knot_baseValue() =
            assertEquals(0.5144444, 1[knot].baseValue.toDouble(), 1e-6)

    @Test
    fun knot_scaling() =
            assertEquals(1000[knot], 1[kilo(knot)])

    @Test
    fun conversion_knot_to_km() =
            assertEquals(1.852, 1.0[knot][kilo(meter) / hour].value.toDouble(), 1e-6)

    @Test
    fun conversion_km_to_knot() =
            assertEquals(1.0, 1.852[kilo(meter) / hour][knot].value.toDouble(), 1e-6)
}