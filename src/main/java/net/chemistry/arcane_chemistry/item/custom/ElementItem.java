package net.chemistry.arcane_chemistry.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ElementItem extends Item {
    private final String elementName;
    private final String number;
    private final int color;
    private final int numberColor;

    public ElementItem(Properties properties, String elementName, int color, String number, int numberColor) {
        super(properties);
        this.elementName = elementName;
        this.number = number;
        this.color = color;
        this.numberColor = numberColor;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> pTooltipComponents, TooltipFlag flag) {

        pTooltipComponents.add(Component.literal (elementName).setStyle(Style.EMPTY.withColor(color)));
        pTooltipComponents.add(Component.literal (number).setStyle(Style.EMPTY.withColor(numberColor)));
    }
}
