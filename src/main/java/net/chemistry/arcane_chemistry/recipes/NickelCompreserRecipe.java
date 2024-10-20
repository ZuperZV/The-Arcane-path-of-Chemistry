package net.chemistry.arcane_chemistry.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class NickelCompreserRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Optional<Ingredient> ingredient1;
    public final Optional<Ingredient> ingredient2;

    public NickelCompreserRecipe(ItemStack output, Ingredient ingredient0, Optional<Ingredient> ingredient1, Optional<Ingredient> ingredient2) {
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "nickel_compreser");
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
        if(pLevel.isClientSide()) {return false;}

        if (!ingredient0.test(pContainer.getItem(0))) {return false;}
        if (ingredient1.isPresent() && !ingredient1.get().test(pContainer.getItem(1))) {return false;}
        if (ingredient2.isPresent() && !ingredient2.get().test(pContainer.getItem(2))) {return false;}
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

        return ingredients;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "nickel_compreser";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<NickelCompreserRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "nickel_compreser";
    }
    public static final class Serializer implements RecipeSerializer<NickelCompreserRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "nickel_compreser");

        private final MapCodec<NickelCompreserRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                return recipe.output;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> {
                return recipe.ingredient0;
            }), Ingredient.CODEC.optionalFieldOf("ingredient1").forGetter((recipe) -> {
                return recipe.ingredient1;
            }), Ingredient.CODEC.optionalFieldOf("ingredient2").forGetter((recipe) -> {
                return recipe.ingredient2;
            })).apply(instance, NickelCompreserRecipe::new);
        });

        private final StreamCodec<RegistryFriendlyByteBuf, NickelCompreserRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public MapCodec<NickelCompreserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, NickelCompreserRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static NickelCompreserRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> input1 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input2 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new NickelCompreserRecipe(output, input0, input1, input2);
        }


        private static void write(RegistryFriendlyByteBuf buffer, NickelCompreserRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);

            if (recipe.ingredient1.isPresent()) {buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1.get());} else {buffer.writeBoolean(false);}
            if (recipe.ingredient2.isPresent()) {buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient2.get());
            } else {
                buffer.writeBoolean(false);
            }
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }

    }
}