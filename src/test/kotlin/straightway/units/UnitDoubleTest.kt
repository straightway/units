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
import org.junit.jupiter.api.Test
import straightway.error.Panic
import straightway.testing.flow.Equal
import straightway.testing.flow.Throw
import straightway.testing.flow.does
import straightway.testing.flow.expect
import straightway.testing.flow.is_
import straightway.testing.flow.to_

class UnitDoubleTest {

    @Test
    fun construction_value() =
            Assertions.assertEquals(2.0, UnitDouble(2.0, testUnit).value)

    @Test
    fun construction_unit() =
            Assertions.assertEquals(testUnit, UnitDouble(2.0, testUnit).unit)

    @Test
    fun construction_withIndexer_value() =
            Assertions.assertEquals(2.0, 2.0[testUnit].value)

    @Test
    fun construction_withIndexer_unit() =
            Assertions.assertEquals(testUnit, 2.0[testUnit].unit)

    @Test
    fun construction_withScaledIndexer_value() =
            Assertions.assertEquals(2.0, 2.0[kilo(testUnit)].value)

    @Test
    fun construction_withScaledIndexer_unit() =
            Assertions.assertEquals(kilo(testUnit), 2.0[kilo(testUnit)].unit)

    @Test
    fun baseValue() =
            Assertions.assertEquals(2000.0, 2.0[kilo(testUnit)].baseValue)

    @Test
    fun baseValue_shifted() =
            Assertions.assertEquals(3.0, 2.0[TestQuantity(uni, 1)].baseValue)

    @Test
    fun baseValue_scaled_shifted() =
            Assertions.assertEquals(2001.0, 2.0[kilo(TestQuantity(uni, 1))].baseValue)

    @Test
    fun toString_unscaled() =
            Assertions.assertEquals("2.0 TU", 2.0[testUnit].toString())

    @Test
    fun toString_scaled() =
            Assertions.assertEquals("2.0 kTU", 2.0[kilo(testUnit)].toString())

    @Test
    fun equals_sameQuantity_sameScale() =
            Assertions.assertTrue(2.0[testUnit] == 2.0[testUnit])

    @Test
    fun equals_sameQuantity_differentScale() =
            Assertions.assertTrue(2000[testUnit] == 2.0[kilo(testUnit)])

    @Test
    fun equals_sameQuantity_differentUnit() =
            Assertions.assertFalse(2.0[testUnit] == 2.0[otherTestUnit])

    @Test
    fun equals_sameQuantity_differentNumberTypes() =
            expect(11.0[testUnit] is_ Equal to_ 1.1[deca(testUnit)])

    @Suppress("ReplaceCallWithComparison")
    @Test
    fun equals_differentTypes() =
            Assertions.assertFalse(2000.0[testUnit].equals("Hallo"))

    @Test
    fun equals_differentProducts() =
            Assertions.assertFalse(2000.0[meter * second] == 2000.0[mol * ampere])

    @Test
    fun equals_shiftedUnit() =
            Assertions.assertTrue(0.0[TestQuantity(uni, 1)] == 1.0[TestQuantity(uni, 0)])

    @Test
    fun compare_sameScale_true() =
            Assertions.assertTrue(1.0[testUnit] < 2.0[testUnit])

    @Test
    fun compare_sameScale_false() =
            Assertions.assertFalse(2.0[testUnit] < 1.0[testUnit])

    @Test
    fun compare_differentScale() =
            Assertions.assertTrue(2.0[testUnit] < 1.0[kilo(testUnit)])

    @Test
    fun compare_differentNumberTypes() =
            Assertions.assertTrue(1.0[testUnit] < 2.0[testUnit])

    @Test
    fun siCorrectedUnit_value() =
            Assertions.assertEquals(2.0, 2.0[siCorrectedTestUnit].value)

    @Test
    fun siCorrectedUnit_scaledValue() =
            Assertions.assertEquals(2.0, 2.0[siCorrectedTestUnit].baseValue)

    @Test
    fun siCorrectedUnit_baseValue() =
            Assertions.assertEquals(2e3, 2.0[siCorrectedTestUnit withScale uni].baseValue)

    @Test
    fun convert_sameUnit() =
            Assertions.assertEquals(1.0, 1.0[testUnit][testUnit].value)

    @Test
    fun convert_targetScaled() =
            Assertions.assertEquals(1_000.0, 1.0[testUnit][milli(testUnit)].value)

    @Test
    fun convert_sourceScaled() =
            Assertions.assertEquals(1_000.0, 1.0[kilo(testUnit)][testUnit].value)

    @Test
    fun convert_bothScaled() =
            Assertions.assertEquals(1_000_000.0, 1.0[kilo(testUnit)][milli(testUnit)].value)

    @Test
    fun convert_scaleCorrected() =
            Assertions.assertEquals(1.0, 1.0[siCorrectedTestUnit][siCorrectedTestUnit].value)

