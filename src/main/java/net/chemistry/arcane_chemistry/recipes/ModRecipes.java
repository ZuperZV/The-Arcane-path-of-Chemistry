package net.chemistry.arcane_chemistry.recipes;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Arcane_chemistry.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Arcane_chemistry.MOD_ID);

    public static void register(IEventBus eventBus){
        RECIPE_TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }

    public static final Supplier<RecipeType<HardOvenRecipe>> HARD_OVEN_RECIPE_TYPE =
            RECIPE_TYPES.register("hard_oven", () -> HardOvenRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<AtomicOvenRecipe>> ATOMIC_OVEN_RECIPE_TYPE =
            RECIPE_TYPES.register("atomic_oven", () -> AtomicOvenRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<NickelCompreserRecipe>> NICKEL_COMPRESER_RECIPE_TYPE =
            RECIPE_TYPES.register("nickel_compreser", () -> NickelCompreserRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<FirePotRecipe>> FIRE_POT_RECIPE_TYPE =
            RECIPE_TYPES.register("fire_pot", () -> FirePotRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<FlotationerRecipe>> FLOTATION_RECIPE_TYPE =
            RECIPE_TYPES.register("flotation", () -> FlotationerRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<CentrifugeRecipe>> CENTRIFUGE_RECIPE_TYPE =
            RECIPE_TYPES.register("centrifuge", () -> CentrifugeRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<ElectrolyzerRecipe>> ELECTOLYZER_RECIPE_TYPE =
            RECIPE_TYPES.register("electrolyzer", () -> ElectrolyzerRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<TungstenCompreserRecipe>> TUNGSTEN_RECIPE_TYPE =
            RECIPE_TYPES.register("tungsten_compreser", () -> TungstenCompreserRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<ChamberRecipe>> CHAMBER_RECIPE_TYPE =
            RECIPE_TYPES.register("chamber", () -> ChamberRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<AtomicNucleusConstructorRecipe>> ATOMIC_NUCLEUS_CONSTRUCTOR_RECIPE_TYPE =
            RECIPE_TYPES.register("atomic_nucleus_constructor", () -> AtomicNucleusConstructorRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<LatexBowlRecipe>> LATEX_BOWL_RECIPE_TYPE =
            RECIPE_TYPES.register("latex_bowl", () -> LatexBowlRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<PedestalSlabRecipe>> PEDESTAL_SLAB_RECIPE_TYPE =
            RECIPE_TYPES.register("pedestal_slab", () -> PedestalSlabRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<PedestalSlabClayRecipe>> PEDESTAL_SLAB_CLAY_RECIPE_TYPE =
            RECIPE_TYPES.register("pedestal_slab_clay", () -> PedestalSlabClayRecipe.Type.INSTANCE);

    public static final Supplier<RecipeType<PedestalSlabAuroraRecipe>> PEDESTAL_SLAB_AURORA_RECIPE_TYPE =
            RECIPE_TYPES.register("pedestal_slab_aurora", () -> PedestalSlabAuroraRecipe.Type.INSTANCE);




    public static final Supplier<RecipeSerializer<HardOvenRecipe>> HARD_OVEN_SERIALIZER =
            SERIALIZERS.register("hard_oven", () -> HardOvenRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<AtomicOvenRecipe>> ATOMIC_OVEN_SERIALIZER =
            SERIALIZERS.register("atomic_oven", () -> AtomicOvenRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<NickelCompreserRecipe>> NICKEL_COMPRESER_SERIALIZER =
            SERIALIZERS.register("nickel_compreser", () -> NickelCompreserRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<FirePotRecipe>> FIRE_POT_SERIALIZER =
            SERIALIZERS.register("fire_pot", () -> FirePotRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<FlotationerRecipe>> FLOTATION_SERIALIZER =
            SERIALIZERS.register("flotation", () -> FlotationerRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_SERIALIZER =
            SERIALIZERS.register("centrifuge", () -> CentrifugeRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<ElectrolyzerRecipe>> ELECTOLYZER_SERIALIZER =
            SERIALIZERS.register("electrolyzer", () -> ElectrolyzerRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<TungstenCompreserRecipe>> TUNGSTEN_SERIALIZER =
            SERIALIZERS.register("tungsten_compreser", () -> TungstenCompreserRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<ChamberRecipe>> CHAMBER_SERIALIZER =
            SERIALIZERS.register("chamber", () -> ChamberRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<AtomicNucleusConstructorRecipe>> ATOMIC_NUCLEUS_CONSTRUCTOR_SERIALIZER =
            SERIALIZERS.register("atomic_nucleus_constructor", () -> AtomicNucleusConstructorRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<LatexBowlRecipe>> LATEX_BOWL_SERIALIZER =
            SERIALIZERS.register("latex_bowl", () -> LatexBowlRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<PedestalSlabRecipe>> PEDESTAL_SLAB_SERIALIZER =
            SERIALIZERS.register("pedestal_slab", () -> PedestalSlabRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<PedestalSlabClayRecipe>> PEDESTAL_SLAB_CLAY_SERIALIZER =
            SERIALIZERS.register("pedestal_slab_clay", () -> PedestalSlabClayRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<PedestalSlabAuroraRecipe>> PEDESTAL_SLAB_AURORA_SERIALIZER =
            SERIALIZERS.register("pedestal_slab_aurora", () -> PedestalSlabAuroraRecipe.Serializer.INSTANCE);


}