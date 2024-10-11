package net.chemistry.arcane_chemistry.item;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Arcane_chemistry.MOD_ID);

    public static final Supplier<CreativeModeTab> ARCANE_CHEMISTRY_TAB =
            CREATIVE_MODE_TABS.register("arcane_chemistry_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.arcane_chemistry.arcane_chemistry_tab"))
                    .icon(() -> new ItemStack(ModItems.TEST.get()))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.TEST.get());
                        //pOutput.accept(ModBlocks..get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
