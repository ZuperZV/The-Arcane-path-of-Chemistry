package net.chemistry.arcane_chemistry.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chemistry.arcane_chemistry.recipes.AtomicNucleusConstructorRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmiAtomicNucleusConstructorRecipe extends BasicEmiRecipe {

    public EmiAtomicNucleusConstructorRecipe(AtomicNucleusConstructorRecipe recipe) {
        super(ArcaneChemistryEmiPlugin.ATOMIC_NUCLEUS_CONSTRUCTOR_CATEGORY, recipe.getId(), 160, 90);

        this.inputs.add(EmiIngredient.of(recipe.nucleusCell));
        recipe.ingredient1.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient2.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient3.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient4.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient5.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient6.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient7.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient8.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient9.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));
        recipe.ingredient10.ifPresent(ing -> this.inputs.add(EmiIngredient.of(ing)));

        this.outputs.add(EmiStack.of(recipe.output));
        this.outputs.add(EmiStack.of(recipe.output2));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 53, 37).drawBack(true);
        widgets.addSlot(inputs.get(1), 79, 37).drawBack(true);
        widgets.addSlot(inputs.get(2), 25, 37).drawBack(true);
        widgets.addSlot(inputs.get(3), 53, 10).drawBack(true);
        widgets.addSlot(inputs.get(4), 88, 18).drawBack(true);
        widgets.addSlot(inputs.get(5), 103, 37).drawBack(true);
        widgets.addSlot(inputs.get(6), 88, 56).drawBack(true);
        widgets.addSlot(inputs.get(7), 53, 64).drawBack(true);
        widgets.addSlot(inputs.get(8), 16, 56).drawBack(true);
        widgets.addSlot(inputs.get(9), 2, 37).drawBack(true);
        widgets.addSlot(inputs.get(10), 16, 18).drawBack(true);

        widgets.addSlot(outputs.get(0), 122, 37).recipeContext(this);
        widgets.addSlot(outputs.get(1), 141, 37).recipeContext(this);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 122, 12);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 121, 12, 20000, true, false, false);
    }
}
