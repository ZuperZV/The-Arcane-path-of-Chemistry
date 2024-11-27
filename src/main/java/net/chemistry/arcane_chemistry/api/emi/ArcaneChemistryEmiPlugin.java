package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.recipes.*;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

import java.util.List;

@EmiEntrypoint
public class ArcaneChemistryEmiPlugin implements EmiPlugin {

    public static final EmiStack HARD_OVEN_WORKSTATION = EmiStack.of(ModBlocks.HARD_OVEN.get());
    public static final EmiStack NICKEL_COMPRESER_WORKSTATION = EmiStack.of(ModBlocks.NICKEL_COMPRESER.get());
    public static final EmiStack ATOMIC_OVEN_WORKSTATION = EmiStack.of(ModBlocks.ATOMIC_OVEN.get());
    public static final EmiStack CENTRIFUGE_WORKSTATION = EmiStack.of(ModBlocks.CENTRIFUGE.get());
    public static final EmiStack ELECTROLYZER_WORKSTATION = EmiStack.of(ModBlocks.ELECTOLYZER.get());
    public static final EmiStack FIRE_POT_WORKSTATION = EmiStack.of(ModBlocks.FIRE_POT.get());
    public static final EmiStack FLOTATIONER_WORKSTATION = EmiStack.of(ModBlocks.FLOTATIONER.get());
    public static final EmiStack LATEX_WORKSTATION = EmiStack.of(ModBlocks.LATEX_BOWL.get());
    public static final EmiStack ATOMIC_NUCLEUS_CONSTRUCTOR_WORKSTATION = EmiStack.of(ModBlocks.ATOMIC_NUCLEUS_CONSTRUCTOR.get());
    public static final EmiStack PEDESTAL_SLAB_WORKSTATION = EmiStack.of(ModBlocks.PEDESTAL_SLAB.get());
    public static final EmiStack PEDESTAL_SLAB_CLAY_WORKSTATION = EmiStack.of(ModBlocks.PEDESTAL_SLAB.get());
    public static final EmiStack PEDESTAL_SLAB_CLAY_WORKSTATION_CLAY = EmiStack.of(ModItems.CLAY_DUST.get());
    public static final EmiStack PEDESTAL_SLAB_AURORA_WORKSTATION = EmiStack.of(ModBlocks.PEDESTAL_SLAB.get());
    public static final EmiStack PEDESTAL_SLAB_AURORA_WORKSTATION_AURORA = EmiStack.of(ModItems.AURORA_DUST.get());

    public static final EmiRecipeCategory HARD_OVEN_CATEGORY = new EmiRecipeCategory(HARD_OVEN_WORKSTATION.getId(), HARD_OVEN_WORKSTATION);
    public static final EmiRecipeCategory NICKEL_COMPRESER_CATEGORY = new EmiRecipeCategory(NICKEL_COMPRESER_WORKSTATION.getId(), NICKEL_COMPRESER_WORKSTATION);
    public static final EmiRecipeCategory ATOMIC_OVEN_CATEGORY = new EmiRecipeCategory(ATOMIC_OVEN_WORKSTATION.getId(), ATOMIC_OVEN_WORKSTATION);
    public static final EmiRecipeCategory CENTRIFUGE_CATEGORY = new EmiRecipeCategory(CENTRIFUGE_WORKSTATION.getId(), CENTRIFUGE_WORKSTATION);
    public static final EmiRecipeCategory ELECTROLYZER_CATEGORY = new EmiRecipeCategory(ELECTROLYZER_WORKSTATION.getId(), ELECTROLYZER_WORKSTATION);
    public static final EmiRecipeCategory FIRE_POT_CATEGORY = new EmiRecipeCategory(FIRE_POT_WORKSTATION.getId(), FIRE_POT_WORKSTATION);
    public static final EmiRecipeCategory FLOTATIONER_CATEGORY = new EmiRecipeCategory(FLOTATIONER_WORKSTATION.getId(), FLOTATIONER_WORKSTATION);
    public static final EmiRecipeCategory LATEX_CATEGORY = new EmiRecipeCategory(LATEX_WORKSTATION.getId(), LATEX_WORKSTATION);
    public static final EmiRecipeCategory ATOMIC_NUCLEUS_CONSTRUCTOR_CATEGORY = new EmiRecipeCategory(ATOMIC_NUCLEUS_CONSTRUCTOR_WORKSTATION.getId(), ATOMIC_NUCLEUS_CONSTRUCTOR_WORKSTATION);
    public static final EmiRecipeCategory PEDESTAL_SLAB_CATEGORY = new EmiRecipeCategory(PEDESTAL_SLAB_WORKSTATION.getId(), PEDESTAL_SLAB_WORKSTATION);
    public static final EmiRecipeCategory PEDESTAL_SLAB_CLAY_CATEGORY = new EmiRecipeCategory(PEDESTAL_SLAB_CLAY_WORKSTATION.getId(), PEDESTAL_SLAB_CLAY_WORKSTATION, PEDESTAL_SLAB_CLAY_WORKSTATION_CLAY);
    public static final EmiRecipeCategory PEDESTAL_SLAB_AURORA_CATEGORY = new EmiRecipeCategory(PEDESTAL_SLAB_AURORA_WORKSTATION.getId(), PEDESTAL_SLAB_AURORA_WORKSTATION, PEDESTAL_SLAB_AURORA_WORKSTATION_AURORA);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(HARD_OVEN_CATEGORY);
        registry.addCategory(NICKEL_COMPRESER_CATEGORY);
        registry.addCategory(ATOMIC_OVEN_CATEGORY);
        registry.addCategory(CENTRIFUGE_CATEGORY);
        registry.addCategory(ELECTROLYZER_CATEGORY);
        registry.addCategory(FIRE_POT_CATEGORY);
        registry.addCategory(FLOTATIONER_CATEGORY);
        registry.addCategory(LATEX_CATEGORY);
        registry.addCategory(ATOMIC_NUCLEUS_CONSTRUCTOR_CATEGORY);
        registry.addCategory(PEDESTAL_SLAB_CATEGORY);
        registry.addCategory(PEDESTAL_SLAB_CLAY_CATEGORY);
        registry.addCategory(PEDESTAL_SLAB_AURORA_CATEGORY);

