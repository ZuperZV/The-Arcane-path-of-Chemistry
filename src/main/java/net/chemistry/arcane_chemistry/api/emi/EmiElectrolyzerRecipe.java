package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.recipes.ElectrolyzerRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmiElectrolyzerRecipe extends BasicEmiRecipe {

    public EmiElectrolyzerRecipe(ElectrolyzerRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.ELECTROLYZER_CATEGORY, recipe.getId(), 100, 39);

        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        if (recipe.getIngredients().size() > 1) {
            this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        }
        this.outputs.add(EmiStack.of(recipe.output));
        this.outputs.add(EmiStack.of(recipe.output2));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 2, 11).drawBack(true);
        if (inputs.size() > 1) {
            widgets.addSlot(inputs.get(1), 21, 11).drawBack(true);
        }
        widgets.addSlot(outputs.get(0), 82, 11).recipeContext(this).drawBack(true);
        widgets.addSlot(outputs.get(1), 63, 11).recipeContext(this).drawBack(true);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 39, 12);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 39, 12, 20000, true, false, false);
    }
}