package net.chemistry.arcane_chemistry.api.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.recipes.HardOvenRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.recipes.NickelCompreserRecipe;
import net.chemistry.arcane_chemistry.screen.HardOvenScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static mezz.jei.api.recipe.RecipeType<HardOvenRecipe> HARD_OVEN_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(HardOvenRecipeCategory.UID, HardOvenRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<NickelCompreserRecipe> NICEKL_COMPERESER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(NickelCompreserRecipeCategory.UID, NickelCompreserRecipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var jeiHelpers = registration.getJeiHelpers();

        registration.addRecipeCategories(new HardOvenRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new NickelCompreserRecipeCategory(jeiHelpers.getGuiHelper()));
    }


    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        var world = Minecraft.getInstance().level;
        if (world != null) {

            var hard_oven = world.getRecipeManager();
            registration.addRecipes(HardOvenRecipeCategory.RECIPE_TYPE,
                    getRecipe(hard_oven, ModRecipes.HARD_OVEN_RECIPE_TYPE.get()));

            var nicekl_comperser = world.getRecipeManager();
            registration.addRecipes(NickelCompreserRecipeCategory.RECIPE_TYPE,
                    getRecipe(nicekl_comperser, ModRecipes.NICKEL_COMPRESER_RECIPE_TYPE.get()));
        }

    }

    public <C extends RecipeInput, T extends Recipe<C>> List<T> getRecipe(RecipeManager manager, RecipeType<T> recipeType){
        List<T> list = new ArrayList<>();
        manager.getAllRecipesFor(recipeType).forEach(tRecipeHolder -> {
            list.add(tRecipeHolder.value());
        });
        return list;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){

        var hard_oven = new ItemStack(ModBlocks.HARD_OVEN.get());
        registration.addRecipeCatalyst(hard_oven, HardOvenRecipeCategory.RECIPE_TYPE, RecipeTypes.FUELING);

        var nicekl_comperser = new ItemStack(ModBlocks.NICKEL_COMPRESER.get());
        registration.addRecipeCatalyst(nicekl_comperser, NickelCompreserRecipeCategory.RECIPE_TYPE);

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(HardOvenScreen.class, 81, 38, 24, 17, JEIPlugin.HARD_OVEN_TYPE);
    }
}