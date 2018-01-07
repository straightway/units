/****************************************************************************
Copyright 2016 github.com/straightway

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ****************************************************************************/
package straightway.units

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import straightway.numbers.times

class UnitScaleTest {
    @Test fun reciproke_timesOriginal_isUni() =
        assertEquals(1.0, kilo.magnitude * kilo.reciproke.magnitude)
    @Test fun construction_withNonPredefinedMagnitude_autoCreatesPrefix() =
        assertEquals("[10000]", UnitScale(10000).prefix)
    @Test fun construction_withPredefinedMagnitude_autoUsesPredefinedPrefix() =
        assertEquals("k", UnitScale(1000).prefix)
    @Test fun construction_withPredefinedMagnitudeDouble_autoUsesPredefinedPrefix() =
        assertEquals("k", UnitScale(1000.0).prefix)

    @Test fun scalingAUnit() =
        assertEquals(kilo, kilo(meter).scale)

    @Test fun scalingAUnit_twice() =
        assertEquals(mega, kilo(kilo(one)).scale)
}