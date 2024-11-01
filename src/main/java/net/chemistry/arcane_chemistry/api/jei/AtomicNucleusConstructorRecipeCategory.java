package net.chemistry.arcane_chemistry.api.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.recipes.AtomicNucleusConstructorRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AtomicNucleusConstructorRecipeCategory implements IRecipeCategory<AtomicNucleusConstructorRecipe> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "atomic_nucleus_constructor");
    public static final RecipeType<AtomicNucleusConstructorRecipe> RECIPE_TYPE = RecipeType.create(Arcane_chemistry.MOD_ID, "atomic_nucleus_constructor", AtomicNucleusConstructorRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableStatic nucleusSlot;
    private final IDrawableStatic slot_1;
    private final IDrawableStatic slot_2;
    private final IDrawableStatic slot_3;
    private final IDrawableStatic slot_4;
    private final IDrawableStatic slot_5;
    private final IDrawableStatic slot_6;
    private final IDrawableStatic slot_7;
    private final IDrawableStatic slot_8;
    private final IDrawableStatic slot_9;
    private final IDrawableStatic slot_10;

    private final IDrawableStatic Output;
    private final IDrawableStatic Output2;

    private final IDrawableStatic arrowbacki;
    private final IDrawableAnimated progressArrow;

    public AtomicNucleusConstructorRecipeCategory(IGuiHelper helper) {
        ResourceLocation ARROWBACK = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/null_arrow.png");
        ResourceLocation ARROW = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/arrow.png");
        ResourceLocation SLOT = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/slot.png");

        this.background = helper.createBlankDrawable(160, 90);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ATOMIC_NUCLEUS_CONSTRUCTOR.get()));

        IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 0, 23, 15).setTextureSize(23, 15).addPadding(12, 0, 122, 0).build();
        this.progressArrow = helper.createAnimatedDrawable(progressDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 0, 23, 15).setTextureSize(23, 15).addPadding(12, 0, 121, 0).build();

        this.nucleusSlot = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 52, 0).build();
        this.slot_1 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 78, 0).build();
        this.slot_2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 24, 0).build();
        this.slot_3 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(9, 0, 52, 0).build();
        this.slot_4 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(17, 0, 87, 0).build();
        this.slot_5 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 102, 0).build();
        this.slot_6 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(55, 0, 87, 0).build();
        this.slot_7 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(63, 0, 52, 0).build();
        this.slot_8 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(55, 0, 15, 0).build();
        this.slot_9 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 1, 0).build();
        this.slot_10 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(17, 0, 15, 0).build();

        this.Output = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 121, 0).build();
        this.Output2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(36, 0, 140, 0).build();
    }


    @Override
    public RecipeType<AtomicNucleusConstructorRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.arcane_chemistry.atomic_nucleus_constructor");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(AtomicNucleusConstructorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.nucleusSlot.draw(guiGraphics);
        this.slot_1.draw(guiGraphics);
        this.slot_2.draw(guiGraphics);
        this.slot_3.draw(guiGraphics);
        this.slot_4.draw(guiGraphics);
        this.slot_5.draw(guiGraphics);
        this.slot_6.draw(guiGraphics);
        this.slot_7.draw(guiGraphics);
        this.slot_8.draw(guiGraphics);
        this.slot_9.draw(guiGraphics);
        this.slot_10.draw(guiGraphics);

        this.Output.draw(guiGraphics);
        this.Output2.draw(guiGraphics);

        this.arrowbacki.draw(guiGraphics);
        this.progressArrow.draw(guiGraphics);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, AtomicNucleusConstructorRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 53, 37).addIngredients(recipe.nucleusCell);

        if (recipe.ingredient1.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 79, 37).addIngredients(recipe.ingredient1.get());
        if (recipe.ingredient2.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 25, 37).addIngredients(recipe.ingredient2.get());
        if (recipe.ingredient3.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 53, 10).addIngredients(recipe.ingredient3.get());
        if (recipe.ingredient4.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 88, 18).addIngredients(recipe.ingredient4.get());
        if (recipe.ingredient5.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 103, 37).addIngredients(recipe.ingredient5.get());
        if (recipe.ingredient6.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 88, 56).addIngredients(recipe.ingredient6.get());
        if (recipe.ingredient7.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 53, 64).addIngredients(recipe.ingredient7.get());
        if (recipe.ingredient8.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 16, 56).addIngredients(recipe.ingredient8.get());
        if (recipe.ingredient9.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 2, 37).addIngredients(recipe.ingredient9.get());
        if (recipe.ingredient10.isPresent()) builder.addSlot(RecipeIngredientRole.INPUT, 16, 18).addIngredients(recipe.ingredient10.get());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 122, 37).addItemStack(recipe.output);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 141, 37).addItemStack(recipe.output2);

    }
}