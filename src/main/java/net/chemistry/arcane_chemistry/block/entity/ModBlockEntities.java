package net.chemistry.arcane_chemistry.block.entity;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Arcane_chemistry.MOD_ID);



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}