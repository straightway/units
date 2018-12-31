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
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import straightway.error.Panic
import straightway.testing.flow.does
import straightway.testing.flow.Equal
import straightway.testing.flow.expect
import straightway.testing.flow.is_
import straightway.testing.flow.Throw
import straightway.testing.flow.to_

class UnitNumberTest {

    @Test
    fun construction_value() =
            assertEquals(2.n, UnitNumber(2.n, testUnit).value)

    @Test
    fun construction_unit() =
            assertEquals(testUnit, UnitNumber(2.n, testUnit).unit)

    @Test
    fun construction_withIndexer_value() =
            assertEquals(2.n, 2.n[testUnit].value)

    @Test
    fun construction_withIndexer_unit() =
            assertEquals(testUnit, 2.n[testUnit].unit)

    @Test
    fun construction_withScaledIndexer_value() =
            assertEquals(2.n, 2.n[kilo(testUnit)].value)

    @Test
    fun construction_withScaledIndexer_unit() =
            assertEquals(kilo(testUnit), 2.n[kilo(testUnit)].unit)

    @Test
    fun baseValue() =
            assertEquals(2000.n, 2.n[kilo(testUnit)].baseValue)

    @Test
    fun baseValue_shifted() =
            assertEquals(3.n, 2.n[TestQuantity(uni, 1)].baseValue)

    @Test
    fun baseValue_scaled_shifted() =
            assertEquals(2001.n, 2.n[kilo(TestQuantity(uni, 1))].baseValue)

    @Test
    fun toString_unscaled() =
            assertEquals("2 TU", 2.n[testUnit].toString())

    @Test
    fun toString_scaled() =
            assertEquals("2 kTU", 2.n[kilo(testUnit)].toString())

    @Test
    fun equals_sameQuantity_sameScale() =
            assertTrue(2.n[testUnit] == 2.n[testUnit])

    @Test
    fun equals_sameQuantity_differentScale() =
            assertTrue(2000.n[testUnit] == 2.n[kilo(testUnit)])

    @Test
    fun equals_sameQuantity_differentUnit() =
            assertFalse(2.n[testUnit] == 2.n[otherTestUnit])

    @Test
    fun equals_sameQuantity_differentNumberTypes() =
            expect(11.n[testUnit] is_ Equal to_ 1.1.n[deca(testUnit)])

    @Suppress("ReplaceCallWithComparison")
    @Test
    fun equals_differentTypes() =
            assertFalse(2000.n[testUnit].equals("Hallo"))

    @Test
    fun equals_differentProducts() =
            assertFalse(2000.n[meter * second] == 2000.n[mol * ampere])

    @Test
    fun equals_shiftedUnit() =
            assertTrue(0.n[TestQuantity(uni, 1.n)] == 1.n[TestQuantity(uni, 0.n)])

    @Test
    fun compare_sameScale_true() =
            assertTrue(1.n[testUnit] < 2.n[testUnit])

    @Test
    fun compare_sameScale_false() =
            assertFalse(2.n[testUnit] < 1.n[testUnit])

    @Test
    fun compare_differentScale() =
            assertTrue(2.n[testUnit] < 1.n[kilo(testUnit)])

    @Test
    fun compare_differentNumberTypes() =
            assertTrue(1.0.n[testUnit] < 2.n[testUnit])

    @Test
    fun siCorrectedUnit_value() =
            assertEquals(2.n, 2.n[siCorrectedTestUnit].value)

    @Test
    fun siCorrectedUnit_scaledValue() =
            assertEquals(2.0.n, 2.0.n[siCorrectedTestUnit].baseValue)

    @Test
    fun siCorrectedUnit_baseValue() =
            assertEquals(2e3.n, 2.0.n[siCorrectedTestUnit withScale uni].baseValue)

    @Test
    fun convert_sameUnit() =
            assertEquals(1.n, 1.n[testUnit][testUnit].value)

    @Test
    fun convert_targetScaled() =
            assertEquals(1_000.n, 1.n[testUnit][milli(testUnit)].value)

    @Test
    fun convert_sourceScaled() =
            assertEquals(1_000.n, 1.n[kilo(testUnit)][testUnit].value)

    @Test
    fun convert_bothScaled() =
            assertEquals(1_000_000.n, 1.n[kilo(testUnit)][milli(testUnit)].value)

