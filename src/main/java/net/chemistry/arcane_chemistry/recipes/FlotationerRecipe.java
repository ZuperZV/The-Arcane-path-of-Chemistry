package net.chemistry.arcane_chemistry.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public class FlotationerRecipe implements Recipe<FluidRecipeInput> {

    public final ItemStack output;
    public final Ingredient crushedIngredient;
    public final Ingredient ingredientoven;
    public final FluidStack inputFluid;

    public FlotationerRecipe(ItemStack output, Ingredient crushedIngredient, Ingredient ingredientoven, FluidStack inputFluid) {
        this.output = output;
        this.crushedIngredient = crushedIngredient;
        this.ingredientoven = ingredientoven;
        this.inputFluid = inputFluid;
    }
    @Override
    public ItemStack assemble(FluidRecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "flotation");
    }
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }
    public ItemStack getResultEmi(){
        return output.copy();
    }

    @Override
    public boolean matches(FluidRecipeInput input, Level level) {
        boolean itemsMatch = crushedIngredient.test(input.getItem(0)) && ingredientoven.test(input.getItem(1));

        FluidStack containerFluid = input.getFluidStack();

        boolean fluidMatches = FluidStack.isSameFluid(containerFluid, inputFluid);

        return itemsMatch && fluidMatches;
    }



    @Override
    public boolean isSpecial() {
        return true;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, crushedIngredient);
        ingredients.add(1, ingredientoven);
        return ingredients;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "flotation";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<FlotationerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "flotation";
    }
    public static final class Serializer implements RecipeSerializer<FlotationerRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "flotation");

        private final MapCodec<FlotationerRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.crushedIngredient),
                    Ingredient.CODEC_NONEMPTY.fieldOf("oven_ingredient").forGetter(recipe -> recipe.ingredientoven),
                    CodecFix.FLUID_STACK_CODEC.fieldOf("fluid").forGetter(recipe -> recipe.inputFluid)
            ).apply(instance, FlotationerRecipe::new);
        });

        private static void write(RegistryFriendlyByteBuf buffer, FlotationerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.crushedIngredient);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredientoven);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.inputFluid);
        }

        private static FlotationerRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            FluidStack fluid = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new FlotationerRecipe(output, input0, input1, fluid);
        }

        @Override
        public MapCodec<FlotationerRecipe> codec() {
            return CODEC;
        }

        private final StreamCodec<RegistryFriendlyByteBuf, FlotationerRecipe> STREAM_CODEC = StreamCodec.of(
                FlotationerRecipe.Serializer::write, FlotationerRecipe.Serializer::read);

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FlotationerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}