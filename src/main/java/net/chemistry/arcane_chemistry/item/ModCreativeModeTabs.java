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

                        pOutput.accept(ModBlocks.NICKEL_BLOCK.get());
                        pOutput.accept(ModItems.NICKEL_INGOT.get());
                        pOutput.accept(ModItems.RAW_NICKEL.get());
                        pOutput.accept(ModItems.RAW_IMPURE_NICKEL.get());
                        pOutput.accept(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get());
                        pOutput.accept(ModItems.RAW_IMPURE_IRON.get());

                        // Alkali Metals
                        pOutput.accept(ModItems.LITHIUM.get());     // Lithium
                        pOutput.accept(ModItems.SODIUM.get());      // Sodium
                        pOutput.accept(ModItems.POTASSIUM.get());   // Potassium
                        pOutput.accept(ModItems.RUBIDIUM.get());    // Rubidium
                        pOutput.accept(ModItems.CAESIUM.get());     // Caesium
                        pOutput.accept(ModItems.FRANCIUM.get());    // Francium

                        // Alkaline Earth Metals
                        pOutput.accept(ModItems.BERYLLIUM.get());   // Beryllium
                        pOutput.accept(ModItems.MAGNESIUM.get());   // Magnesium
                        pOutput.accept(ModItems.CALCIUM.get());     // Calcium
                        pOutput.accept(ModItems.STRONTIUM.get());   // Strontium
                        pOutput.accept(ModItems.BARIUM.get());      // Barium
                        pOutput.accept(ModItems.RADIUM.get());      // Radium

                        // Transition Metals
                        pOutput.accept(ModItems.SCANDIUM.get());    // Scandium
                        pOutput.accept(ModItems.TITANIUM.get());    // Titanium
                        pOutput.accept(ModItems.VANADIUM.get());    // Vanadium
                        pOutput.accept(ModItems.CHROMIUM.get());    // Chromium
                        pOutput.accept(ModItems.MANGANESE.get());   // Manganese
                        pOutput.accept(ModItems.IRON.get());        // Iron
                        pOutput.accept(ModItems.COBALT.get());      // Cobalt
                        pOutput.accept(ModItems.NICKEL.get());      // Nickel
                        pOutput.accept(ModItems.COPPER.get());      // Copper
                        pOutput.accept(ModItems.ZINC.get());        // Zinc
                        pOutput.accept(ModItems.YTTRIUM.get());     // Yttrium
                        pOutput.accept(ModItems.ZIRCONIUM.get());   // Zirconium
                        pOutput.accept(ModItems.NIOBIUM.get());     // Niobium
                        pOutput.accept(ModItems.MOLYBDENUM.get());  // Molybdenum
                        pOutput.accept(ModItems.TECHNETIUM.get());  // Technetium
                        pOutput.accept(ModItems.RUTHENIUM.get());   // Ruthenium
                        pOutput.accept(ModItems.RHODIUM.get());     // Rhodium
                        pOutput.accept(ModItems.PALLADIUM.get());   // Palladium
                        pOutput.accept(ModItems.SILVER.get());      // Silver
                        pOutput.accept(ModItems.CADMIUM.get());     // Cadmium
                        pOutput.accept(ModItems.HAFNIUM.get());     // Hafnium
                        pOutput.accept(ModItems.TANTALUM.get());    // Tantalum
                        pOutput.accept(ModItems.TUNGSTEN.get());    // Tungsten
                        pOutput.accept(ModItems.RHENIUM.get());     // Rhenium
                        pOutput.accept(ModItems.OSMIUM.get());      // Osmium
                        pOutput.accept(ModItems.IRIDIUM.get());     // Iridium
                        pOutput.accept(ModItems.PLATINUM.get());    // Platinum
                        pOutput.accept(ModItems.GOLD.get());        // Gold
                        pOutput.accept(ModItems.MERCURY.get());     // Mercury

                        // Post-Transition Metals
                        pOutput.accept(ModItems.ALUMINIUM.get());   // Aluminium
                        pOutput.accept(ModItems.GALLIUM.get());     // Gallium
                        pOutput.accept(ModItems.INDIUM.get());      // Indium
                        pOutput.accept(ModItems.TIN.get());         // Tin
                        pOutput.accept(ModItems.THALLIUM.get());    // Thallium
                        pOutput.accept(ModItems.LEAD.get());        // Lead
                        pOutput.accept(ModItems.BISMUTH.get());     // Bismuth

                        // Lanthanides
                        pOutput.accept(ModItems.LANTHANUM.get());   // Lanthanum
                        pOutput.accept(ModItems.CERIUM.get());      // Cerium
                        pOutput.accept(ModItems.PRASEODYMIUM.get());// Praseodymium
                        pOutput.accept(ModItems.NEODYMIUM.get());   // Neodymium
                        pOutput.accept(ModItems.PROMETHIUM.get());  // Promethium
                        pOutput.accept(ModItems.SAMARIUM.get());    // Samarium
                        pOutput.accept(ModItems.EUROPIUM.get());    // Europium
                        pOutput.accept(ModItems.GADOLINIUM.get());  // Gadolinium
                        pOutput.accept(ModItems.TERBIUM.get());     // Terbium
                        pOutput.accept(ModItems.DYSPROSIUM.get());  // Dysprosium
                        pOutput.accept(ModItems.HOLMIUM.get());     // Holmium
                        pOutput.accept(ModItems.ERBIUM.get());      // Erbium
                        pOutput.accept(ModItems.THULIUM.get());     // Thulium
                        pOutput.accept(ModItems.YTTERBIUM.get());   // Ytterbium
                        pOutput.accept(ModItems.LUTETIUM.get());    // Lutetium

                        // Actinides
                        pOutput.accept(ModItems.ACTINIUM.get());    // Actinium
                        pOutput.accept(ModItems.THORIUM.get());     // Thorium
                        pOutput.accept(ModItems.PROTACTINIUM.get());// Protactinium
                        pOutput.accept(ModItems.URANIUM.get());     // Uranium
                        pOutput.accept(ModItems.NEPTUNIUM.get());   // Neptunium
                        pOutput.accept(ModItems.PLUTONIUM.get());   // Plutonium
                        pOutput.accept(ModItems.AMERICIUM.get());   // Americium
                        pOutput.accept(ModItems.CURIUM.get());      // Curium
                        pOutput.accept(ModItems.BERKELIUM.get());   // Berkelium
                        pOutput.accept(ModItems.CALIFORNIUM.get()); // Californium
                        pOutput.accept(ModItems.EINSTEINIUM.get()); // Einsteinium
                        pOutput.accept(ModItems.FERMUM.get());      // Fermium
                        pOutput.accept(ModItems.MENDELEVIUM.get()); // Mendelevium
                        pOutput.accept(ModItems.NOBELIUM.get());    // Nobelium
                        pOutput.accept(ModItems.LAWRENCIUM.get());  // LawrenciumNobelium
                        pOutput.accept(ModItems.RUTHERFORDIUM.get());
                        pOutput.accept(ModItems.DUBNIUM.get());
                        pOutput.accept(ModItems.SEABORGIUM.get());
                        pOutput.accept(ModItems.BOHRIUM.get());
                        pOutput.accept(ModItems.HASSIUM.get());

                        // pOutput.accept(ModItems.HYDROGEN.get());  // Hydrogen
                        // pOutput.accept(ModItems.HELIUM.get());    // Helium
                        // pOutput.accept(ModItems.CARBON.get());    // Carbon
                        // pOutput.accept(ModItems.NITROGEN.get());  // Nitrogen
                        // pOutput.accept(ModItems.OXYGEN.get());    // Oxygen
                        // pOutput.accept(ModItems.FLUORINE.get());  // Fluorine
                        // pOutput.accept(ModItems.PHOSPHORUS.get());// Phosphorus
                        // pOutput.accept(ModItems.SULFUR.get());    // Sulfur
                        // pOutput.accept(ModItems.CHLORINE.get());  // Chlorine
                        // pOutput.accept(ModItems.BROMINE.get());   // Bromine
                        // pOutput.accept(ModItems.IODINE.get());    // Iodine
                        // pOutput.accept(ModItems.NEON.get());      // Neon
                        // pOutput.accept(ModItems.ARGON.get());     // Argon
                        // pOutput.accept(ModItems.KRYPTON.get());   // Krypton
                        // pOutput.accept(ModItems.XENON.get());     // Xenon
                        // pOutput.accept(ModItems.RADON.get());     // Radon
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
