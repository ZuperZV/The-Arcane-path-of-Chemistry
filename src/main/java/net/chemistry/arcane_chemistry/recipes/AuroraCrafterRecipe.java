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

public class AuroraCrafterRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final Optional<Ingredient> ingredient0;
    public final Optional<Ingredient> ingredient1;
    public final Optional<Ingredient> ingredient2;
    public final Optional<Ingredient> ingredient3;
    public final Optional<Ingredient> ingredient4;
    public final Optional<Ingredient> ingredient5;
    public final Optional<Ingredient> ingredient6;
    public final Optional<Ingredient> ingredient7;
    public final Optional<Ingredient> ingredient8;

    public AuroraCrafterRecipe(ItemStack output,
                               Optional<Ingredient> ingredient0,
                               Optional<Ingredient> ingredient1,
                               Optional<Ingredient> ingredient2,
                               Optional<Ingredient> ingredient3,
                               Optional<Ingredient> ingredient4,
                               Optional<Ingredient> ingredient5,
                               Optional<Ingredient> ingredient6,
                               Optional<Ingredient> ingredient7,
                               Optional<Ingredient> ingredient8) {
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
        this.ingredient5 = ingredient5;
        this.ingredient6 = ingredient6;
        this.ingredient7 = ingredient7;
        this.ingredient8 = ingredient8;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "aurora_crafter");
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

        if (ingredient0.isPresent() && !ingredient0.get().test(pContainer.getItem(0))) {
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
        if (ingredient5.isPresent() && !ingredient5.get().test(pContainer.getItem(4))) {
            return false;
        }
        if (ingredient6.isPresent() && !ingredient6.get().test(pContainer.getItem(4))) {
            return false;
        }
        if (ingredient7.isPresent() && !ingredient7.get().test(pContainer.getItem(4))) {
            return false;
        }
        if (ingredient8.isPresent() && !ingredient8.get().test(pContainer.getItem(4))) {
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

        ingredient0.ifPresent(ingredients::add);
        ingredient1.ifPresent(ingredients::add);
        ingredient2.ifPresent(ingredients::add);
        ingredient3.ifPresent(ingredients::add);
        ingredient4.ifPresent(ingredients::add);
        ingredient5.ifPresent(ingredients::add);
        ingredient6.ifPresent(ingredients::add);
        ingredient7.ifPresent(ingredients::add);
        ingredient8.ifPresent(ingredients::add);

        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "aurora_crafter";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AuroraCrafterRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return AuroraCrafterRecipe.Type.INSTANCE;
    }

    public static final class Type implements RecipeType<AuroraCrafterRecipe> {
        private Type() { }
        public static final AuroraCrafterRecipe.Type INSTANCE = new AuroraCrafterRecipe.Type();
        public static final String ID = "aurora_crafter";
    }
    public static final class Serializer implements RecipeSerializer<AuroraCrafterRecipe> {
        private Serializer() {}
        public static final AuroraCrafterRecipe.Serializer INSTANCE = new AuroraCrafterRecipe.Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "aurora_crafter");

        private final MapCodec<AuroraCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                    Ingredient.CODEC.optionalFieldOf("ingredient").forGetter((recipe) -> recipe.ingredient0),
                    Ingredient.CODEC.optionalFieldOf("ingredient1").forGetter((recipe) -> recipe.ingredient1),
                    Ingredient.CODEC.optionalFieldOf("ingredient2").forGetter((recipe) -> recipe.ingredient2),
                    Ingredient.CODEC.optionalFieldOf("ingredient3").forGetter((recipe) -> recipe.ingredient3),
                    Ingredient.CODEC.optionalFieldOf("ingredient4").forGetter((recipe) -> recipe.ingredient4),
                    Ingredient.CODEC.optionalFieldOf("ingredient5").forGetter((recipe) -> recipe.ingredient5),
                    Ingredient.CODEC.optionalFieldOf("ingredient6").forGetter((recipe) -> recipe.ingredient6),
                    Ingredient.CODEC.optionalFieldOf("ingredient7").forGetter((recipe) -> recipe.ingredient7),
                    Ingredient.CODEC.optionalFieldOf("ingredient8").forGetter((recipe) -> recipe.ingredient8)
            ).apply(instance, AuroraCrafterRecipe::new);
        });


        private final StreamCodec<RegistryFriendlyByteBuf, AuroraCrafterRecipe> STREAM_CODEC = StreamCodec.of(
                AuroraCrafterRecipe.Serializer::write, AuroraCrafterRecipe.Serializer::read);

        @Override
        public MapCodec<AuroraCrafterRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AuroraCrafterRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AuroraCrafterRecipe read(RegistryFriendlyByteBuf buffer) {
            Optional<Ingredient> input0 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input1 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input2 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input3 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input4 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input5 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input6 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input7 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> input8 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new AuroraCrafterRecipe(output, input0, input1, input2, input3, input4, input5, input6, input7, input8);
        }


        private static void write(RegistryFriendlyByteBuf buffer, AuroraCrafterRecipe recipe) {
            if (recipe.ingredient0.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0.get());
            } else {
                buffer.writeBoolean(false);
            }
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
            if (recipe.ingredient5.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient5.get());
            } else {
                buffer.writeBoolean(false);
            }
            if (recipe.ingredient6.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient6.get());
            } else {
                buffer.writeBoolean(false);
            }
            if (recipe.ingredient7.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient7.get());
            } else {
                buffer.writeBoolean(false);
            }
            if (recipe.ingredient8.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient8.get());
            } else {
                buffer.writeBoolean(false);
            }
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}