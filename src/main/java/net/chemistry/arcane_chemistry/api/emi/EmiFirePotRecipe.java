package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.recipes.FirePotRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmiFirePotRecipe extends BasicEmiRecipe {

    public EmiFirePotRecipe(FirePotRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.FIRE_POT_CATEGORY, recipe.getId(), 100, 58);

        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        if (recipe.getIngredients().size() > 1) this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        if (recipe.getIngredients().size() > 2) this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(2)));
        if (recipe.getIngredients().size() > 3) this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(3)));
        if (recipe.getIngredients().size() > 4) this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(4)));

        this.outputs.add(EmiStack.of(recipe.output));
        this.outputs.add(EmiStack.of(recipe.output2));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 21, 40).drawBack(true);
        if (inputs.size() > 1) widgets.addSlot(inputs.get(1), 2, 11).drawBack(true);
        if (inputs.size() > 2) widgets.addSlot(inputs.get(2), 21, 2).drawBack(true);
        if (inputs.size() > 3) widgets.addSlot(inputs.get(3), 40, 11).drawBack(true);
        if (inputs.size() > 4) widgets.addSlot(inputs.get(4), 21, 21).drawBack(true);

        widgets.addSlot(outputs.get(0), 80, 11).recipeContext(this).drawBack(true);
        widgets.addSlot(outputs.get(1), 80, 31).recipeContext(this).drawBack(true);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 48, 35);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 48, 35, 20000, true, false, false);
    }
}
