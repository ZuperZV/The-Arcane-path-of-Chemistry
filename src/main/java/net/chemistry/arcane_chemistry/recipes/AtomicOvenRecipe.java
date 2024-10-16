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

public class AtomicOvenRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Ingredient ingredientoven;

    public AtomicOvenRecipe(ItemStack output, Ingredient ingredient0, Ingredient ingredientoven) {
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredientoven = ingredientoven;
    }
    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "atomic_oven");
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
        if(pLevel.isClientSide()) {
            return false;
        }
        return ingredient0.test(pContainer.getItem(0)) && ingredientoven.test(pContainer.getItem(1));
    }
    @Override
    public boolean isSpecial() {
        return true;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredientoven);
        return ingredients;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "atomic_oven";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<AtomicOvenRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "atomic_oven";
    }
    public static final class Serializer implements RecipeSerializer<AtomicOvenRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "atomic_oven");

        private final MapCodec<AtomicOvenRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                return recipe.output;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> {
                return recipe.ingredient0;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("oven_ingredient").forGetter((recipe) -> {
                return recipe.ingredientoven;
            })).apply(instance, AtomicOvenRecipe::new);
        });

        private final StreamCodec<RegistryFriendlyByteBuf, AtomicOvenRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public MapCodec<AtomicOvenRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AtomicOvenRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AtomicOvenRecipe read(RegistryFriendlyByteBuf  buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new AtomicOvenRecipe(output, input0 , input1);
        }

        private static void write(RegistryFriendlyByteBuf  buffer, AtomicOvenRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredientoven);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}