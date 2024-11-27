package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.recipes.AtomicOvenRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmiAtomicOvenRecipe extends BasicEmiRecipe {

    public EmiAtomicOvenRecipe(AtomicOvenRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.ATOMIC_OVEN_CATEGORY, recipe.getId(), 100, 40);

        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));

        this.outputs.add(EmiStack.of(recipe.output));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 4, 5).drawBack(true);
        widgets.addSlot(inputs.get(1), 27, 5).drawBack(true);

        widgets.addSlot(outputs.get(0), 75, 12).recipeContext(this).drawBack(true);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 48, 13);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 48, 14, 20000, true, false, false);

        widgets.addTexture(EmiTexture.EMPTY_FLAME, 17, 24);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 17, 24, 20000, false, true, true);
    }
}
