/*
 * @author "Hannah Brooke <hannah@mail.yttrium.io>" a.k.a hotel, HotelCalifornia, hotel_california
 *
 * Copyright (c) 2023.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.yttrium.bgpipes.block.edge

import io.yttrium.bgpipes.BGPipes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockEntityEdge(pPos: BlockPos, pBlockState: BlockState) : BlockEntity(
    BGPipes.BlockEntities[BGPipes.BlockEntityTypes.Edge]!!.get(), pPos,
    pBlockState
)