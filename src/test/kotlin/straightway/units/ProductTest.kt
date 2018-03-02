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
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import straightway.error.Panic
import straightway.numbers.times
import straightway.testing.flow.does
import straightway.testing.flow.expect
import straightway.testing.flow.throw_

class ProductTest {

    @Test
    fun toString_containsBothSubExpressions() =
            assertEquals("m*s", Product(second, meter).toString())

    @Test
    fun toString_showsRescaledScale() =
            assertEquals("m(m*s)", kilo(Product(milli(second), milli(meter))).toString())

    @Test
    fun toString_unscaled_showsLeftScale() =
            assertEquals("m*ms", Product(milli(second), meter).toString())

    @Test
    fun toString_unscaled_showsRightScale() =
            assertEquals("mm*s", Product(second, milli(meter)).toString())

    @Test
    fun toString_unscaled_differentScale_sameUnit() =
            assertEquals("k(m²)", Product(mega(meter), milli(meter)).toString())

    @Test
    fun toString_separatesReciprokeFactors() =
            assertEquals(
                    "1/km*ms",
                    Product(Reciprocal(kilo(meter)), Reciprocal(milli(second))).toString())

    @Test
    fun toString_withComplexNumeratorAndDenominator() =
            assertEquals(
                    "Mmol*kK²/ms*m²",
                    Product(
                            kilo(kelvin),
                            Product(
                                    mega(mol),
                                    Product(
                                            kilo(kelvin),
                                            Product(
                                                    Reciprocal(meter),
                                                    Product(
                                                            Reciprocal(meter),
                                                            Reciprocal(milli(second)))))))
                            .toString())

    @Test
    fun id_dependsOnBothComponents() =
            assertNotEquals((second * meter).id, (mol * meter).id)

    @Test
    fun id_dependsOnBothComponents_complexType() = assertNotEquals(
            (square(meter) * square(mol) / (meter * second)).id,
            (square(mol) * meter * second / cubic(second)).id)

    @Test
    fun id_isIndependentOfOrderOfFactors() =
            assertEquals((second * meter).id, (meter * second).id)

    @Test
    fun id_isCancelled_resultOne() =
            assertEquals(one.id, Product(second, Reciprocal(second)).id)

    @Test
    fun id_isCancelled_higherFactor() =
            assertEquals(second.id, Product(square(second), Reciprocal(second)).id)

    @Test
    fun id_isCancelled_higherFactor_reciproke() =
            assertEquals(Reciprocal(second).id, Product(second, Reciprocal(square(second))).id)

    @Test
    fun id_isCancelled_reciproke_reciproke() =
            assertEquals(
                    cubic(second).id,
                    Product(second, Reciprocal(Reciprocal(square(second)))).id)

    @Test
    fun withSymbol_keepsShortId() =
            assertEquals((meter * second).id, (meter * second).withSymbol("X").id)

    @Test
    fun withSymbol_changesStringRepresentation() =
            assertEquals("X", (meter * second).withSymbol("X").toString())

    @Test
    fun scaled_withSymbol_changesStringRepresentation() =
            assertEquals("mX", milli((meter * second).withSymbol("X")).toString())

    @Test
    fun withSymbol_scaledFactors_keepsSiScale() =
            assertEquals(deca, (kilo(meter) * centi(second)).withSymbol("X").siScale)

    @Test
    fun withSymbol_scaledFactors_hasScaleUni() =
            assertEquals(uni, (kilo(meter) * hecto(second)).withSymbol("X").scale)

    @Test
    fun withSymbol_respectsScaleCorrection() =
            assertEquals(milli, (gram * second).withSymbol("X").siScale)

    @Test
    fun withSymbol_ofScaledUni_rescaled() =
            assertEquals("kX", kilo((kilo(meter) * second).withSymbol("X")).toString())

    @Test
    fun equals_sameFactors_sameOrder() =
            assertEquals(meter * second, meter * second)

    @Test
    fun equals_sameFactors_differentOrder() =
            assertEquals(meter * second, second * meter)

    @Test
    fun equals_differentFactors() =
            assertNotEquals(meter * second, mol * meter)

    @Test
    fun equals_sameFactors_differentScale() =
            assertNotEquals(meter * second, meter * milli(second))

    @Test
    fun equals_sameFactors_scaleOnDifferentFactor() =
            assertEquals(meter * milli(second), milli(meter) * second)

    @Test
    fun equals_sameFactors_globalScaleAndFactor1Scale() =
            assertEquals(milli(meter * second), milli(meter) * second)

    @Test
    fun equals_sameFactors_globalScaleAndFactor2Scale() =
            assertEquals(milli(meter * second), meter * milli(second))

    @Test
    fun scale_uni_whenBothSubexpressionsHave_ScaleUni() =
            assertEquals(uni, Product(second, meter).scale)

