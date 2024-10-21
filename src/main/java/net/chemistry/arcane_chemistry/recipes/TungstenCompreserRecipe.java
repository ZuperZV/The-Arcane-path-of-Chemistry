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

import java.util.Optional;

public class TungstenCompreserRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final ItemStack output2;
    public final Ingredient ingredient0;
    public final Optional<Ingredient> ingredient1;
    public final Optional<Ingredient> ingredient2;
    public final Optional<Ingredient> ingredient3;

    public TungstenCompreserRecipe(ItemStack output, ItemStack output2, Ingredient ingredient0,
                         Optional<Ingredient> ingredient1,
                         Optional<Ingredient> ingredient2,
                         Optional<Ingredient> ingredient3) {
        this.output = output;
        this.output2 = output2;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "tungsten_compreser");
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
        if (ingredient1.isPresent() && !ingredient1.get().test(pContainer.getItem(1))) {
            return false;
        }
        if (ingredient2.isPresent() && !ingredient2.get().test(pContainer.getItem(2))) {
            return false;
        }
        if (ingredient3.isPresent() && !ingredient3.get().test(pContainer.getItem(3))) {
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
        ingredient1.ifPresent(ingredients::add);
        ingredient2.ifPresent(ingredients::add);
        ingredient3.ifPresent(ingredients::add);

        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "tungsten_compreser";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TungstenCompreserRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return TungstenCompreserRecipe.Type.INSTANCE;
    }

    public static final class Type implements RecipeType<TungstenCompreserRecipe> {
        private Type() { }
        public static final TungstenCompreserRecipe.Type INSTANCE = new TungstenCompreserRecipe.Type();
        public static final String ID = "tungsten_compreser";
    }
    public static final class Serializer implements RecipeSerializer<TungstenCompreserRecipe> {
        private Serializer() {}
        public static final TungstenCompreserRecipe.Serializer INSTANCE = new TungstenCompreserRecipe.Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "tungsten_compreser");

        private final MapCodec<TungstenCompreserRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output2").forGetter((recipe) -> recipe.output2),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient0),
                    Ingredient.CODEC.optionalFieldOf("ingredient1").forGetter((recipe) -> recipe.ingredient1),
                    Ingredient.CODEC.optionalFieldOf("ingredient2").forGetter((recipe) -> recipe.ingredient2),
                    Ingredient.CODEC.optionalFieldOf("ingredient3").forGetter((recipe) -> recipe.ingredient3)
            ).apply(instance, TungstenCompreserRecipe::new);
        });


        private final StreamCodec<RegistryFriendlyByteBuf, TungstenCompreserRecipe> STREAM_CODEC = StreamCodec.of(
                TungstenCompreserRecipe.Serializer::write, TungstenCompreserRecipe.Serializer::read);

        @Override
        public MapCodec<TungstenCompreserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TungstenCompreserRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static TungstenCompreserRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> input1 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input2 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input3 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output2 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new TungstenCompreserRecipe(output, output2, input0, input1, input2, input3);
        }


        private static void write(RegistryFriendlyByteBuf buffer, TungstenCompreserRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            if (recipe.ingredient1.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1.get());
            } else {
                buffer.writeBoolean(false);
            }
            if (recipe.ingredient2.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient2.get());
            } else {
                buffer.writeBoolean(false);
            }
            if (recipe.ingredient3.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient3.get());
            } else {
                buffer.writeBoolean(false);
            }

            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output2);
        }
    }
}