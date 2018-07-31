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

import java.io.Serializable

typealias QuantityId = String

/**
 * A quantity which can be attached to a number, forming a unit value. Instances
 * of quantities are units.
 */
interface Quantity : Serializable, Scalable {
    /**
     * The identifier of the quantity. All "compatible" quantities have the same id.
     */
    val id: QuantityId

    /**
     * Gets the absolute base quantity, without scale, baseMagnitude or value shift.
     */
    val baseQuantity: Quantity get() = withScale(uni)

    /**
     * Get a new unit with a different scale.
     */
    infix fun withScale(scale: UnitScale): Quantity
}

val Scalable.siScale get() = scale * siScaleCorrection.reciprocal
operator fun <Q : Quantity> Q.times(other: One) = this.timesScaleOf(other)
@Suppress("UNCHECKED_CAST")
fun <Q : Quantity> Q.timesScaleOf(other: Scalable) = when (other.siScale) {
    uni -> this
    else -> this.withScale(scale * other.scale * other.siScaleCorrection.reciprocal) as Q
}

@Suppress("UNCHECKED_CAST")
fun <Q : Quantity> Q.divScaleOf(other: Quantity) = when (other.siScale) {
    uni -> this
    else -> this.withScale(scale * other.siScaleCorrection * other.scale.reciprocal) as Q
}
