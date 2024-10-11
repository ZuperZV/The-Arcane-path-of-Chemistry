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

    public static final Supplier<RecipeType<FirePotRecipe>> FIRE_POT_RECIPE_TYPE =
            RECIPE_TYPES.register("fire_pot", () -> FirePotRecipe.Type.INSTANCE);


    public static final Supplier<RecipeSerializer<HardOvenRecipe>> HARD_OVEN_SERIALIZER =
            SERIALIZERS.register("hard_oven", () -> HardOvenRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<FirePotRecipe>> FIRE_POT_SERIALIZER =
            SERIALIZERS.register("fire_pot", () -> FirePotRecipe.Serializer.INSTANCE);
}