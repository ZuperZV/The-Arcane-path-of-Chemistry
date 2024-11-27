package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.recipes.LatexBowlRecipe;
import net.minecraft.resources.ResourceLocation;

public class EmiLatexBowlRecipe extends BasicEmiRecipe {
    public static final EmiTexture LATEX_TEXTURE = new EmiTexture(ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/latex_bowl.png"), 18, 0, 18, 18, 18, 18, 18, 18);

    public EmiLatexBowlRecipe(LatexBowlRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.LATEX_CATEGORY, recipe.getId(), 83, 24);

        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));

        this.outputs.add(EmiStack.of(recipe.output));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 4, 4).drawBack(true);

        widgets.addSlot(outputs.get(0), 62, 4).recipeContext(this);

        widgets.addTexture(LATEX_TEXTURE, 18, 3);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 36, 5);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 36, 5, 20000, true, false, false);
    }
}
