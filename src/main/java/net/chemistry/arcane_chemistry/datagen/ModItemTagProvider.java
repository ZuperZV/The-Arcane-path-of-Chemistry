package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Arcane_chemistry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(ModTags.Items.CRUSHED_RAW_IRON)
                .add(ModItems.RAW_IMPURE_IRON.get())
                .add(ModItems.CRUSHED_RAW_IRON.get());

        this.tag(ModTags.Items.SULFUR)
                .add(ModItems.SULFUR.get());

        this.tag(Tags.Items.INGOTS)
                .add(ModItems.CARVIUM_INGOT.get())
                .add(ModItems.AURORA_INGOT.get())
                .add(ModItems.NICKEL_INGOT.get())
                .add(ModItems.COBALT_INGOT.get())
                .add(ModItems.CARBIDE_INGOT.get())
                .add(ModItems.TUNGSTEN_INGOT.get())
                .add(ModItems.TUNGSTEN_CARBIDE_INGOT.get())
                .add(ModItems.VANADIUM_INGOT.get())
                .add(ModItems.CHROMIUM_INGOT.get());

        this.tag(Tags.Items.RAW_MATERIALS)
                .add(ModItems.RAW_CARVIUM.get())
                .add(ModItems.RAW_NICKEL.get())
                .add(ModItems.RAW_COBALT.get())
                .add(ModItems.RAW_CARBIDE.get());

        this.tag(Tags.Items.TOOLS)
                .add(ModItems.AMAFIST_SWORD.get());
    }
}