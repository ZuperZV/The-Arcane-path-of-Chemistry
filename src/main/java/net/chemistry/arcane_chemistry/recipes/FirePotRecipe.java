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

import java.util.Optional;

public class FirePotRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final ItemStack output2;
    public final Ingredient ingredient0;
    public final Optional<Ingredient> ingredient1;
    public final Optional<Ingredient> ingredient2;
    public final Optional<Ingredient> ingredient3;
    public final Optional<Ingredient> ingredient4;

    public FirePotRecipe(ItemStack output, ItemStack output2, Ingredient ingredient0,
                         Optional<Ingredient> ingredient1,
                         Optional<Ingredient> ingredient2,
                         Optional<Ingredient> ingredient3,
                         Optional<Ingredient> ingredient4) {
        this.output = output;
        this.output2 = output2;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "fire_pot");
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
        if (ingredient4.isPresent() && !ingredient4.get().test(pContainer.getItem(4))) {
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
        ingredient4.ifPresent(ingredients::add);

        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "fire_pot";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FirePotRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return FirePotRecipe.Type.INSTANCE;
    }

    public static final class Type implements RecipeType<FirePotRecipe> {
        private Type() { }
        public static final FirePotRecipe.Type INSTANCE = new FirePotRecipe.Type();
        public static final String ID = "fire_pot";
    }
    public static final class Serializer implements RecipeSerializer<FirePotRecipe> {
        private Serializer() {}
        public static final FirePotRecipe.Serializer INSTANCE = new FirePotRecipe.Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "fire_pot");

        private final MapCodec<FirePotRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output2").forGetter((recipe) -> recipe.output2),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient0),
                    Ingredient.CODEC.optionalFieldOf("ingredient1").forGetter((recipe) -> recipe.ingredient1),
                    Ingredient.CODEC.optionalFieldOf("ingredient2").forGetter((recipe) -> recipe.ingredient2),
                    Ingredient.CODEC.optionalFieldOf("ingredient3").forGetter((recipe) -> recipe.ingredient3),
                    Ingredient.CODEC.optionalFieldOf("ingredient4").forGetter((recipe) -> recipe.ingredient4)
            ).apply(instance, FirePotRecipe::new);
        });


        private final StreamCodec<RegistryFriendlyByteBuf, FirePotRecipe> STREAM_CODEC = StreamCodec.of(
                FirePotRecipe.Serializer::write, FirePotRecipe.Serializer::read);

        @Override
        public MapCodec<FirePotRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FirePotRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FirePotRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> input1 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input2 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input3 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input4 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output2 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new FirePotRecipe(output, output2, input0, input1, input2, input3, input4);
        }


        private static void write(RegistryFriendlyByteBuf buffer, FirePotRecipe recipe) {
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
            if (recipe.ingredient4.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient4.get());
            } else {
                buffer.writeBoolean(false);
            }
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output2);
        }
    }
}