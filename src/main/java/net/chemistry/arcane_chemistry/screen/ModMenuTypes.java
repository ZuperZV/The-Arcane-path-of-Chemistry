package net.chemistry.arcane_chemistry.screen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, Arcane_chemistry.MOD_ID);

    public static final Supplier<MenuType<HardOvenMenu>> HARD_OVEN_MENU = MENUS.register("hard_oven_menu",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new HardOvenMenu(windowId, inv.player, data.readBlockPos())));

    public static final Supplier<MenuType<NickelCompreserMenu>> NICKEL_COMPRESER_MENU = MENUS.register("nickel_compreser_menu",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new NickelCompreserMenu(windowId, inv.player, data.readBlockPos())));

    public static final Supplier<MenuType<TungstenCompreserMenu>> TUNGSTEN_COMPRESER_MENU = MENUS.register("tungsten_compreser_menu",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new TungstenCompreserMenu(windowId, inv.player, data.readBlockPos())));

    public static final Supplier<MenuType<FlotationerMenu>> FLOTATIONER_MENU = MENUS.register("flotationer_menu",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new FlotationerMenu(windowId, inv.player, data.readBlockPos())));

    public static final Supplier<MenuType<AtomicOvenMenu>> ATOMIC_OVEN_MENU = MENUS.register("atomic_oven_menu",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new AtomicOvenMenu(windowId, inv.player, data.readBlockPos())));

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                            String name) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
