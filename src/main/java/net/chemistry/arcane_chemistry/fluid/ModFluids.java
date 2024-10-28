package net.chemistry.arcane_chemistry.fluid;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.chemistry.arcane_chemistry.Arcane_chemistry.MOD_ID;
import static net.chemistry.arcane_chemistry.fluid.ModFluidTypes.FLUID_TYPES;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, MOD_ID);
    public static final DeferredRegister.Blocks FLUID_BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static final Supplier<FlowingFluid> SOURCE_CRUDE_OIL = FLUIDS.register("source_crude_oil",
            () -> new CrudeOilFlowingFluid.Source(ModFluids.CRUDE_OIL_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_CRUDE_OIL = FLUIDS.register("flowing_crude_oil",
            () -> new CrudeOilFlowingFluid.Flowing(ModFluids.CRUDE_OIL_PROPERTIES));

    public static final DeferredBlock<LiquidBlock> CRUDE_OIL_BLOCK = FLUID_BLOCKS.register("crude_oil_block",
            () -> new LiquidBlock(ModFluids.SOURCE_CRUDE_OIL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredItem<Item> CRUDE_OIL_BUCKET = ModItems.ITEMS.registerItem("crude_oil_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_CRUDE_OIL.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final CrudeOilFlowingFluid.Properties CRUDE_OIL_PROPERTIES = new CrudeOilFlowingFluid.Properties(
            ModFluidTypes.CRUDE_OIL_FLUID_TYPE, SOURCE_CRUDE_OIL, FLOWING_CRUDE_OIL)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(1)
            .block(ModFluids.CRUDE_OIL_BLOCK)
            .bucket(ModFluids.CRUDE_OIL_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
        FLUID_BLOCKS.register(eventBus);
    }
}