    @Test
    fun convert_scaleCorrected() =
            assertEquals(1.n, 1.n[siCorrectedTestUnit][siCorrectedTestUnit].value)

    @Test
    fun convert_scaleCorrected_sourceScaled() =
            assertEquals(1_000.n, 1.n[kilo(siCorrectedTestUnit)][uni(siCorrectedTestUnit)].value)

    @Test
    fun convert_scaleCorrected_targetScaled() =
            assertEquals(1_000.n, 1.n[uni(siCorrectedTestUnit)][milli(siCorrectedTestUnit)].value)

    @Test
    fun convert_scaleCorrected_bothScaled() =
            assertEquals(
                    1_000_000.n, 1.n[kilo(siCorrectedTestUnit)][milli(siCorrectedTestUnit)].value)

    @Test
    fun convert_sourceShifted() =
            assertEquals(2.n, 1.n[TestQuantity(uni, 1)][TestQuantity(uni, 0)].value)

    @Test
    fun convert_targetShifted() =
            assertEquals(1.n, 2.n[TestQuantity(uni, 0)][TestQuantity(uni, 1)].value)

    @Test
    fun convert_bothShifted() =
            assertEquals(1.n, 1.n[TestQuantity(uni, 1)][TestQuantity(uni, 1)].value)

    @Test
    fun convert_sourceShifted_sourceScaled() =
            assertEquals(1001.n, 1.n[kilo(TestQuantity(uni, 1))][TestQuantity(uni, 0)].value)

    @Test
    fun convert_sourceShifted_targetScaled() =
            assertEquals(0.002.n, 1.n[TestQuantity(uni, 1)][kilo(TestQuantity(uni, 0))].value)

    @Test
    fun convert_sourceShifted_bothScaled() =
            assertEquals(1.1.n, 1.n[deca(TestQuantity(uni, 1))][deca(TestQuantity(uni, 0))].value)

    @Test
    fun convert_targetShifted_sourceScaled() =
            assertEquals(999.n, 1.n[kilo(TestQuantity(uni, 0))][TestQuantity(uni, 1)].value)

