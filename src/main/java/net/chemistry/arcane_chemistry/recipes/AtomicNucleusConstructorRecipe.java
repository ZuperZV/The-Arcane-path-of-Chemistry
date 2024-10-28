package net.chemistry.arcane_chemistry.recipes;

import com.mojang.serialization.Codec;
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

public class AtomicNucleusConstructorRecipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final ItemStack output2;
    public final Ingredient nucleusCell;
    public final Optional<Ingredient> ingredient1;
    public final Optional<Ingredient> ingredient2;
    public final Optional<Ingredient> ingredient3;
    public final Optional<Ingredient> ingredient4;
    public final Optional<Ingredient> ingredient5;
    public final Optional<Ingredient> ingredient6;
    public final Optional<Ingredient> ingredient7;
    public final Optional<Ingredient> ingredient8;
    public final Optional<Ingredient> ingredient9;
    public final Optional<Ingredient> ingredient10;

    public AtomicNucleusConstructorRecipe(ItemStack output, ItemStack output2, Ingredient nucleusCell,
                                          Optional<Ingredient> ingredient1,
                                          Optional<Ingredient> ingredient2,
                                          Optional<Ingredient> ingredient3,
                                          Optional<Ingredient> ingredient4,
                                          Optional<Ingredient> ingredient5,
                                          Optional<Ingredient> ingredient6,
                                          Optional<Ingredient> ingredient7,
                                          Optional<Ingredient> ingredient8,
                                          Optional<Ingredient> ingredient9,
                                          Optional<Ingredient> ingredient10) {
        this.output = output;
        this.output2 = output2;
        this.nucleusCell = nucleusCell;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
        this.ingredient5 = ingredient5;
        this.ingredient6 = ingredient6;
        this.ingredient7 = ingredient7;
        this.ingredient8 = ingredient8;
        this.ingredient9 = ingredient9;
        this.ingredient10 = ingredient10;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "atomic_nucleus_constructor");
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    public ItemStack getResultEmi() {
        return output.copy();
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        if (!nucleusCell.test(pContainer.getItem(0))) {return false;}
        if (ingredient1.isPresent() && !ingredient1.get().test(pContainer.getItem(1))) {return false;}
        if (ingredient2.isPresent() && !ingredient2.get().test(pContainer.getItem(2))) {return false;}
        if (ingredient3.isPresent() && !ingredient3.get().test(pContainer.getItem(3))) {return false;}
        if (ingredient4.isPresent() && !ingredient4.get().test(pContainer.getItem(4))) {return false;}
        if (ingredient5.isPresent() && !ingredient5.get().test(pContainer.getItem(5))) {return false;}
        if (ingredient6.isPresent() && !ingredient6.get().test(pContainer.getItem(6))) {return false;}
        if (ingredient7.isPresent() && !ingredient7.get().test(pContainer.getItem(7))) {return false;}
        if (ingredient8.isPresent() && !ingredient8.get().test(pContainer.getItem(8))) {return false;}
        if (ingredient9.isPresent() && !ingredient9.get().test(pContainer.getItem(9))) {return false;}
        if (ingredient10.isPresent() && !ingredient10.get().test(pContainer.getItem(10))) {return false;}

        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        ingredients.add(nucleusCell);
        ingredient1.ifPresent(ingredients::add);
        ingredient2.ifPresent(ingredients::add);
        ingredient3.ifPresent(ingredients::add);
        ingredient4.ifPresent(ingredients::add);
        ingredient5.ifPresent(ingredients::add);
        ingredient6.ifPresent(ingredients::add);
        ingredient7.ifPresent(ingredients::add);
        ingredient8.ifPresent(ingredients::add);
        ingredient9.ifPresent(ingredients::add);
        ingredient10.ifPresent(ingredients::add);

        return ingredients;
    }

    public int getCountForIngredient(int index) {
        switch (index) {
            case 0: return nucleusCell.getItems().length;
            case 1: return ingredient1.map(i -> i.getItems().length).orElse(0);
            case 2: return ingredient2.map(i -> i.getItems().length).orElse(0);
            case 3: return ingredient3.map(i -> i.getItems().length).orElse(0);
            case 4: return ingredient4.map(i -> i.getItems().length).orElse(0);
            case 5: return ingredient5.map(i -> i.getItems().length).orElse(0);
            case 6: return ingredient6.map(i -> i.getItems().length).orElse(0);
            case 7: return ingredient7.map(i -> i.getItems().length).orElse(0);
            case 8: return ingredient8.map(i -> i.getItems().length).orElse(0);
            case 9: return ingredient9.map(i -> i.getItems().length).orElse(0);
            case 10: return ingredient10.map(i -> i.getItems().length).orElse(0);
            default: throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public String getGroup() {
        return "atomic_nucleus_constructor";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AtomicNucleusConstructorRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return AtomicNucleusConstructorRecipe.Type.INSTANCE;
    }

    public static final class Type implements RecipeType<AtomicNucleusConstructorRecipe> {
        private Type() { }
        public static final AtomicNucleusConstructorRecipe.Type INSTANCE = new AtomicNucleusConstructorRecipe.Type();
        public static final String ID = "atomic_nucleus_constructor";
    }

    public static final class Serializer implements RecipeSerializer<AtomicNucleusConstructorRecipe> {
        private Serializer() {}
        public static final AtomicNucleusConstructorRecipe.Serializer INSTANCE = new AtomicNucleusConstructorRecipe.Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "atomic_nucleus_constructor");

        private final MapCodec<AtomicNucleusConstructorRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                    CodecFix.OPTIONAL_ITEM_STACK_CODEC.fieldOf("output2").forGetter((recipe) -> recipe.output2),
                    Ingredient.CODEC_NONEMPTY.fieldOf("nucleus_cell").forGetter((recipe) -> recipe.nucleusCell),
                    Ingredient.CODEC.optionalFieldOf("ingredient1").forGetter((recipe) -> recipe.ingredient1),
                    Ingredient.CODEC.optionalFieldOf("ingredient2").forGetter((recipe) -> recipe.ingredient2),
                    Ingredient.CODEC.optionalFieldOf("ingredient3").forGetter((recipe) -> recipe.ingredient3),
                    Ingredient.CODEC.optionalFieldOf("ingredient4").forGetter((recipe) -> recipe.ingredient4),
                    Ingredient.CODEC.optionalFieldOf("ingredient5").forGetter((recipe) -> recipe.ingredient5),
                    Ingredient.CODEC.optionalFieldOf("ingredient6").forGetter((recipe) -> recipe.ingredient6),
                    Ingredient.CODEC.optionalFieldOf("ingredient7").forGetter((recipe) -> recipe.ingredient7),
                    Ingredient.CODEC.optionalFieldOf("ingredient8").forGetter((recipe) -> recipe.ingredient8),
                    Ingredient.CODEC.optionalFieldOf("ingredient9").forGetter((recipe) -> recipe.ingredient9),
                    Ingredient.CODEC.optionalFieldOf("ingredient10").forGetter((recipe) -> recipe.ingredient10)
            ).apply(instance, AtomicNucleusConstructorRecipe::new);
        });

        private final StreamCodec<RegistryFriendlyByteBuf, AtomicNucleusConstructorRecipe> STREAM_CODEC = StreamCodec.of(
                AtomicNucleusConstructorRecipe.Serializer::write, AtomicNucleusConstructorRecipe.Serializer::read);

        @Override
        public MapCodec<AtomicNucleusConstructorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AtomicNucleusConstructorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AtomicNucleusConstructorRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient nucleusCell = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> ingredient1 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient2 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient3 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient4 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient5 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient6 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient7 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient8 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient9 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            Optional<Ingredient> ingredient10 = buffer.readBoolean() ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)) : Optional.empty();
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output2 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new AtomicNucleusConstructorRecipe(output, output2, nucleusCell, ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9, ingredient10);
        }

        private static void write(RegistryFriendlyByteBuf buffer, AtomicNucleusConstructorRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.nucleusCell);
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
            if (recipe.ingredient9.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient9.get());
            } else {
                buffer.writeBoolean(false);
            }
            if (recipe.ingredient10.isPresent()) {
                buffer.writeBoolean(true);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient10.get());
            } else {
                buffer.writeBoolean(false);
            }

            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output2);
        }
    }
}