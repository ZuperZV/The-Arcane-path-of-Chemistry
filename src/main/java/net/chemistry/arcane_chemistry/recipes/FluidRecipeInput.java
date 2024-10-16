package net.chemistry.arcane_chemistry.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidRecipeInput implements RecipeInput {
    private final FluidStack fluidStack;

    public FluidRecipeInput(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    @Override
    public ItemStack getItem(int p_346128_) {
        return null;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    @Override
    public int size() {
        return 0;
    }
}
