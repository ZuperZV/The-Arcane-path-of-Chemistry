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

public class ElectrolyzerRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final ItemStack output2;
    public final Ingredient ingredient0;
    public final Optional<Ingredient> ingredient1;

    public ElectrolyzerRecipe(ItemStack output, ItemStack output2, Ingredient ingredient0, Optional<Ingredient> ingredient1) {
        this.output = output;
        this.output2 = output2;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "electrolyzer");
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

        return ingredients;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "electrolyzer";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<ElectrolyzerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "electrolyzer";
    }
    public static final class Serializer implements RecipeSerializer<ElectrolyzerRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "electrolyzer");

        private final MapCodec<ElectrolyzerRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                        return recipe.output;
                    }),
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output2").forGetter((recipe) -> {
                        return recipe.output2;
                    }),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> {
                        return recipe.ingredient0;
                    }),
                    Ingredient.CODEC.optionalFieldOf("ingredient1").forGetter((recipe) -> {
                        return recipe.ingredient1;
                    })
            ).apply(instance, ElectrolyzerRecipe::new);
        });


        private final StreamCodec<RegistryFriendlyByteBuf, ElectrolyzerRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public MapCodec<ElectrolyzerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ElectrolyzerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ElectrolyzerRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> input1 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output2 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new ElectrolyzerRecipe(output, output2, input0, input1);
        }


        private static void write(RegistryFriendlyByteBuf buffer, ElectrolyzerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            if (recipe.ingredient1.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1.get());
            } else {buffer.writeBoolean(false);}
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output2);
        }
    }
}