package net.chemistry.arcane_chemistry.item.custom.decorator;

import net.chemistry.arcane_chemistry.item.custom.AtomItem;
import net.chemistry.arcane_chemistry.item.custom.AtomItemEmptyReagent;
import net.chemistry.arcane_chemistry.item.custom.AtomItemReagent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;
import com.mojang.blaze3d.vertex.PoseStack;

public class NumberDecorator implements IItemDecorator {

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (stack.getItem() instanceof AtomItem atomItem) {
            String elementName = atomItem.getElementName();

            if (elementName != null && !elementName.isEmpty()) {
                int textColor = 0xFFFFFF;

                PoseStack poseStack = guiGraphics.pose();

                poseStack.pushPose();

                poseStack.translate(xOffset, yOffset, 200);

                guiGraphics.drawString(font, elementName, 1, 1, textColor, false);

                poseStack.popPose();

                return true;
            }
        } else if (stack.getItem() instanceof AtomItemReagent atomItem) {
            String elementName = atomItem.getElementName();

            if (elementName != null && !elementName.isEmpty()) {
                int textColor = 0xFFFFFF;

                PoseStack poseStack = guiGraphics.pose();

                poseStack.pushPose();

                poseStack.translate(xOffset, yOffset, 200);

                guiGraphics.drawString(font, elementName, 1, 1, textColor, false);

                poseStack.popPose();

                return true;
            }
        }

        return false;
    }
}
