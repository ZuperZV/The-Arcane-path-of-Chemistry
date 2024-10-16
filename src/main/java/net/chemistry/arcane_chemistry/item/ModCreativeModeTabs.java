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
                    .icon(() -> new ItemStack(ModItems.IRON.get()))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.FLINT_SWORD.get());
                        pOutput.accept(ModItems.FLINT_PICKAXE.get());
                        pOutput.accept(ModItems.FLINT_AXE.get());
                        pOutput.accept(ModItems.FLINT_SHOVEL.get());
                        pOutput.accept(ModItems.FLINT_HOE.get());

                        pOutput.accept(ModBlocks.HARD_OVEN.get());
                        pOutput.accept(ModBlocks.FIRE_POT.get());

                        pOutput.accept(ModBlocks.NICKEL_COMPRESER.get());
                        pOutput.accept(ModBlocks.NICKEL_BLOCK.get());
                        pOutput.accept(ModItems.NICKEL_INGOT.get());
                        pOutput.accept(ModItems.RAW_NICKEL.get());
                        pOutput.accept(ModItems.RAW_IMPURE_NICKEL.get());
                        pOutput.accept(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get());
                        pOutput.accept(ModItems.RAW_IMPURE_IRON.get());
                        pOutput.accept(ModItems.CRUSHED_RAW_IRON.get());

                        pOutput.accept(ModItems.SCHEELITE_CRYSTAL.get());
                        pOutput.accept(ModItems.CRUSHED_SCHEELITE_CRYSTAL.get());
                        pOutput.accept(ModItems.RAW_COBALT.get());
                        pOutput.accept(ModItems.COBALT_INGOT.get());
                        pOutput.accept(ModItems.CARBON_CHUNK.get());
                        pOutput.accept(ModItems.CARBIDE_INGOT.get());
                        pOutput.accept(ModItems.RAW_CARBIDE.get());
                        pOutput.accept(ModItems.TUNGSTEN_INGOT.get());
                        pOutput.accept(ModItems.TUNGSTEN_CARBIDE_INGOT.get());
                        pOutput.accept(ModBlocks.VANADIUM_BLOCK.get());
                        pOutput.accept(ModItems.VANADIUM_INGOT.get());
                        pOutput.accept(ModItems.VANADIUM_CATALYST.get());


                        pOutput.accept(ModItems.WATER_REAGENT.get());

                        pOutput.accept(ModItems.REAGENT.get());
                        pOutput.accept(ModItems.IRON_REAGENT.get());
                        pOutput.accept(ModItems.RED_IRON_REAGENT.get());

                        pOutput.accept(ModItems.LITHIUM.get());
                        pOutput.accept(ModItems.SODIUM.get());
                        pOutput.accept(ModItems.POTASSIUM.get());
                        pOutput.accept(ModItems.RUBIDIUM.get());
                        pOutput.accept(ModItems.CAESIUM.get());
                        pOutput.accept(ModItems.FRANCIUM.get());
                        pOutput.accept(ModItems.BERYLLIUM.get());
                        pOutput.accept(ModItems.MAGNESIUM.get());
                        pOutput.accept(ModItems.CALCIUM.get());
                        pOutput.accept(ModItems.STRONTIUM.get());
                        pOutput.accept(ModItems.BARIUM.get());
                        pOutput.accept(ModItems.RADIUM.get());
                        pOutput.accept(ModItems.SCANDIUM.get());
                        pOutput.accept(ModItems.TITANIUM.get());
                        pOutput.accept(ModItems.VANADIUM.get());
                        pOutput.accept(ModItems.CHROMIUM.get());
                        pOutput.accept(ModItems.MANGANESE.get());
                        pOutput.accept(ModItems.IRON.get());
                        pOutput.accept(ModItems.COBALT.get());
                        pOutput.accept(ModItems.NICKEL.get());
                        pOutput.accept(ModItems.COPPER.get());
                        pOutput.accept(ModItems.ZINC.get());
                        pOutput.accept(ModItems.YTTRIUM.get());
                        pOutput.accept(ModItems.ZIRCONIUM.get());
                        pOutput.accept(ModItems.NIOBIUM.get());
                        pOutput.accept(ModItems.MOLYBDENUM.get());
                        pOutput.accept(ModItems.TECHNETIUM.get());
                        pOutput.accept(ModItems.RUTHENIUM.get());
                        pOutput.accept(ModItems.RHODIUM.get());
                        pOutput.accept(ModItems.PALLADIUM.get());
                        pOutput.accept(ModItems.SILVER.get());
                        pOutput.accept(ModItems.CADMIUM.get());
                        pOutput.accept(ModItems.HAFNIUM.get());
                        pOutput.accept(ModItems.TANTALUM.get());
                        pOutput.accept(ModItems.TUNGSTEN.get());
                        pOutput.accept(ModItems.RHENIUM.get());
                        pOutput.accept(ModItems.OSMIUM.get());
                        pOutput.accept(ModItems.IRIDIUM.get());
                        pOutput.accept(ModItems.PLATINUM.get());
                        pOutput.accept(ModItems.GOLD.get());
                        pOutput.accept(ModItems.MERCURY.get());
                        pOutput.accept(ModItems.ALUMINIUM.get());
                        pOutput.accept(ModItems.GALLIUM.get());
                        pOutput.accept(ModItems.INDIUM.get());
                        pOutput.accept(ModItems.TIN.get());
                        pOutput.accept(ModItems.THALLIUM.get());
                        pOutput.accept(ModItems.LEAD.get());
                        pOutput.accept(ModItems.BISMUTH.get());
                        pOutput.accept(ModItems.POLONIUM.get());
                        pOutput.accept(ModItems.LANTHANUM.get());
                        pOutput.accept(ModItems.CERIUM.get());
                        pOutput.accept(ModItems.PRASEODYMIUM.get());
                        pOutput.accept(ModItems.NEODYMIUM.get());
                        pOutput.accept(ModItems.PROMETHIUM.get());
                        pOutput.accept(ModItems.SAMARIUM.get());
                        pOutput.accept(ModItems.EUROPIUM.get());
                        pOutput.accept(ModItems.GADOLINIUM.get());
                        pOutput.accept(ModItems.TERBIUM.get());
                        pOutput.accept(ModItems.DYSPROSIUM.get());
                        pOutput.accept(ModItems.HOLMIUM.get());
                        pOutput.accept(ModItems.ERBIUM.get());
                        pOutput.accept(ModItems.THULIUM.get());
                        pOutput.accept(ModItems.YTTERBIUM.get());
                        pOutput.accept(ModItems.LUTETIUM.get());
                        pOutput.accept(ModItems.ACTINIUM.get());
                        pOutput.accept(ModItems.THORIUM.get());
                        pOutput.accept(ModItems.PROTACTINIUM.get());
                        pOutput.accept(ModItems.URANIUM.get());
                        pOutput.accept(ModItems.NEPTUNIUM.get());
                        pOutput.accept(ModItems.PLUTONIUM.get());
                        pOutput.accept(ModItems.AMERICIUM.get());
                        pOutput.accept(ModItems.CURIUM.get());
                        pOutput.accept(ModItems.BERKELIUM.get());
                        pOutput.accept(ModItems.CALIFORNIUM.get());
                        pOutput.accept(ModItems.EINSTEINIUM.get());
                        pOutput.accept(ModItems.FERMUM.get());
                        pOutput.accept(ModItems.MENDELEVIUM.get());
                        pOutput.accept(ModItems.NOBELIUM.get());
                        pOutput.accept(ModItems.LAWRENCIUM.get());
                        pOutput.accept(ModItems.RUTHERFORDIUM.get());
                        pOutput.accept(ModItems.DUBNIUM.get());
                        pOutput.accept(ModItems.SEABORGIUM.get());
                        pOutput.accept(ModItems.BOHRIUM.get());
                        pOutput.accept(ModItems.HASSIUM.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
