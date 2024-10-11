package net.chemistry.arcane_chemistry.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.*;

import java.util.List;

public class ElementalPickaxeItem extends PickaxeItem {
    private final String elementName;
    private final int color;

    public ElementalPickaxeItem(Tier tier, Properties properties,
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