    @Test
    fun convert_targetShifted_targetScaled() =
            assertEquals(0.001.n, 2.n[TestQuantity(uni, 0)][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_targetShifted_bothScaled() =
            assertEquals(0.999.n, 1.n[kilo(TestQuantity(uni, 0))][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_bothShifted_sourceScaled() =
            assertEquals(1000.n, 1.n[kilo(TestQuantity(uni, 1))][TestQuantity(uni, 1)].value)

    @Test
    fun convert_bothShifted_targetScaled() =
            assertEquals(0.002.n, 2.n[TestQuantity(uni, 1)][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_bothShifted_bothScaled() =
            assertEquals(1.0.n, 1.n[kilo(TestQuantity(uni, 1))][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_compatibleUnit_noScale_value() =
            expect(1.n[(meter * second) / second][meter].value is_ Equal to_ 1.0.n)

    @Test
    fun convert_compatibleUnit_noScale_unit() =
            expect(1.n[(meter * second) / second][meter].unit is_ Equal to_ meter)

    @Test
    fun convert_compatibleUnit_scaled_value() =
            expect(1.n[(meter * second) / second][milli(meter)].value is_ Equal to_ 1000.0.n)

    @Test
    fun convert_compatibleUnit_scaled_unit() =
            expect(1.n[(meter * second) / second][milli(meter)].unit is_ Equal to_ milli(meter))

    @Test
    fun convert_compatibleUnit_siCorrected_value() =
            expect(1.n[(kilo(gram) * second) / second][gram].value is_ Equal to_ 1000.0.n)

    @Test
    fun convert_compatibleUnit_siCorrected_unit() =
            expect(1.n[(kilo(gram) * second) / second][gram].unit is_ Equal to_ gram)

    @Test
    fun convert_compatibleUnit_shifted_value() =
            expect(1.n[(kelvin * second) / second][kilo(celsius)].value is_ Equal to_ -0.27215)

    @Test
    fun convert_compatibleUnit_shifted_unit() =
            expect(1.n[(kelvin * second) / second][celsius].unit is_ Equal to_ celsius)

    @Test
    fun convert_compatibleUnit_withFactor_value() =
            expect(1.n[(pound * second) / second][kilo(gram)].value is_ Equal to_ 0.45359237.n)

    @Test
    fun convert_compatibleUnit_withFactor_unit() =
            expect(1.n[(pound * second) / second][kilo(gram)].unit is_ Equal to_ kilo(gram))

    @Test
    fun convert_incompatibleUnit() =
            expect({ 1.n[(meter * second) / second][mol] } does Throw.type<Panic>())

    @Test
    fun inFunctionParameter() {
        foo(2.n[testUnit])
        foo(2.7.n[mega(testUnit)])
        bar(7.n[meter / second])
        bar(7.n[kilo(meter) / hour])
        assertEquals(10.0.n[meter / second].baseValue, 36.0.n[kilo(meter) / hour].baseValue)
        assertTrue(10.0.n[meter / second] == 36.0.n[kilo(meter) / hour])
        assertTrue(36.0.n[meter / second] > 36.0.n[kilo(meter) / hour])
        assertTrue(36.0.n[kilo(meter) / hour] < 36.0.n[meter / second])
    }

    @Test
    fun add() {
        val a = 1.n[mol]
        val b = 1.n[deca(mol)]
        expect(a + b is_ Equal to_ 11.n[mol])
    }

    @Test
    fun sub() {
        val a = 1.n[mol]
        val b = 1.n[deca(mol)]
        expect(a - b is_ Equal to_ (-9).n[mol])
    }

    @Test
    fun times_otherUnit() =
            expect(2.n[mol] * 3.n[second] is_ Equal to_ 6.n[mol * second])

    @Test
    fun times_scalar_first() =
            expect(3.n * (2.n[mol]) is_ Equal to_ 6.n[mol])

    @Test
    fun times_scalar_second() =
            expect((2.n[fahrenheit] * 3.n) is_ Equal to_ 6.n[fahrenheit])

    @Test
    fun div_otherUnit() =
            expect(6.n[mol] / 3.n[second] is_ Equal to_ 2.n[mol / second])

    @Test
    fun div_scalar_first() =
            expect(1.0.n / (2.0.n[mol]) is_ Equal to_ 0.5.n[Reciprocal(mol)])

    @Test
    fun div_scalar_second() =
            expect((2.n[fahrenheit] / 2.n) is_ Equal to_ 1.n[fahrenheit])

    @Test
    fun unary_minus() =
            expect((-1).n[mol] is_ Equal to_ -(1.n[mol]))

    @Test
    fun unary_plus() =
            expect(1.n[mol] is_ Equal to_ +(1.n[mol]))

    @Test
    fun min() =
            expect(min(2.n[mol], 1.n[mol]) is_ Equal to_ 1.n[mol])

    @Test
    fun max() =
            expect(max(2.n[mol], 1.n[mol]) is_ Equal to_ 2.n[mol])

    @Test
    fun abs_positive() =
            expect(abs(2.n[mol]) is_ Equal to_ 2.n[mol])

    @Test
    fun abs_negative() =
            expect(abs(-2.n[mol]) is_ Equal to_ 2.n[mol])

    @Test
    fun `multiplication without fixed number type`() {
        val unitValue = 3.n[hour]
        val factor: Number = 2.n
        expect(unitValue * factor is_ Equal to_ 6.n[hour])
    }

    @Test
    fun `division without fixed number type`() {
        val unitValue = 3.0.n[hour]
        val factor: Number = 2.n
        expect(unitValue / factor is_ Equal to_ 1.5.n[hour])
    }

    // region Private

    @Suppress("UNUSED_PARAMETER")
    private fun foo(v: UnitValue<TestQuantity>) {
    }

    @Suppress("UNUSED_PARAMETER")
    private fun bar(v: UnitValue<Product<Length, Reciprocal<Time>>>) {
    }

    private open class TestQuantity(scale: UnitScale, override val valueShift: Number) :
            QuantityBase("TU", scale, { TestQuantity(it, valueShift) })

    private val testUnit = TestQuantity(uni, 0)

    private open class SiCorrectedTestQuantity(scale: UnitScale, override val valueShift: Number) :
            QuantityBase("STU", scale, { SiCorrectedTestQuantity(it, valueShift) }) {
        override val siScaleCorrection = milli
    }

    private val siCorrectedTestUnit = SiCorrectedTestQuantity(milli, 0)

    private open class OtherTestQuantity(scale: UnitScale, override val valueShift: Number) :
            QuantityBase("OTU", scale, { OtherTestQuantity(it, valueShift) })

    private val otherTestUnit = OtherTestQuantity(uni, 0)

    private val Number.n get() = this

    // endregion
}