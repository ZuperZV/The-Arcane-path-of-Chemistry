package net.chemistry.arcane_chemistry.block.entity;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.FirePotCampfireBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.HardOvenBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Arcane_chemistry.MOD_ID);

    public static final Supplier<BlockEntityType<HardOvenBlockEntity>> HARD_OVEN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("hard_oven_be", () -> BlockEntityType.Builder.of(
                    HardOvenBlockEntity::new, ModBlocks.HARD_OVEN.get()).build(null));

    public static final Supplier<BlockEntityType<FirePotCampfireBlockEntity>> FIRE_POT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fire_pot_be", () -> BlockEntityType.Builder.of(
                    FirePotCampfireBlockEntity::new, ModBlocks.FIRE_POT_CAMPFIRE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}