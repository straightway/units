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

import org.junit.jupiter.api.Test
import straightway.testing.bdd.Given
import straightway.testing.flow.Equal
import straightway.testing.flow.expect
import straightway.testing.flow.is_
import straightway.testing.flow.to_

class UnitValueRangeTest {

    @Test
    fun `range start is accessible`() =
            Given {
                UnitValueRange(1[hour], 2[hour])
            } when_ {
                start
            } then {
                expect(it.result is_ Equal to_ 1[hour])
            }

    @Test
    fun `range end is accessible`() =
            Given {
                UnitValueRange(1[hour], 2[hour])
            } when_ {
                endInclusive
            } then {
                expect(it.result is_ Equal to_ 2[hour])
            }

    @Test
    fun `string represensation`() =
            Given {
                UnitValueRange(1[hour], 2[hour])
            } when_ {
                toString()
            } then {
                expect(it.result is_ Equal to_ "1 h..2 h")
            }
}