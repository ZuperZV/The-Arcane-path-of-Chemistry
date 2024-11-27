package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.recipes.NickelCompreserRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmiNickelCompreserRecipe extends BasicEmiRecipe {

    public EmiNickelCompreserRecipe(NickelCompreserRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.NICKEL_COMPRESER_CATEGORY, recipe.getId(), 100, 24);

        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        if (recipe.getIngredients().size() > 1) {
            this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        }
        if (recipe.getIngredients().size() > 2) {
            this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(2)));
        }

        this.outputs.add(EmiStack.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 21, 2).drawBack(true);
        if (inputs.size() > 1) {
            widgets.addSlot(inputs.get(1), 2, 6).drawBack(true);
        }
        if (inputs.size() > 2) {
            widgets.addSlot(inputs.get(2), 40, 6).drawBack(true);
        }

        widgets.addSlot(outputs.get(0), 80, 4).recipeContext(this).drawBack(true);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 48, 4);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 48, 4, 20000, true, false, false);
    }
}
