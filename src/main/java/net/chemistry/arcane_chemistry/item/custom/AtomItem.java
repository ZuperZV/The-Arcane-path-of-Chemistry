package net.chemistry.arcane_chemistry.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AtomItem extends Item {
    private final String elementName;
    private final int color;

    private final String Skall;

    private final String Skallnumber;
    private final int SkallnumberColor;

    private final String Skallnumber2;
    private final int SkallnumberColor2;

    private final String Skallnumber3;
    private final int SkallnumberColor3;

    private final String Skallnumber4;
    private final int SkallnumberColor4;

    private final String Skallnumber5;
    private final int SkallnumberColor5;

    private final String Skallnumber6;
    private final int SkallnumberColor6;

    private final String Skallnumber7;
    private final int SkallnumberColor7;

    private final String Skallnumber8;
    private final int SkallnumberColor8;

    public AtomItem(Properties properties, String Skall, String elementName, int color, String Skallnumber,
                    int SkallnumberColor, String Skallnumber2, int SkallnumberColor2,
                    String Skallnumber3, int SkallnumberColor3,
                    String Skallnumber4, int SkallnumberColor4,
                    String Skallnumber5, int SkallnumberColor5,
                    String Skallnumber6, int SkallnumberColor6,
                    String Skallnumber7, int SkallnumberColor7,
                    String Skallnumber8, int SkallnumberColor8) {
        super(properties);
        this.elementName = elementName;
        this.color = color;

        this.Skall = Skall;

        this.Skallnumber = Skallnumber;
        this.SkallnumberColor = SkallnumberColor;

        this.Skallnumber2 = Skallnumber2;
        this.SkallnumberColor2 = SkallnumberColor2;

        this.Skallnumber3 = Skallnumber3;
        this.SkallnumberColor3 = SkallnumberColor3;

        this.Skallnumber4 = Skallnumber4;
        this.SkallnumberColor4 = SkallnumberColor4;

        this.Skallnumber5 = Skallnumber5;
        this.SkallnumberColor5 = SkallnumberColor5;

        this.Skallnumber6 = Skallnumber6;
        this.SkallnumberColor6 = SkallnumberColor6;

        this.Skallnumber7 = Skallnumber7;
        this.SkallnumberColor7 = SkallnumberColor7;

        this.Skallnumber8 = Skallnumber8;
        this.SkallnumberColor8 = SkallnumberColor8;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> pTooltipComponents, TooltipFlag flag) {

        pTooltipComponents.add(Component.literal (elementName).setStyle(Style.EMPTY.withColor(color)));

        if (!(Skallnumber == "0"))
        pTooltipComponents.add(Component.literal (Skallnumber).setStyle(Style.EMPTY.withColor(SkallnumberColor)));

        if (!(Skallnumber2 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber2).setStyle(Style.EMPTY.withColor(SkallnumberColor2)));

        if (!(Skallnumber3 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber3).setStyle(Style.EMPTY.withColor(SkallnumberColor3)));

        if (!(Skallnumber4 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber4).setStyle(Style.EMPTY.withColor(SkallnumberColor4)));

        if (!(Skallnumber5 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber5).setStyle(Style.EMPTY.withColor(SkallnumberColor5)));

        if (!(Skallnumber6 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber6).setStyle(Style.EMPTY.withColor(SkallnumberColor6)));

        if (!(Skallnumber7 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber7).setStyle(Style.EMPTY.withColor(SkallnumberColor7)));

        if (!(Skallnumber8 == "0"))
            pTooltipComponents.add(Component.literal (Skallnumber8).setStyle(Style.EMPTY.withColor(SkallnumberColor8)));
    }
}
