package net.chemistry.arcane_chemistry.item;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.item.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Arcane_chemistry.MOD_ID);

    public static final DeferredItem<ElementItem> IRON = ITEMS .register("iron",
            () -> new ElementItem(new Item.Properties(), "Fe", 0xffda39, "26", 0x282834));

    public static final DeferredItem<Item> FLINT_SWORD = ITEMS.register("flint_sword",
            () -> new ElementalSwordItem(
                    ModToolTiers.FLINT,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.FLINT, 3, -2.4F)), "SiO2", 0xffda39
            ));

    public static final DeferredItem<Item> FLINT_PICKAXE = ITEMS.register("flint_pickaxe",
            () -> new ElementalPickaxeItem(
                    ModToolTiers.FLINT,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.FLINT, 1, -2.8F)), "SiO2", 0xffda39
            ));

    public static final DeferredItem<Item> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new ElementalAxeItem(
                    ModToolTiers.FLINT,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.FLINT, 1.5F, -3.0F)), "SiO2", 0xffda39
            ));

    public static final DeferredItem<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new ElementalShovelItem(
                    ModToolTiers.FLINT,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.FLINT, 5.0F, -3.0F)), "SiO2", 0xffda39
            ));

    public static final DeferredItem<Item> FLINT_HOE = ITEMS.register("flint_hoe",
            () -> new ElementalHoeItem(
                    ModToolTiers.FLINT,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.FLINT, -4, 0.0F)), "SiO2", 0xffda39
            ));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}