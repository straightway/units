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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MassTest {

    @Test
    fun kiloGram_isBaseUnit() =
            assertEquals(1.0, 1.0[kilo(gram)].baseValue)

    @Test
    fun gram() =
            assertEquals(1e-3, 1.0[gram].baseValue)

    @Test
    fun kilogram_toString_squared() =
            assertEquals("kg²", square(kilo(gram)).toString())

    @Test
    fun ton_is1000Kg() =
            assertEquals(1000[kilo(gram)], 1[ton])

    @Test
    fun ton_hasBaseValue1000() =
            assertEquals(1000, 1[ton].baseValue)

    @Test
    fun compare_lbAv_to_kg() =
            assertEquals(453.59237[gram], 1[poundAv])

    @Test
    fun compare_ozAv_to_lbAv() =
            assertEquals(1[poundAv], 16[ounceAv])

    @Test
    fun compare_lbAp_to_lbAv() =
            assertEquals(
                    (144.0 / 175.0)[poundAv].baseValue.toDouble(),
                    1.0[poundAp].baseValue.toDouble(),
                    1e-6)

    @Test
    fun compare_ozAp_to_lbAp() =
            assertEquals(1[poundAp], 12[ounceAp])

    @Test
    fun compare_lbs_to_lbAv() =
            assertEquals(1[pound], 1[poundAv])

    @Test
    fun compare_oz_to_lb() =
            assertEquals(1[pound], 16[ounce])

    @Test
    fun `add two lb av`() =
            Assertions.assertEquals(2[poundAv], 1[poundAv] + 1[poundAv])

    @Test
    fun `subtract two lb av`() =
            Assertions.assertEquals(1[poundAv], 2[poundAv] - 1[poundAv])

    @Test
    fun `multiply two lb av`() =
            Assertions.assertEquals(1[poundAv*poundAv], 1[poundAv] * 1[poundAv])

    @Test
    fun `divide lb av by kg`() =
            Assertions.assertEquals(0.45359237[one], 1[poundAv] / 1[kilo(gram)])

    @Test
    fun `divide kg by lb av`() =
            Assertions.assertEquals((1.0/0.45359237)[one], 1.0[kilo(gram)] / 1.0[poundAv])
}