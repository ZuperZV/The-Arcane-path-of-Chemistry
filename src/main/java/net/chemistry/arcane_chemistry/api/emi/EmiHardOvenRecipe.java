package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.recipes.HardOvenRecipe;

public class EmiHardOvenRecipe extends BasicEmiRecipe {

    public EmiHardOvenRecipe(HardOvenRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.HARD_OVEN_CATEGORY, recipe.getId(), 100, 40);

        if (recipe.getIngredients().size() >= 2) {
            this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
            this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        } else if ((recipe.getIngredients().size() == 1)){
            this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        }

        this.outputs.add(EmiStack.of(recipe.output));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 2, 3).drawBack(true);
        widgets.addSlot(inputs.get(1), 25, 3).drawBack(true);

        widgets.addSlot(outputs.get(0), 75, 12).recipeContext(this);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 48, 13);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 48, 14, 20000, true, false, false);

        widgets.addTexture(EmiTexture.EMPTY_FLAME, 17, 24);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 17, 24, 20000, false, true, true);
    }
}