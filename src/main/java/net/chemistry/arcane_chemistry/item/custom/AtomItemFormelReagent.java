package net.chemistry.arcane_chemistry.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AtomItemFormelReagent extends Item {
    public final String elementName;
    private final int color;

    public AtomItemFormelReagent(Properties properties, String elementName, int color) {
        super(properties);

        this.elementName = elementName;
        this.color = color;

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> pTooltipComponents, TooltipFlag flag) {

        pTooltipComponents.add(Component.literal (elementName).setStyle(Style.EMPTY.withColor(color)));
    }

    public String getElementName() {
        return this.elementName;
    }
}
