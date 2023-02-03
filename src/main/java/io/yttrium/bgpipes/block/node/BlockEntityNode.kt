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

import io.yttrium.bgpipes.BGPipes
import io.yttrium.bgpipes.util.ItemTransferDirection
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.items.IItemHandler

class BlockEntityNode(pos: BlockPos, blockState: BlockState,
                      var direction: ItemTransferDirection = ItemTransferDirection.Pull
) : BlockEntity(
    BGPipes.BlockEntities[BGPipes.BlockEntityTypes.Node]!!.get(), pos,
    blockState
), IItemHandler {

    private val items = Array<ItemStack>(slots) { _ -> ItemStack.EMPTY }

    override fun getSlots(): Int {
        return 9
    }

    override fun getStackInSlot(slot: Int): ItemStack {
        return items[slot]
    }

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        return if (direction != ItemTransferDirection.Push || !isItemValid(slot, stack)) {
            stack
        } else {
            if (!simulate) {
                items[slot].count = items[slot].count.plus(stack.count).coerceAtMost(items[slot].maxStackSize)
            }
            ItemStack(items[slot].item, items[slot].maxStackSize - stack.count)
        }
    }

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        return if (direction != ItemTransferDirection.Pull || items[slot].isEmpty) {
            ItemStack.EMPTY
        } else {
            if (!simulate) {
                items[slot].count = items[slot].count.minus(amount).coerceAtLeast(0)
            }
            ItemStack(items[slot].item, items[slot].count.minus(amount).coerceAtLeast(0))
        }
    }

    override fun getSlotLimit(slot: Int): Int {
        return items[slot].maxStackSize
    }

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        return stack.`is`(items[slot].item)
    }
}