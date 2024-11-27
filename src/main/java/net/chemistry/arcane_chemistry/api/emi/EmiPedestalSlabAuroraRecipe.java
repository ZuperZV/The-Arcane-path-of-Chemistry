package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.recipes.PedestalSlabAuroraRecipe;
import net.chemistry.arcane_chemistry.recipes.PedestalSlabClayRecipe;
import net.minecraft.resources.ResourceLocation;

public class EmiPedestalSlabAuroraRecipe extends BasicEmiRecipe {
    public static final EmiTexture NULL_MAGI_SLAB = new EmiTexture(ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/null_magi_slab.png"), 0, 0, 100, 60, 100, 60, 100, 60);
    public static final EmiTexture MAGI_SLAB = new EmiTexture(ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/magi_slab.png"), 0, 0, 100, 60, 100, 60, 100, 60);

    public EmiPedestalSlabAuroraRecipe(PedestalSlabAuroraRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.PEDESTAL_SLAB_AURORA_CATEGORY, recipe.getId(), 100, 60);

        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));

        this.outputs.add(EmiStack.of(recipe.output));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 42, 33).drawBack(true);

        widgets.addSlot(outputs.get(0), 42, 9).recipeContext(this).drawBack(true);

        widgets.addTexture(NULL_MAGI_SLAB, 0, 0);
        widgets.addAnimatedTexture(MAGI_SLAB, 0, 0, 20000, false, true, false);
    }
}
