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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public class ChamberRecipe implements Recipe<FluidRecipeInput> {

    public final ItemStack output;
    public final FluidStack inputFluid;
    public final FluidStack gasFluidOutput;

    public ChamberRecipe(ItemStack output,
                         FluidStack inputFluid, FluidStack gasFluidOutput) {
        this.output = output;
        this.inputFluid = inputFluid;
        this.gasFluidOutput = gasFluidOutput;
    }

    public String getFluidName(FluidStack fluidStack) {
        return fluidStack.getFluid().getFluidType().getDescription().toString();
    }

    public String getInputFluidName() {
        return getFluidName(inputFluid);
    }

    public String getGasFluidOutputName() {
        return getFluidName(gasFluidOutput);
    }

    @Override
    public ItemStack assemble(FluidRecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    public FluidStack getGasFluidOutput() {
        return gasFluidOutput.copy();
    }

    @Override
    public boolean matches(FluidRecipeInput input, Level level) {
        FluidStack containerFluid = input.getFluidType();
        return FluidStack.isSameFluid(containerFluid, inputFluid) &&
                containerFluid.getAmount() >= inputFluid.getAmount();
    }


    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public String getGroup() {
        return "chamber";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<ChamberRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "chamber";
    }

    public static final class Serializer implements RecipeSerializer<ChamberRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "chamber");

        private final MapCodec<ChamberRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    CodecFix.FLUID_STACK_CODEC.fieldOf("fluid").forGetter(recipe -> recipe.inputFluid),
                    CodecFix.FLUID_STACK_CODEC.fieldOf("gas_fluid_output").forGetter(recipe -> recipe.gasFluidOutput)
            ).apply(instance, ChamberRecipe::new);
        });

        private static void write(RegistryFriendlyByteBuf buffer, ChamberRecipe recipe) {
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.inputFluid);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.gasFluidOutput);
        }

        private static ChamberRecipe read(RegistryFriendlyByteBuf buffer) {
            FluidStack fluid = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            FluidStack gasFluid = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new ChamberRecipe(output, fluid, gasFluid);
        }

        @Override
        public MapCodec<ChamberRecipe> codec() {
            return CODEC;
        }

        private final StreamCodec<RegistryFriendlyByteBuf, ChamberRecipe> STREAM_CODEC = StreamCodec.of(
                ChamberRecipe.Serializer::write, ChamberRecipe.Serializer::read);

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}