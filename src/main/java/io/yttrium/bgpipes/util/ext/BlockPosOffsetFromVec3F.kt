/*
 * @author "Hannah Brooke <hannah@mail.yttrium.io>" a.k.a hotel, HotelCalifornia, hotel_california
 *
 * Copyright (c) 2023.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.yttrium.bgpipes.util.ext

import net.minecraft.core.BlockPos
import org.joml.Vector3f
fun BlockPos.offset(step: Vector3f): BlockPos {
    return this.offset(step.x.toInt(), step.y.toInt(), step.z.toInt())
}