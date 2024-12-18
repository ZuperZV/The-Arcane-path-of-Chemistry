package net.chemistry.arcane_chemistry.item.custom;

import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.util.ModTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier FLINT = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL,
            105, 3f, 1.5f, 3,
            () -> Ingredient.of(Items.FLINT));

    public static final Tier AMAFIST = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_AMAFIST_TOOL,
            10500, 9.0F, 4.5F, 35,
            () -> Ingredient.of(Items.FLINT));
}
