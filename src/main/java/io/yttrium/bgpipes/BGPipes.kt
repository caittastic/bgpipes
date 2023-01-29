/*
 * @author "Hannah Brooke <hannah@mail.yttrium.io>" a.k.a hotel, HotelCalifornia, hotel_california
 *
 * Copyright (c) 2023.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.yttrium.bgpipes

import com.mojang.logging.LogUtils
import io.yttrium.bgpipes.block.edge.BlockEdge
import io.yttrium.bgpipes.block.edge.BlockEntityEdge
import io.yttrium.bgpipes.block.edge.BlockItemEdge
import io.yttrium.bgpipes.block.node.BlockEntityNode
import io.yttrium.bgpipes.block.node.BlockItemNode
import io.yttrium.bgpipes.block.node.BlockNode
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.CreativeModeTabEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

@Mod(BGPipes.ModID)
class BGPipes {
    // this looks stupid but i guess in case i ever add stuff that's not shared across the three of these categories
    // it will be useful? whatever
    enum class BlockTypes {
        Node, Edge,
    }

    enum class BlockEntityTypes {
        Node, Edge,
    }

    enum class ItemTypes {
        Node, Edge,
    }

    companion object {
        const val ModID = "bgpipes"

        private val Logger = LogUtils.getLogger()

        private val BlockRegistry: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, ModID)
        val Blocks: Map<BlockTypes, RegistryObject<Block>> = mapOf(
            BlockTypes.Node to BlockRegistry.register("node", ::BlockNode),
            BlockTypes.Edge to BlockRegistry.register("pipe", ::BlockEdge),
        )

        private val BlockEntityRegistry: DeferredRegister<BlockEntityType<*>> =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModID)
        val BlockEntities = mapOf(
            BlockEntityTypes.Node to BlockEntityRegistry.register("node") {
                BlockEntityType.Builder.of(::BlockEntityNode, BlockNode()).build(null)
            },
            BlockEntityTypes.Edge to BlockEntityRegistry.register("pipe") {
                BlockEntityType.Builder.of(::BlockEntityEdge, BlockEdge()).build(null)
            },
        )

        private val ItemRegistry: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, ModID)
        val Items: Map<ItemTypes, RegistryObject<Item>> = mapOf(
            ItemTypes.Node to ItemRegistry.register("node", ::BlockItemNode),
            ItemTypes.Edge to ItemRegistry.register("pipe", ::BlockItemEdge),
        )
    }

    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        modEventBus.addListener(this::commonSetup)

        BlockRegistry.register(modEventBus)
        BlockEntityRegistry.register(modEventBus)
        ItemRegistry.register(modEventBus)

        MinecraftForge.EVENT_BUS.register(this)
        modEventBus.addListener(this::addCreative)
    }

    private fun addCreative(event: CreativeModeTabEvent.BuildContents) {
        if (event.tab == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            Blocks.filterValues { v ->
                v.get().asItem() != net.minecraft.world.item.Items.AIR
            }.forEach { (_, v) -> event.accept(v) }
        }
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        TODO("Not yet implemented")
    }
}