    @Test
    fun convert_scaleCorrected_sourceScaled() =
            Assertions.assertEquals(
                    1_000.0, 1.0[kilo(siCorrectedTestUnit)][uni(siCorrectedTestUnit)].value)

    @Test
    fun convert_scaleCorrected_targetScaled() =
            Assertions.assertEquals(
                    1_000.0, 1.0[uni(siCorrectedTestUnit)][milli(siCorrectedTestUnit)].value)

    @Test
    fun convert_scaleCorrected_bothScaled() =
            Assertions.assertEquals(
                    1_000_000.0, 1.0[kilo(siCorrectedTestUnit)][milli(siCorrectedTestUnit)].value)

    @Test
    fun convert_sourceShifted() =
            Assertions.assertEquals(
                    2.0, 1.0[TestQuantity(uni, 1)][TestQuantity(uni, 0)].value)

    @Test
    fun convert_targetShifted() =
            Assertions.assertEquals(
                    1.0, 2.0[TestQuantity(uni, 0)][TestQuantity(uni, 1)].value)

    @Test
    fun convert_bothShifted() =
            Assertions.assertEquals(
                    1.0, 1.0[TestQuantity(uni, 1)][TestQuantity(uni, 1)].value)

    @Test
    fun convert_sourceShifted_sourceScaled() =
            Assertions.assertEquals(
                    1001.0, 1.0[kilo(TestQuantity(uni, 1))][TestQuantity(uni, 0)].value)

    @Test
    fun convert_sourceShifted_targetScaled() =
            Assertions.assertEquals(
                    0.002, 1.0[TestQuantity(uni, 1)][kilo(TestQuantity(uni, 0))].value)

    @Test
    fun convert_sourceShifted_bothScaled() =
            Assertions.assertEquals(
                    1.1, 1.0[deca(TestQuantity(uni, 1))][deca(TestQuantity(uni, 0))].value)

    @Test
    fun convert_targetShifted_sourceScaled() =
            Assertions.assertEquals(
                    999.0, 1.0[kilo(TestQuantity(uni, 0))][TestQuantity(uni, 1)].value)

