package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.recipes.FlotationerRecipe;
import net.chemistry.arcane_chemistry.screen.renderer.FluidTankRenderer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.client.gui.GuiGraphics;

public class EmiFlotationerRecipe extends BasicEmiRecipe {

    private final FluidStack inputFluid;

    public EmiFlotationerRecipe(FlotationerRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.FLOTATIONER_CATEGORY, recipe.getId(), 100, 58);

        this.inputFluid = recipe.inputFluid;

        this.inputs.add(EmiIngredient.of(recipe.crushedIngredient));
        this.inputs.add(EmiIngredient.of(recipe.reagentingredient));
        this.outputs.add(EmiStack.of(recipe.output));
        this.outputs.add(EmiStack.of(recipe.output2));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 7, 9).drawBack(true);
        widgets.addSlot(inputs.get(1), 7, 35).drawBack(true);
        widgets.addSlot(outputs.get(0), 81, 21).recipeContext(this).drawBack(true);
        widgets.addSlot(outputs.get(1), 61, 21).recipeContext(this).drawBack(true);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 30, 22);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 30, 22, 20000, true, false, false);
    }
}