        registry.addWorkstation(HARD_OVEN_CATEGORY, HARD_OVEN_WORKSTATION);
        registry.addWorkstation(NICKEL_COMPRESER_CATEGORY, NICKEL_COMPRESER_WORKSTATION);
        registry.addWorkstation(ATOMIC_OVEN_CATEGORY, ATOMIC_OVEN_WORKSTATION);
        registry.addWorkstation(CENTRIFUGE_CATEGORY, CENTRIFUGE_WORKSTATION);
        registry.addWorkstation(ELECTROLYZER_CATEGORY, ELECTROLYZER_WORKSTATION);
        registry.addWorkstation(FIRE_POT_CATEGORY, FIRE_POT_WORKSTATION);
        registry.addWorkstation(FLOTATIONER_CATEGORY, FLOTATIONER_WORKSTATION);
        registry.addWorkstation(LATEX_CATEGORY, LATEX_WORKSTATION);
        registry.addWorkstation(ATOMIC_NUCLEUS_CONSTRUCTOR_CATEGORY, ATOMIC_NUCLEUS_CONSTRUCTOR_WORKSTATION);
        registry.addWorkstation(PEDESTAL_SLAB_CATEGORY, PEDESTAL_SLAB_WORKSTATION);
        registry.addWorkstation(PEDESTAL_SLAB_CLAY_CATEGORY, PEDESTAL_SLAB_CLAY_WORKSTATION);
        registry.addWorkstation(PEDESTAL_SLAB_CLAY_CATEGORY, PEDESTAL_SLAB_CLAY_WORKSTATION_CLAY);
        registry.addWorkstation(PEDESTAL_SLAB_AURORA_CATEGORY, PEDESTAL_SLAB_AURORA_WORKSTATION);
        registry.addWorkstation(PEDESTAL_SLAB_AURORA_CATEGORY, PEDESTAL_SLAB_AURORA_WORKSTATION_AURORA);

        RecipeManager manager = registry.getRecipeManager();

        for (var recipe : manager.getAllRecipesFor(ModRecipes.HARD_OVEN_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiHardOvenRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.NICKEL_COMPRESER_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiNickelCompreserRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.ATOMIC_OVEN_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiAtomicOvenRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.CENTRIFUGE_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiCentrifugeRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.ELECTOLYZER_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiElectrolyzerRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.FIRE_POT_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiFirePotRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.FLOTATION_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiFlotationerRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.LATEX_BOWL_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiLatexBowlRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.ATOMIC_NUCLEUS_CONSTRUCTOR_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiAtomicNucleusConstructorRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.PEDESTAL_SLAB_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiPedestalSlabRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.PEDESTAL_SLAB_CLAY_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiPedestalSlabClayRecipe(recipe.value()));
        }
        for (var recipe : manager.getAllRecipesFor(ModRecipes.PEDESTAL_SLAB_AURORA_RECIPE_TYPE.get())) {
            registry.addRecipe(new EmiPedestalSlabAuroraRecipe(recipe.value()));
        }
    }
}