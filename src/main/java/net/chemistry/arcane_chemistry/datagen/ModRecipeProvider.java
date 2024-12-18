package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {

        SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get()), RecipeCategory.MISC , ModItems.RAW_NICKEL.get(), 0.1f , 200)
                .unlockedBy("has_raw_impure_nickel_iron_mix",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "raw_nickel_from_blasting"));

        SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.RAW_NICKEL.get()), RecipeCategory.MISC , ModItems.NICKEL_INGOT.get(), 0.1f , 200)
                .unlockedBy("has_raw_nickel",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_NICKEL.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "nickel_ingot_from_blasting"));

        SimpleCookingRecipeBuilder.smelting (Ingredient.of(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get()), RecipeCategory.MISC , ModItems.RAW_NICKEL.get(), 0.15f , 300)
                .unlockedBy("has_raw_impure_nickel_iron_mix",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "raw_nickel_from_smelting"));

        SimpleCookingRecipeBuilder.smelting (Ingredient.of(ModItems.RAW_NICKEL.get()), RecipeCategory.MISC , ModItems.NICKEL_INGOT.get(), 0.15f , 300)
                .unlockedBy("has_raw_nickel",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_NICKEL.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "nickel_ingot_from_smelting"));


        SimpleCookingRecipeBuilder.smelting (Ingredient.of(ModItems.RAW_COBALT.get()), RecipeCategory.MISC , ModItems.COBALT_INGOT.get(), 0.15f , 300)
                .unlockedBy("has_raw_cobalt",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_COBALT.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "raw_cobalt_from_smelting"));

        SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.RAW_COBALT.get()), RecipeCategory.MISC , ModItems.COBALT_INGOT.get(), 0.1f , 200)
                .unlockedBy("has_raw_cobalt",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_COBALT.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "raw_cobalt_from_blasting"));


        SimpleCookingRecipeBuilder.smelting (Ingredient.of(ModItems.RAW_CARBIDE.get()), RecipeCategory.MISC , ModItems.CARBIDE_INGOT.get(), 0.15f , 300)
                .unlockedBy("has_raw_carbide",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_CARBIDE.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "raw_carbide_from_smelting"));

        SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.RAW_CARBIDE.get()), RecipeCategory.MISC , ModItems.CARBIDE_INGOT.get(), 0.1f , 200)
                .unlockedBy("has_raw_carbide",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_CARBIDE.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "raw_carbide_from_blasting"));

        SimpleCookingRecipeBuilder.smelting (Ingredient.of(ModItems.CHROMIUM_CHUNK_MIX.get()), RecipeCategory.MISC , ModItems.SODIUM_CHROMATE.get(), 0.15f , 300)
                .unlockedBy("has_chromium_chunk_mix",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.CHROMIUM_CHUNK_MIX.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "sodium_chromate_from_smelting"));

        SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.CHROMIUM_CHUNK_MIX.get()), RecipeCategory.MISC , ModItems.SODIUM_CHROMATE.get(), 0.1f , 200)
                .unlockedBy("has_chromium_chunk_mix",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.CHROMIUM_CHUNK_MIX.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "sodium_chromate_from_blasting"));

        SimpleCookingRecipeBuilder.smoking (Ingredient.of(ModItems.FLAT_LATEX.get()), RecipeCategory.MISC , ModItems.SMOKED_LATEX.get(), 0.5f , 200)
                .unlockedBy("has_flat_latex",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.FLAT_LATEX.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "smoked_latex_from_smoking"));

        SimpleCookingRecipeBuilder.blasting (Ingredient.of(ModItems.RAW_CARVIUM.get()), RecipeCategory.MISC , ModItems.CARVIUM_INGOT.get(), 0.3f , 200)
                .unlockedBy("has_raw_carvium",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_CARVIUM.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "carvium_ingot_from_blasting"));

        SimpleCookingRecipeBuilder.smelting (Ingredient.of(ModItems.RAW_CARVIUM.get()), RecipeCategory.MISC , ModItems.CARVIUM_INGOT.get(), 0.15f , 300)
                .unlockedBy("has_raw_carvium",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.RAW_CARVIUM.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "carvium_ingot_from_smelting"));


        fourBlockStorageRecipes(pWriter, RecipeCategory.BUILDING_BLOCKS, ModItems.NICKEL_INGOT.get(), RecipeCategory.MISC,
                ModBlocks.NICKEL_BLOCK.get());

        fourBlockStorageRecipes(pWriter, RecipeCategory.BUILDING_BLOCKS, ModItems.VANADIUM_INGOT.get(), RecipeCategory.MISC,
                ModBlocks.VANADIUM_BLOCK);

        fourBlockStorageRecipes(pWriter, RecipeCategory.BUILDING_BLOCKS, ModItems.CHROMIUM_INGOT.get(), RecipeCategory.MISC,
                ModBlocks.CHROMIUM_BLOCK.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HARD_OVEN.get())
                .pattern("A A")
                .pattern("A A")
                .pattern("ABA")
                .define('A', Blocks.STONE)
                .define('B', Blocks.FURNACE)
                .unlockedBy("has_furnace", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.FURNACE).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LATEX_BOWL.get())
                .pattern("ABA")
                .pattern("A A")
                .pattern("AAA")
                .define('A', ItemTags.LOGS)
                .define('B', ModItems.VANADIUM_INGOT)
                .unlockedBy("has_logs", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ItemTags.LOGS).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.NICKEL_COMPRESER.get())
                .pattern("DBD")
                .pattern("CAC")
                .pattern("DDD")
                .define('A', ModBlocks.NICKEL_BLOCK.get())
                .define('B', Blocks.GREEN_STAINED_GLASS_PANE)
                .define('C', Items.REDSTONE)
                .define('D', Items.IRON_INGOT)
                .unlockedBy("has_nickel_block", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.NICKEL_BLOCK.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "nickel_compreser_from_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ELECTOLYZER.get())
                .pattern("A A")
                .pattern("ACA")
                .pattern("BDB")
                .define('A', ModItems.NICKEL_INGOT)
                .define('B', ModBlocks.NICKEL_BLOCK)
                .define('C', Blocks.GLASS)
                .define('D', Items.FLINT)
                .unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NICKEL_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CENTRIFUGE.get())
                .pattern("A A")
                .pattern("ACA")
                .pattern("BDB")
                .define('A', ModItems.CHROMIUM_INGOT)
                .define('B', ModBlocks.CHROMIUM_BLOCK)
                .define('C', Blocks.GLASS)
                .define('D', Items.FLINT)
                .unlockedBy("has_chromium_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.CHROMIUM_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ATOMIC_OVEN.get())
                .pattern("A A")
                .pattern("ACA")
                .pattern("BDB")
                .define('A', ModItems.VANADIUM_INGOT)
                .define('B', ModBlocks.VANADIUM_BLOCK)
                .define('C', ModBlocks.CHROMIUM_BLOCK)
                .define('D', ModItems.SODIUM_CHROMATE)
                .unlockedBy("has_vanadium_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.VANADIUM_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FLOTATIONER.get())
                .pattern("A A")
                .pattern("ACA")
                .pattern("BDB")
                .define('A', ModItems.VANADIUM_INGOT)
                .define('B', ModBlocks.VANADIUM_BLOCK)
                .define('C', ModBlocks.CHROMIUM_BLOCK)
                .define('D', ModItems.CRUSHED_AMETHYST_SHARD)
                .unlockedBy("has_vanadium_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.VANADIUM_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PEDESTAL_SLAB.get())
                .pattern(" B ")
                .pattern("CAC")
                .pattern("BDB")
                .define('A', Blocks.LODESTONE)
                .define('B', Blocks.STONE_BRICKS)
                .define('C', ModItems.LATEX_BALL)
                .define('D', ModItems.AMETHYST_SHARDS)
                .unlockedBy("has_lodestone", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.LODESTONE).build()))
                .save(pWriter);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REAGENT.get())
                .pattern("A A")
                .pattern("A A")
                .pattern(" A ")
                .define('A', Blocks.GLASS)
                .unlockedBy("has_glas", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.GLASS).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "reagent_from_crafting"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FIRE_POT.get())
                .pattern("A A")
                .pattern("A A")
                .pattern("BAB")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.IRON_BLOCK)
                .unlockedBy("has_iron_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "fire_pot_from_crafting"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ATOMIC_NUCLEUS_CONSTRUCTOR.get())
                .pattern("DED")
                .pattern("FBF")
                .pattern("ACA")
                .define('A', ModItems.NICKEL_INGOT)
                .define('B', ModBlocks.NICKEL_BLOCK)
                .define('C', ModBlocks.CHROMIUM_BLOCK)
                .define('D', ModBlocks.VANADIUM_BLOCK)
                .define('E', Blocks.CRAFTER)
                .define('F', ModItems.COBALT_INGOT)
                .unlockedBy("has_cobalt_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.COBALT_INGOT).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "atomic_nucleus_constructor_from_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.LODESTONE)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Blocks.CHISELED_STONE_BRICKS)
                .define('B', ModItems.VANADIUM_INGOT)
                .unlockedBy("has_vanadium_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.VANADIUM_INGOT).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "lodestone_from_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BIO_HARVESTER.get())
                .pattern("DBD")
                .pattern("AEA")
                .pattern("DCD")
                .define('A', ModBlocks.NICKEL_BLOCK)
                .define('B', Blocks.DIRT)
                .define('C', ModBlocks.CHROMIUM_BLOCK)
                .define('D', ModItems.NICKEL_INGOT)
                .define('E', Items.WATER_BUCKET)
                .unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NICKEL_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ADVANCED_BIO_HARVESTER.get())
                .pattern("D D")
                .pattern("AEA")
                .pattern("ACA")
                .define('A', ModBlocks.VANADIUM_BLOCK)
                .define('C', ModBlocks.BIO_HARVESTER)
                .define('D', ModItems.VANADIUM_INGOT)
                .define('E', Items.WATER_BUCKET)
                .unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NICKEL_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMAFIST_SWORD.get())
                .pattern("A")
                .pattern("B")
                .pattern("C")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', ModItems.AURORA_INGOT)
                .define('C', ModItems.AMETHYST_SHARDS)
                .unlockedBy("has_aurora_ingot", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.AURORA_INGOT).build()))
                .save(pWriter);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_IRON_REAGENT.get())
                .requires(ModItems.IRON_REAGENT.get())
                .requires(Items.REDSTONE)
                .unlockedBy("has_iron_reagent", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.IRON_REAGENT.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VANADIUM_INGOT.get())
                .requires(ModItems.VANADIUM_CATALYST.get())
                .requires(ModTags.Items.CRUSHED_RAW_IRON)
                .unlockedBy("has_vanadium_catalyst", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.VANADIUM_INGOT.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHROMIUM_CHUNK_MIX.get())
                .requires(ModItems.SODIUM)
                .requires(ModItems.CARBON_CHUNK.get())
                .requires(ModBlocks.LIMESTONE.get())
                .unlockedBy("has_limestone", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.LIMESTONE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NUCLEUS.get())
                .requires(ModItems.SMOKED_LATEX)
                .requires(ModItems.PROTON)
                .requires(ModItems.NEUTRON)
                .unlockedBy("has_proton", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.PROTON.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IRON_NUCLEUS.get())
                .requires(ModItems.SMOKED_LATEX)
                .requires(Items.IRON_NUGGET)
                .requires(ModItems.NUCLEUS)
                .unlockedBy("has_nucleus", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NUCLEUS.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ALUMINIUM_NUCLEUS.get())
                .requires(ModItems.SMOKED_LATEX)
                .requires(Items.IRON_INGOT)
                .requires(Items.GOLD_NUGGET)
                .requires(ModItems.NUCLEUS)
                .unlockedBy("has_nucleus", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NUCLEUS.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LEAD_NUCLEUS.get())
                .requires(ModItems.SMOKED_LATEX)
                .requires(Items.IRON_INGOT)
                .requires(Items.IRON_INGOT)
                .requires(Items.GOLD_NUGGET)
                .requires(ModItems.NUCLEUS)
                .unlockedBy("has_nucleus", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NUCLEUS.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_NUCLEUS.get())
                .requires(ModItems.SMOKED_LATEX)
                .requires(Items.COPPER_INGOT)
                .requires(Items.GOLD_NUGGET)
                .requires(ModItems.NUCLEUS)
                .unlockedBy("has_nucleus", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NUCLEUS.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILVER_NUCLEUS.get())
                .requires(ModItems.SMOKED_LATEX)
                .requires(Items.IRON_BLOCK)
                .requires(ModItems.NUCLEUS)
                .unlockedBy("has_nucleus", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.NUCLEUS.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_IMPURE_NICKEL.get())
                .requires(ModItems.RAW_IMPURE_IRON)
                .requires(Blocks.ANDESITE)
                .requires(Blocks.ANDESITE)
                .requires(Blocks.ANDESITE)
                .requires(Blocks.GRAVEL)
                .requires(Blocks.GRAVEL)
                .unlockedBy("has_raw_impure_iron", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.RAW_IMPURE_IRON.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LATEX_CLAY_BALL.get())
                .requires(Items.CLAY)
                .requires(ModItems.SMOKED_LATEX)
                .unlockedBy("has_smoked_latex", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.SMOKED_LATEX.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SKRAP_AURORA.get())
                .requires(ModItems.AURORA_DUST)
                .requires(Items.NETHERITE_SCRAP)
                .unlockedBy("has_aurora_dust", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.AURORA_DUST.get()).build()))
                .save(pWriter);
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

    protected static void fourBlockStorageRecipes(
            RecipeOutput p_301057_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_
    ) {
        fourBlockStorageRecipes(
                p_301057_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), null, getSimpleRecipeName(p_251689_), null
        );
    }

    protected static void fourBlockStorageRecipes(
            RecipeOutput p_301222_,
            RecipeCategory p_250083_,
            ItemLike p_250042_,
            RecipeCategory p_248977_,
            ItemLike p_251911_,
            String p_250475_,
            @Nullable String p_248641_,
            String p_252237_,
            @Nullable String p_250414_
    ) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 4)
                .requires(p_251911_)
                .group(p_250414_)
                .unlockedBy(getHasName(p_251911_), has(p_251911_))
                .save(p_301222_, ResourceLocation.parse(p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_)
                .define('#', p_250042_)
                .pattern("##")
                .pattern("##")
                .group(p_248641_)
                .unlockedBy(getHasName(p_250042_), has(p_250042_))
                .save(p_301222_, ResourceLocation.parse(p_250475_));
    }
}