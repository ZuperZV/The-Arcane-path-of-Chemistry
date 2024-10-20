package net.chemistry.arcane_chemistry.util;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL = createTag("incorrect_for_flint_tool");
        public static final TagKey<Block> NEEDS_FLINT_TOOL = createTag("needs_flint_tool");



        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> CRUSHED_RAW_IRON = createTag("crushed_raw_iron");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, name));
        }
    }
}