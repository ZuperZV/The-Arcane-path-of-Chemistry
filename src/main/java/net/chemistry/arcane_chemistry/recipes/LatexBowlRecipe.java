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

public class LatexBowlRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final Ingredient ingredient0;

    public LatexBowlRecipe(ItemStack output, Ingredient ingredient0) {
        this.output = output;
        this.ingredient0 = ingredient0;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "latex_bowl");
    }
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }
    public ItemStack getResultEmi(){
        return output.copy();
    }
    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        if (!ingredient0.test(pContainer.getItem(0))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        ingredients.add(ingredient0);

        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "latex_bowl";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LatexBowlRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return LatexBowlRecipe.Type.INSTANCE;
    }

    public static final class Type implements RecipeType<LatexBowlRecipe> {
        private Type() { }
        public static final LatexBowlRecipe.Type INSTANCE = new LatexBowlRecipe.Type();
        public static final String ID = "latex_bowl";
    }
    public static final class Serializer implements RecipeSerializer<LatexBowlRecipe> {
        private Serializer() {}
        public static final LatexBowlRecipe.Serializer INSTANCE = new LatexBowlRecipe.Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "latex_bowl");

        private final MapCodec<LatexBowlRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient0)
            ).apply(instance, LatexBowlRecipe::new);
        });


        private final StreamCodec<RegistryFriendlyByteBuf, LatexBowlRecipe> STREAM_CODEC = StreamCodec.of(
                LatexBowlRecipe.Serializer::write, LatexBowlRecipe.Serializer::read);

        @Override
        public MapCodec<LatexBowlRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LatexBowlRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static LatexBowlRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new LatexBowlRecipe(output, input0);
        }


        private static void write(RegistryFriendlyByteBuf buffer, LatexBowlRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);

            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}