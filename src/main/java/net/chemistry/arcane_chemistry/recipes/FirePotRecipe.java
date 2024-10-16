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

public class FirePotRecipe implements Recipe<RecipeInput> {

    public final Ingredient ingredient0;
    private final FluidStack fluidOutput;
    private final FluidStack fluidInput;

    public FirePotRecipe(Ingredient ingredient0, FluidStack fluidInput, FluidStack fluidOutput) {
        this.ingredient0 = ingredient0;
        this.fluidOutput = fluidOutput;
        this.fluidInput = fluidInput;
    }
    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return null;
    }

    public FluidStack getFluidOutput() {
        return fluidOutput;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "fire_pot");
    }


    public FluidStack getResultEmi(){
        return fluidOutput.copy();
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        return ingredient0.test(pContainer.getItem(0));
    }
    @Override
    public boolean isSpecial() {
        return true;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, ingredient0);
        return ingredients;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_336125_) {
        return null;
    }

    @Override
    public String getGroup() {
        return "fire_pot";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<FirePotRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fire_pot";
    }
    public static final class Serializer implements RecipeSerializer<FirePotRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "fire_pot");

        private final MapCodec<FirePotRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient0),
                    FluidStack.CODEC.fieldOf("fluid_input").forGetter(recipe -> recipe.fluidInput),
                    FluidStack.CODEC.fieldOf("fluid_output").forGetter(recipe -> recipe.fluidOutput)
            ).apply(instance, FirePotRecipe::new);
        });


        private final StreamCodec<RegistryFriendlyByteBuf, FirePotRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public MapCodec<FirePotRecipe> codec() {
            return (MapCodec<FirePotRecipe>) CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FirePotRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FirePotRecipe read(RegistryFriendlyByteBuf  buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            FluidStack fluidInput = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            FluidStack fluidOutput = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new FirePotRecipe(input0, fluidInput, fluidOutput);
        }

        private static void write(RegistryFriendlyByteBuf  buffer, FirePotRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.fluidInput);
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.fluidOutput);
        }
    }
}