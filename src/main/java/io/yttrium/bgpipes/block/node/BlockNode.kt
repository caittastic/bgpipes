/*
 * @author "Hannah Brooke <hannah@mail.yttrium.io>" a.k.a hotel, HotelCalifornia, hotel_california
 *
 * Copyright (c) 2023.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.yttrium.bgpipes.block.node

import io.yttrium.bgpipes.util.ext.filterNotNullValues
import io.yttrium.bgpipes.util.ext.offset

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material

class BlockNode : Block(Properties.of(Material.METAL)), EntityBlock {
    private var inventoryList = emptyMap<Direction, BlockEntity>()
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return BlockEntityNode(pPos, pState)
    }

    override fun setPlacedBy(
        pLevel: Level, pPos: BlockPos, pState: BlockState, pPlacer: LivingEntity?, pStack: ItemStack
    ) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack)
        inventoryList = Direction.values().asIterable()
            .associateBy({ it }, { pLevel.getBlockEntity(BlockPos(pPos.offset(it.step()))) }).filterNotNullValues()
    }
}


