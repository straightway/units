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

/**
 * The scalar quantity.
 */
class One internal constructor(scale: UnitScale)
    : QuantityBase("1", scale, { One(it) }) {
    operator fun <Q : Quantity> times(q: Q) =
            q.timesScaleOf(this)

    operator fun <Q : Quantity> div(q: Q) =
            Reciproke(q.divScaleOf(this))

    operator fun <Q : Quantity> div(q: Reciproke<Q>) = when {
        q.explicitSymbol != null -> Reciproke(q.divScaleOf(this))
        else -> q.wrapped.withScale(uni).timesScaleOf(this).divScaleOf(q)
    }

    @JvmName("div_product_any_reciproke")
    operator fun <QLeft : Quantity, QRight : Quantity> div(q: Product<QLeft, Reciproke<QRight>>) =
            (q.right.wrapped / q.left).timesScaleOf(this)

    @JvmName("div_product_reciproke_any")
    operator fun <QLeft : Quantity, QRight : Quantity> div(q: Product<Reciproke<QLeft>, QRight>) =
            (q.left.wrapped / q.right).timesScaleOf(this)

    @JvmName("div_product_reciproke_reciproke")
    operator fun <QLeft : Quantity, QRight : Quantity> div(q: Product<Reciproke<QLeft>, Reciproke<QRight>>) =
            (q.left.wrapped * q.right.wrapped).timesScaleOf(this)
}

val one = One(uni)