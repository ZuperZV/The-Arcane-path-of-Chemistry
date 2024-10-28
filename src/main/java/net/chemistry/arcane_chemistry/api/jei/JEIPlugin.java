package net.chemistry.arcane_chemistry.api.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.*;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.recipes.*;
import net.chemistry.arcane_chemistry.screen.AtomicOvenScreen;
import net.chemistry.arcane_chemistry.screen.FlotationerScreen;
import net.chemistry.arcane_chemistry.screen.HardOvenScreen;
import net.chemistry.arcane_chemistry.screen.NickelCompreserScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static mezz.jei.api.recipe.RecipeType<HardOvenRecipe> HARD_OVEN_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(HardOvenRecipeCategory.UID, HardOvenRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<AtomicOvenRecipe> ATOMIC_OVEN_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(AtomicOvenRecipeCategory.UID, AtomicOvenRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<NickelCompreserRecipe> NICEKL_COMPERESER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(NickelCompreserRecipeCategory.UID, NickelCompreserRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<CentrifugeRecipe> CENTRIFRUGE_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(CentrifugeRecipeCategory.UID, CentrifugeRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<ElectrolyzerRecipe> ELECTROLYZER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(ElectrolyzerRecipeCategory.UID, ElectrolyzerRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<FirePotRecipe> FIRE_POT_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(FirePotRecipeCategory.UID, FirePotRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<FlotationerRecipe> FLOTATIONER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(FlotationerRecipeCategory.UID, FlotationerRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<LatexBowlRecipe> LATEX_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(LatexBowlRecipeCategory.UID, LatexBowlRecipe.class);




    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var jeiHelpers = registration.getJeiHelpers();

        registration.addRecipeCategories(new HardOvenRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new NickelCompreserRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new AtomicOvenRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new CentrifugeRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new ElectrolyzerRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new FirePotRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new FlotationerRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new LatexBowlRecipeCategory(jeiHelpers.getGuiHelper()));
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

            var atomic_oven = world.getRecipeManager();
            registration.addRecipes(AtomicOvenRecipeCategory.RECIPE_TYPE,
                    getRecipe(atomic_oven, ModRecipes.ATOMIC_OVEN_RECIPE_TYPE.get()));

            var centrifuge = world.getRecipeManager();
            registration.addRecipes(CentrifugeRecipeCategory.RECIPE_TYPE,
                    getRecipe(centrifuge, ModRecipes.CENTRIFUGE_RECIPE_TYPE.get()));

            var Electrolyzer = world.getRecipeManager();
            registration.addRecipes(ElectrolyzerRecipeCategory.RECIPE_TYPE,
                    getRecipe(Electrolyzer, ModRecipes.ELECTOLYZER_RECIPE_TYPE.get()));

            var fire_pot = world.getRecipeManager();
            registration.addRecipes(FirePotRecipeCategory.RECIPE_TYPE,
                    getRecipe(fire_pot, ModRecipes.FIRE_POT_RECIPE_TYPE.get()));

            var flotationer = world.getRecipeManager();
            registration.addRecipes(FlotationerRecipeCategory.RECIPE_TYPE,
                    getRecipe(flotationer, ModRecipes.FLOTATION_RECIPE_TYPE.get()));

            var latex = world.getRecipeManager();
            registration.addRecipes(LatexBowlRecipeCategory.RECIPE_TYPE,
                    getRecipe(latex, ModRecipes.LATEX_BOWL_RECIPE_TYPE.get()));
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

        var atomic_oven = new ItemStack(ModBlocks.ATOMIC_OVEN.get());
        registration.addRecipeCatalyst(atomic_oven, AtomicOvenRecipeCategory.RECIPE_TYPE, RecipeTypes.FUELING);

        var centrifuge = new ItemStack(ModBlocks.CENTRIFUGE.get());
        registration.addRecipeCatalyst(centrifuge, CentrifugeRecipeCategory.RECIPE_TYPE);

        var Electrolyzer = new ItemStack(ModBlocks.ELECTOLYZER.get());
        registration.addRecipeCatalyst(Electrolyzer, ElectrolyzerRecipeCategory.RECIPE_TYPE);

        var fire_pot = new ItemStack(ModBlocks.FIRE_POT.get());
        registration.addRecipeCatalyst(fire_pot, FirePotRecipeCategory.RECIPE_TYPE);

        var flotationer = new ItemStack(ModBlocks.FLOTATIONER.get());
        registration.addRecipeCatalyst(flotationer, FlotationerRecipeCategory.RECIPE_TYPE);

        var latex = new ItemStack(ModBlocks.LATEX_BOWL.get());
        registration.addRecipeCatalyst(latex, LatexBowlRecipeCategory.RECIPE_TYPE);

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(HardOvenScreen.class, 81, 38, 24, 17, JEIPlugin.HARD_OVEN_TYPE);
        registration.addRecipeClickArea(AtomicOvenScreen.class, 81, 38, 24, 17, JEIPlugin.ATOMIC_OVEN_TYPE);
        registration.addRecipeClickArea(NickelCompreserScreen.class, 81, 45, 24, 17, JEIPlugin.NICEKL_COMPERESER_TYPE);
        registration.addRecipeClickArea(FlotationerScreen.class, 36, 22, 24, 17, JEIPlugin.FLOTATIONER_TYPE);
    }
}