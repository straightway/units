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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VolumeTest {
    @Test fun litre_id() = Assertions.assertEquals(cubic(meter).id, liter.id)
    @Test fun litre_toString() = Assertions.assertEquals("l", liter.toString())
    @Test fun litre_scaling() = Assertions.assertEquals(1000[liter], 1[kilo(liter)])
    @Test fun conversion_litre_to_cubicMeter()
        = Assertions.assertEquals(1.0, 1000[liter][cubic(meter)].value.toDouble(), 1e-6)

    @Test fun conversion_cubicMeter_to_litre()
        = Assertions.assertEquals(1000.0, 1[cubic(meter)][liter].value.toDouble(), 1e-6)
}