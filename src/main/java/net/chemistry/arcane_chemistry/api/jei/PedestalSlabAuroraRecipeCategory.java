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
import net.chemistry.arcane_chemistry.recipes.PedestalSlabAuroraRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PedestalSlabAuroraRecipeCategory implements IRecipeCategory<PedestalSlabAuroraRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "pedestal_slab_aurora");
    public static final RecipeType<PedestalSlabAuroraRecipe> RECIPE_TYPE = RecipeType.create(Arcane_chemistry.MOD_ID, "pedestal_slab_aurora", PedestalSlabAuroraRecipe.class);
    public final static ResourceLocation SLOT = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/slot.png");
    public final static ResourceLocation ARROWBACK = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/null_magi_slab.png");
    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableStatic slot_1;
    private final IDrawableStatic slot_2;

    private final IDrawableStatic arrowbacki;
    private final IDrawableAnimated progress;

    public PedestalSlabAuroraRecipeCategory(IGuiHelper helper) {
        ResourceLocation ARROW = ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/magi_slab.png");

        this.background = helper.createBlankDrawable(100, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PEDESTAL_SLAB.get()));

        IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 0, 100, 60).setTextureSize(100, 60).addPadding(0, 0, 0, 0).build();

        this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 0, 100, 60).setTextureSize(100, 60).addPadding(0, 0, 0, 0).build();

        this.slot_1 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(8,0,41,0).build();
        this.slot_2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(32,0,41,0).build();

        this.progress = helper.createAnimatedDrawable(progressDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM,
                false);
    }

    @Override
    public RecipeType<PedestalSlabAuroraRecipe> getRecipeType() {
        return JEIPlugin.PEDESTAL_SLAB_AURORA_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Aurora Pedestal 5x5 Aurora");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(PedestalSlabAuroraRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX,
                     double mouseY) {

        this.slot_1.draw(guiGraphics);
        this.slot_2.draw(guiGraphics);

        this.arrowbacki.draw(guiGraphics);
        this.progress.draw(guiGraphics);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, PedestalSlabAuroraRecipe recipe, @NotNull IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 42, 33)
                .addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 42, 9).addItemStack(recipe.output);
    }
}