package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {

        /*SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.SOFT_CLAY_JAR.get()), RecipeCategory.MISC , ModItems.CLAY_JAR.get(), 0.15f , 200)
                .unlockedBy("has_soft_clay_jar",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SOFT_CLAY_JAR.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "clay_jar_from_blasting"));
         */


        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BLUESTONE.get())
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.BLUESTONE_DUST)
                .unlockedBy("has_bluestone", has(ModItems.BLUESTONE_DUST)).save(pWriter);
         */


        /*ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CLAY_DUST.get())
                .requires(ModItems.HARD_CLAY_BALL.get())
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_hard_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.HARD_CLAY_BALL.get()).build()))
                .save(pWriter);
         */
    }


    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput pRecipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, Arcane_chemistry.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}