    @Test
    fun scale_isLeftScale_whenRightIsUni() =
            assertEquals(kilo, Product(second, kilo(meter)).scale)

    @Test
    fun scale_isRightScale_whenLeftIsUni() =
            assertEquals(mega, Product(mega(second), meter).scale)

    @Test
    fun scale_isProductOfBothScalesScale() =
            assertEquals(giga, Product(mega(second), kilo(meter)).scale)

    @Test
    fun scale_ofSquare_differentScale_sameUnit() =
            assertEquals(kilo, Product(mega(meter), milli(meter)).scale)

    @Test
    fun scale_isProductOfBothScalesScale_withLeftScaleCorrection() =
            assertEquals(mega, Product(kilo(gram), mega(second)).scale)

    @Test
    fun scale_isProductOfBothScalesScale_withRightScaleCorrection() =
            assertEquals(mega, Product(mega(second), kilo(gram)).scale)

    @Test
    fun isScalable() =
            assertEquals(giga, kilo(Product(mega(meter), second)).scale)

    @Test
    fun baseMagnitude_ofLeft() =
            assertEquals(inch.baseMagnitude, Product(inch, second).baseMagnitude)

    @Test
    fun baseMagnitude_ofRight() =
            assertEquals(inch.baseMagnitude, Product(second, inch).baseMagnitude)

    @Test
    fun baseMagnitude_ofBoth() =
            assertEquals(
                    inch.baseMagnitude * minute.baseMagnitude,
                    Product(minute, inch).baseMagnitude)

    @Test
    fun normalizeToTypeOf_sameType() =
            assertEquals(joule.left.left, (joule normalizedToTypeOf joule).left.left)

    @Test
    fun normalizeToTypeOf_otherExpression_sameType() =
            assertEquals(joule.left.left, (newton * meter normalizedToTypeOf joule).left.left)

    @Test
    fun normalizeToTypeOf_otherType_throws() =
            expect({ meter * second normalizedToTypeOf joule } does throw_<Panic>())

    @Test
    fun normalizeToTypeOf_keepsSymbol() =
            assertEquals("J", (joule normalizedToTypeOf newton * meter).toString())

    @Test
    fun normalizeToTypeOf_keepsScale() =
            assertEquals(kilo, (kilo(joule) normalizedToTypeOf newton * meter).scale)

    @Test
    fun normalizeToTypeOf_keepsBaseMagnitude() =
            assertEquals(
                    inch.baseMagnitude,
                    (inch * second normalizedToTypeOf second * meter).baseMagnitude)

    @Test
    fun times_createsProduct() =
            assertEquals(Product(meter, second), meter * second)

    @Test
    fun times_self_createsSquare() =
            assertEquals(square(meter), meter * meter)

    @Test
    fun times_leftOne_yieldsRight() =
            assertEquals(meter, one * meter)

    @Test
    fun times_rightOne_yieldsLeft() =
            assertEquals(meter, meter * one)

    @Test
    fun div_createsProductWithReciproke() =
            assertEquals(Product(meter, Reciprocal(second)), meter / second)

    @Test
    fun div_one_createsProductWithReciproke() =
            assertEquals(second / meter, one / (meter / second))

    @Test
    fun square_hasSquaredShortId_withScale() =
            assertEquals(square(meter).id, square(kilo(meter)).id)

    @Test
    fun square_hasSquaredScale() =
            assertEquals(mega, square(kilo(meter)).scale)

    @Test
    fun square_respectsCorrectedSiScale() =
            assertEquals(uni, square(kilo(gram)).scale)

    @Test
    fun square_toString() =
            assertEquals("m²", square(meter).toString())

    @Test
    fun mass_square_toString() =
            assertEquals("kg²", square(kilo(gram)).toString())

    @Test
    fun square_toString_withScale() =
            assertEquals("km²", square(kilo(meter)).toString())

    @Test
    fun cubic_hasSquaredScale() =
            assertEquals(giga, cubic(kilo(meter)).scale)

    @Test
    fun cubic_respectsCorrectedSiScale() =
            assertEquals(uni, cubic(kilo(gram)).scale)

    @Test
    fun cubic_toString() =
            assertEquals("m³", cubic(meter).toString())

    @Test
    fun cubic_toString_withScale() =
            assertEquals("km³", cubic(kilo(meter)).toString())

    @Test
    fun pow4_hasSquaredScale() =
            assertEquals(tera, Product(kilo(meter), cubic(kilo(meter))).scale)

    @Test
    fun pow4_respectsCorrectedSiScale() =
            assertEquals(uni, Product(kilo(gram), cubic(kilo(gram))).scale)

    @Test
    fun pow4_toString() =
            assertEquals("m^4", Product(meter, cubic(meter)).toString())

    @Test
    fun pow4_toString_withScale() =
            assertEquals("km^4", Product(kilo(meter), cubic(kilo(meter))).toString())
}