    @Test
    fun convert_targetShifted_targetScaled() =
            Assertions.assertEquals(
                    0.001, 2.0[TestQuantity(uni, 0)][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_targetShifted_bothScaled() =
            Assertions.assertEquals(
                    0.999, 1.0[kilo(TestQuantity(uni, 0))][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_bothShifted_sourceScaled() =
            Assertions.assertEquals(
                    1000.0, 1.0[kilo(TestQuantity(uni, 1))][TestQuantity(uni, 1)].value)

    @Test
    fun convert_bothShifted_targetScaled() =
            Assertions.assertEquals(
                    0.002, 2.0[TestQuantity(uni, 1)][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_bothShifted_bothScaled() =
            Assertions.assertEquals(
                    1.0, 1.0[kilo(TestQuantity(uni, 1))][kilo(TestQuantity(uni, 1))].value)

    @Test
    fun convert_compatibleUnit_noScale_value() =
            expect(1.0[(meter * second) / second][meter].value is_ Equal to_ 1.0)

    @Test
    fun convert_compatibleUnit_noScale_unit() =
            expect(1.0[(meter * second) / second][meter].unit is_ Equal to_ meter)

    @Test
    fun convert_compatibleUnit_scaled_value() =
            expect(1.0[(meter * second) / second][milli(meter)].value is_ Equal to_ 1000.0)

    @Test
    fun convert_compatibleUnit_scaled_unit() =
            expect(1.0[(meter * second) / second][milli(meter)].unit is_ Equal to_ milli(meter))

    @Test
    fun convert_compatibleUnit_siCorrected_value() =
            expect(1.0[(kilo(gram) * second) / second][gram].value is_ Equal to_ 1000.0)

    @Test
    fun convert_compatibleUnit_siCorrected_unit() =
            expect(1.0[(kilo(gram) * second) / second][gram].unit is_ Equal to_ gram)

    @Test
    fun convert_compatibleUnit_shifted_value() =
            expect(1.0[(kelvin * second) / second][kilo(celsius)].value is_ Equal to_ -0.27215)

    @Test
    fun convert_compatibleUnit_shifted_unit() =
            expect(1.0[(kelvin * second) / second][celsius].unit is_ Equal to_ celsius)

    @Test
    fun convert_compatibleUnit_withFactor_value() =
            expect(1.0[(pound * second) / second][kilo(gram)].value is_ Equal to_ 0.45359237)

    @Test
    fun convert_compatibleUnit_withFactor_unit() =
            expect(1.0[(pound * second) / second][kilo(gram)].unit is_ Equal to_ kilo(gram))

    @Test
    fun convert_incompatibleUnit() =
            expect({ 1.0[(meter * second) / second][mol] } does Throw.type<Panic>())

    @Test
    fun inFunctionParameter() {
        foo(2.0[testUnit])
        foo(2.7[mega(testUnit)])
        bar(7.0[meter / second])
        bar(7.0[kilo(meter) / hour])
        Assertions.assertEquals(10.0[meter / second].baseValue, 36.0[kilo(meter) / hour].baseValue)
        Assertions.assertTrue(10.0[meter / second] == 36.0[kilo(meter) / hour])
        Assertions.assertTrue(36.0[meter / second] > 36.0[kilo(meter) / hour])
        Assertions.assertTrue(36.0[kilo(meter) / hour] < 36.0[meter / second])
    }

    @Test
    fun add() {
        val a = 1.0[mol]
        val b = 1.0[deca(mol)]
        expect(a + b is_ Equal to_ 11.0[mol])
    }

    @Test
    fun `add Number value as first argument`() {
        val a = 1.0.n[mol]
        val b = 1.0[deca(mol)]
        expect(a + b is_ Equal to_ 11.0[mol])
    }

    @Test
    fun `add Number value as second argument`() {
        val a = 1.0[mol]
        val b = 1.0.n[deca(mol)]
        expect(a + b is_ Equal to_ 11.0[mol])
    }

    @Test
    fun sub() {
        val a = 1.0[mol]
        val b = 1.0[deca(mol)]
        expect(a - b is_ Equal to_ (-9.0)[mol])
    }

    @Test
    fun `sub Number value as first argument`() {
        val a = 1.0.n[mol]
        val b = 1.0[deca(mol)]
        expect(a - b is_ Equal to_ (-9.0)[mol])
    }

    @Test
    fun `sub Number value as second argument`() {
        val a = 1.0[mol]
        val b = 1.0.n[deca(mol)]
        expect(a - b is_ Equal to_ (-9.0)[mol])
    }

    @Test
    fun times_otherUnit() =
            expect(2.0[mol] * 3.0[second] is_ Equal to_ 6.0[mol * second])

    @Test
    fun times_scalar_first() =
            expect(3.0 * (2.0[mol]) is_ Equal to_ 6.0[mol])

    @Test
    fun times_scalar_second() =
            expect((2.0[fahrenheit] * 3.0) is_ Equal to_ 6.0[fahrenheit])

    @Test
    fun `times NumberUnit as first factor`() =
            expect(3.0.n[second] * 2.0[mol] is_ Equal to_ 6.0[mol * second])

    @Test
    fun `times Number scalar as first factor`() =
            expect(3.0.n * 2.0[mol] is_ Equal to_ 6.0[mol])

    @Test
    fun `times NumberUnit as second factor`() =
            expect(2.0[mol] * 3.0.n[second] is_ Equal to_ 6.0[mol * second])

    @Test
    fun `times Number scalar as second factor`() =
            expect(2.0[mol] * 3.0.n is_ Equal to_ 6.0[mol])

    @Test
    fun div_otherUnit() =
            expect(6.0[mol] / 3.0[second] is_ Equal to_ 2.0[mol / second])

    @Test
    fun div_scalar_first() =
            expect(1.0 / (2.0[mol]) is_ Equal to_ 0.5[Reciprocal(mol)])

    @Test
    fun div_scalar_second() =
            expect((2.0[fahrenheit] / 2.0) is_ Equal to_ 1.0[fahrenheit])

    @Test
    fun `div NumberUnit as numerator`() =
            expect(3.0.n[second] / 2.0[mol] is_ Equal to_ 1.5[second / mol])

    @Test
    fun `div Number scalar as numerator`() =
            expect(3.0.n / 2.0[mol] is_ Equal to_ 1.5[one / mol])

    @Test
    fun `div NumberUnit as denominator`() =
            expect(3.0[mol] / 2.0.n[second] is_ Equal to_ 1.5[mol / second])

    @Test
    fun `div Number scalar as denominator`() =
            expect(3.0[mol] / 2.0.n is_ Equal to_ 1.5[mol])

    @Test
    fun unary_minus() =
            expect((-1.0)[mol] is_ Equal to_ -1.0[mol])

    @Test
    fun unary_plus() =
            expect(1.0[mol] is_ Equal to_ +1.0[mol])

    @Test
    fun min() =
            expect(min(2.0[mol], 1.0[mol]) is_ Equal to_ 1.0[mol])

    @Test
    fun max() =
            expect(max(2.0[mol], 1.0[mol]) is_ Equal to_ 2.0[mol])

    @Test
    fun abs_positive() =
            expect(abs(2.0[mol]) is_ Equal to_ 2.0[mol])

    @Test
    fun abs_negative() =
            expect(abs(-2.0[mol]) is_ Equal to_ 2.0[mol])

    @Test
    fun `multiplication without fixed number type`() {
        val unitValue = 3.0[hour]
        val factor = 2.0
        expect(unitValue * factor is_ Equal to_ 6.0[hour])
    }

    @Test
    fun `division without fixed number type`() {
        val unitValue = 3.0[hour]
        val factor: Double = 2.0
        expect(unitValue / factor is_ Equal to_ 1.5[hour])
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