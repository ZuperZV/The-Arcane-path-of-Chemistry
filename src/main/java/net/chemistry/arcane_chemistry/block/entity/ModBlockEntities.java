package net.chemistry.arcane_chemistry.block.entity;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.*;
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

    public static final Supplier<BlockEntityType<AtomicOvenBlockEntity>> ATOMIC_OVEN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("atomic_oven_be", () -> BlockEntityType.Builder.of(
                    AtomicOvenBlockEntity::new, ModBlocks.ATOMIC_OVEN.get()).build(null));

    public static final Supplier<BlockEntityType<FirePotCampfireBlockEntity>> FIRE_POT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fire_pot_be", () -> BlockEntityType.Builder.of(
                    FirePotCampfireBlockEntity::new, ModBlocks.FIRE_POT_CAMPFIRE.get()).build(null));

    public static final Supplier<BlockEntityType<NickelCompreserBlockEntity>> NICKEL_COMPRESER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("nickel_compreser_be", () -> BlockEntityType.Builder.of(
                    NickelCompreserBlockEntity::new, ModBlocks.NICKEL_COMPRESER.get()).build(null));

    public static final Supplier<BlockEntityType<TungstenCompreserBlockEntity>> TUNGSTEN_COMPRESER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("tungsten_compreser_be", () -> BlockEntityType.Builder.of(
                    TungstenCompreserBlockEntity::new, ModBlocks.TUNGSTEN_COMPRESER.get()).build(null));

    public static final Supplier<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("centrifuge_be", () -> BlockEntityType.Builder.of(
                    CentrifugeBlockEntity::new, ModBlocks.CENTRIFUGE.get()).build(null));

    public static final Supplier<BlockEntityType<ElectrolyzerBlockEntity>> ELECTOLYZER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("electrolyzer_be", () -> BlockEntityType.Builder.of(
                    ElectrolyzerBlockEntity::new, ModBlocks.ELECTOLYZER.get()).build(null));

    public static final Supplier<BlockEntityType<FlotationerBlockEntity>> FLOTATIONER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("flotationer_be", () -> BlockEntityType.Builder.of(
                    FlotationerBlockEntity::new, ModBlocks.FLOTATIONER.get()).build(null));

    public static final Supplier<BlockEntityType<HeaterBlockEntity>> HEATER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("heater_be", () -> BlockEntityType.Builder.of(
                    HeaterBlockEntity::new, ModBlocks.HEATER.get()).build(null));

    public static final Supplier<BlockEntityType<DistillationConnecterBlockEntity>> DISTILLATION_CONNECTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("distillation_connecter_be", () -> BlockEntityType.Builder.of(
                    DistillationConnecterBlockEntity::new, ModBlocks.DISTILLATION_CONNECTER.get()).build(null));

    public static final Supplier<BlockEntityType<ChamberBlockEntity>> CHAMBER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("chamber_be", () -> BlockEntityType.Builder.of(
                    ChamberBlockEntity::new, ModBlocks.CHAMBER.get()).build(null));

    public static final Supplier<BlockEntityType<AtomicNucleusConstructorBlockEntity>> ATOMIC_NUCLEUS_CONSTRUCTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("atomic_nucleus_constructor_be", () -> BlockEntityType.Builder.of(
                    AtomicNucleusConstructorBlockEntity::new, ModBlocks.ATOMIC_NUCLEUS_CONSTRUCTOR.get()).build(null));

    public static final Supplier<BlockEntityType<LatexBowlBlockEntity>> LATEX_BOWL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("latex_bowl_be", () -> BlockEntityType.Builder.of(
                    LatexBowlBlockEntity::new, ModBlocks.LATEX_BOWL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}