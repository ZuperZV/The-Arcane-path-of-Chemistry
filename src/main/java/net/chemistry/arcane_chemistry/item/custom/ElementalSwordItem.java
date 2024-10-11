package net.chemistry.arcane_chemistry.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ElementalSwordItem extends SwordItem {
    private final String elementName;
    private final int color;

    public ElementalSwordItem(Tier tier, Properties properties,
                              String elementName, int color) {
        super(tier, properties);
        this.elementName = elementName;
        this.color = color;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, tooltipContext, pTooltipComponents, flag);

        pTooltipComponents.add(Component.literal(elementName).setStyle(Style.EMPTY.withColor(color)));
    }
}
