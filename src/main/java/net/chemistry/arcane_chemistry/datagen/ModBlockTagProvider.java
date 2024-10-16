package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Arcane_chemistry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.HARD_OVEN.get())
                .add(ModBlocks.FIRE_POT.get())
                //.add(ModBlocks.FIRE_POT_CAMPFIRE.get())
                .add(ModBlocks.HARD_OVEN.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.HARD_OVEN.get())
                .add(ModBlocks.FIRE_POT.get())
                //.add(ModBlocks.FIRE_POT_CAMPFIRE.get())
                .add(ModBlocks.HARD_OVEN.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
        ;
    }
}