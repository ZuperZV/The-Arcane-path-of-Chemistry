package net.chemistry.arcane_chemistry.block.custom;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Arcane_chemistry.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModItemBlockRenderTypes {
    @SubscribeEvent
    public static void registerItemModelProperties(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIRE_POT_CAMPFIRE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CENTRIFUGE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELECTOLYZER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLOTATIONER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHAMBER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.LATEX_BOWL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLAY_WIRE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AURORA_WIRE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEDESTAL_SLAB.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AURORA_CRAFTER.get(), RenderType.cutout());
        });
    }
}