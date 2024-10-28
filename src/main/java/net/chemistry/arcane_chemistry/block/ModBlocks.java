package net.chemistry.arcane_chemistry.block;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.custom.*;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Arcane_chemistry.MOD_ID);

    public static final DeferredBlock<Block> HARD_OVEN = registerBlock("hard_oven",
            () -> new HardOvenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> ATOMIC_NUCLEUS_CONSTRUCTOR = registerBlock("atomic_nucleus_constructor",
            () -> new AtomicNucleusConstructorBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> CENTRIFUGE = registerBlock("centrifuge",
            () -> new CentrifugeBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

    public static final DeferredBlock<Block> LATEX_BOWL = registerBlock("latex_bowl",
            () -> new LatexBowlBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

    public static final DeferredBlock<Block> ELECTOLYZER = registerBlock("electrolyzer",
            () -> new ElectrolyzerBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

    public static final DeferredBlock<Block> ATOMIC_OVEN = registerBlock("atomic_oven",
            () -> new AtomicOvenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

    public static final DeferredBlock<Block> FIRE_POT = registerBlock("fire_pot",
            () -> new FirePotBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL), "Fe", 0xffda39));

    public static final DeferredBlock<Block> FIRE_POT_CAMPFIRE = registerBlock("fire_pot_campfire",
            () -> new FirePotCampfireBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion(), "Fe", 0xffda39));

    public static final DeferredBlock<Block> NICKEL_BLOCK = registerBlock("nickel_block",
            () -> new ElemenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL), "Ni", 0xffda39));

    public static final DeferredBlock<Block> TUNGSTEN_BLOCK = registerBlock("tungsten_block",
            () -> new ElemenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL), "W", 0xffda39));

    public static final DeferredBlock<Block> VANADIUM_BLOCK = registerBlock("vanadium_block",
            () -> new ElemenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL), "V", 0xffda39));

    public static final DeferredBlock<Block> CHROMIUM_BLOCK = registerBlock("chromium_block",
            () -> new ElemenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL), "Cr", 0xffda39));

    public static final DeferredBlock<Block> NICKEL_COMPRESER = registerBlock("nickel_compreser",
            () -> new NickelCompreserBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion(), "Ni", 0xffda39));

    public static final DeferredBlock<Block> TUNGSTEN_COMPRESER = registerBlock("tungsten_compreser",
            () -> new TungstenCompreserBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion(), "Ni", 0xffda39));

    public static final DeferredBlock<Block> FLOTATIONER = registerBlock("flotationer",
            () -> new FlotationerBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion(), "Ni", 0xffda39));

    public static final DeferredBlock<Block> LIMESTONE = registerBlock("limestone",
            () -> new ElemenBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion(), "CaCO₃", 0xffda39));

    public static final DeferredBlock<Block> HEATER = registerBlock("heater",
            () -> new HeaterBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> DISTILLATION_CONNECTER = registerBlock("distillation_connecter",
            () -> new DistillationConnecterBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> CHAMBER = registerBlock("chamber",
            () -> new ChamberBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion(), "Ni", 0xffda39));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return p_50763_ -> p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}