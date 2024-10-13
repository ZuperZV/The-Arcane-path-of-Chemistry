package net.chemistry.arcane_chemistry;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.item.ModCreativeModeTabs;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.item.custom.decorator.NumberDecorator;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.screen.HardOvenScreen;
import net.chemistry.arcane_chemistry.screen.ModMenuTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Arcane_chemistry.MOD_ID)
public class Arcane_chemistry {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "arcane_chemistry";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Arcane_chemistry(IEventBus modEventBus, ModContainer modContainer) {

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModRecipes.SERIALIZERS.register(modEventBus);
        ModRecipes.RECIPE_TYPES.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        //modEventBus.addListener(this::onModSetup);
    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.HARD_OVEN_MENU.get(), HardOvenScreen::new);
        }

        @SubscribeEvent
        public static void registerItemDecorators(RegisterItemDecorationsEvent event) {

            // Alkali Metals
            event.register(ModItems.LITHIUM.get(), new NumberDecorator());   // Lithium
            event.register(ModItems.SODIUM.get(), new NumberDecorator());    // Sodium
            event.register(ModItems.POTASSIUM.get(), new NumberDecorator()); // Potassium
            event.register(ModItems.RUBIDIUM.get(), new NumberDecorator());  // Rubidium
            event.register(ModItems.CAESIUM.get(), new NumberDecorator());   // Caesium
            event.register(ModItems.FRANCIUM.get(), new NumberDecorator());  // Francium

            // Alkaline Earth Metals
            event.register(ModItems.BERYLLIUM.get(), new NumberDecorator()); // Beryllium
            event.register(ModItems.MAGNESIUM.get(), new NumberDecorator()); // Magnesium
            event.register(ModItems.CALCIUM.get(), new NumberDecorator());   // Calcium
            event.register(ModItems.STRONTIUM.get(), new NumberDecorator()); // Strontium
            event.register(ModItems.BARIUM.get(), new NumberDecorator());    // Barium
            event.register(ModItems.RADIUM.get(), new NumberDecorator());    // Radium

            // Transition Metals
            event.register(ModItems.SCANDIUM.get(), new NumberDecorator());  // Scandium
            event.register(ModItems.TITANIUM.get(), new NumberDecorator());  // Titanium
            event.register(ModItems.VANADIUM.get(), new NumberDecorator());  // Vanadium
            event.register(ModItems.CHROMIUM.get(), new NumberDecorator());  // Chromium
            event.register(ModItems.MANGANESE.get(), new NumberDecorator()); // Manganese
            event.register(ModItems.IRON.get(), new NumberDecorator());      // Iron
            event.register(ModItems.COBALT.get(), new NumberDecorator());    // Cobalt
            event.register(ModItems.NICKEL.get(), new NumberDecorator());    // Nickel
            event.register(ModItems.COPPER.get(), new NumberDecorator());    // Copper
            event.register(ModItems.ZINC.get(), new NumberDecorator());      // Zinc
            event.register(ModItems.YTTRIUM.get(), new NumberDecorator());   // Yttrium
            event.register(ModItems.ZIRCONIUM.get(), new NumberDecorator()); // Zirconium
            event.register(ModItems.NIOBIUM.get(), new NumberDecorator());   // Niobium
            event.register(ModItems.MOLYBDENUM.get(), new NumberDecorator()); // Molybdenum
            event.register(ModItems.TECHNETIUM.get(), new NumberDecorator()); // Technetium
            event.register(ModItems.RUTHENIUM.get(), new NumberDecorator()); // Ruthenium
            event.register(ModItems.RHODIUM.get(), new NumberDecorator());   // Rhodium
            event.register(ModItems.PALLADIUM.get(), new NumberDecorator()); // Palladium
            event.register(ModItems.SILVER.get(), new NumberDecorator());    // Silver
            event.register(ModItems.CADMIUM.get(), new NumberDecorator());   // Cadmium
            event.register(ModItems.HAFNIUM.get(), new NumberDecorator());   // Hafnium
            event.register(ModItems.TANTALUM.get(), new NumberDecorator());  // Tantalum
            event.register(ModItems.TUNGSTEN.get(), new NumberDecorator());  // Tungsten
            event.register(ModItems.RHENIUM.get(), new NumberDecorator());   // Rhenium
            event.register(ModItems.OSMIUM.get(), new NumberDecorator());    // Osmium
            event.register(ModItems.IRIDIUM.get(), new NumberDecorator());   // Iridium
            event.register(ModItems.PLATINUM.get(), new NumberDecorator());  // Platinum
            event.register(ModItems.GOLD.get(), new NumberDecorator());      // Gold
            event.register(ModItems.MERCURY.get(), new NumberDecorator());   // Mercury

            // Post-Transition Metals
            event.register(ModItems.ALUMINIUM.get(), new NumberDecorator()); // Aluminium
            event.register(ModItems.GALLIUM.get(), new NumberDecorator());   // Gallium
            event.register(ModItems.INDIUM.get(), new NumberDecorator());    // Indium
            event.register(ModItems.TIN.get(), new NumberDecorator());       // Tin
            event.register(ModItems.THALLIUM.get(), new NumberDecorator());  // Thallium
            event.register(ModItems.LEAD.get(), new NumberDecorator());      // Lead
            event.register(ModItems.BISMUTH.get(), new NumberDecorator());   // Bismuth

            // Lanthanides
            event.register(ModItems.LANTHANUM.get(), new NumberDecorator());  // Lanthanum
            event.register(ModItems.CERIUM.get(), new NumberDecorator());     // Cerium
            event.register(ModItems.PRASEODYMIUM.get(), new NumberDecorator());// Praseodymium
            event.register(ModItems.NEODYMIUM.get(), new NumberDecorator());  // Neodymium
            event.register(ModItems.PROMETHIUM.get(), new NumberDecorator()); // Promethium
            event.register(ModItems.SAMARIUM.get(), new NumberDecorator());   // Samarium
            event.register(ModItems.EUROPIUM.get(), new NumberDecorator());   // Europium
            event.register(ModItems.GADOLINIUM.get(), new NumberDecorator()); // Gadolinium
            event.register(ModItems.TERBIUM.get(), new NumberDecorator());    // Terbium
            event.register(ModItems.DYSPROSIUM.get(), new NumberDecorator()); // Dysprosium
            event.register(ModItems.HOLMIUM.get(), new NumberDecorator());    // Holmium
            event.register(ModItems.ERBIUM.get(), new NumberDecorator());     // Erbium
            event.register(ModItems.THULIUM.get(), new NumberDecorator());    // Thulium
            event.register(ModItems.YTTERBIUM.get(), new NumberDecorator());  // Ytterbium
            event.register(ModItems.LUTETIUM.get(), new NumberDecorator());   // Lutetium

            // Actinides
            event.register(ModItems.ACTINIUM.get(), new NumberDecorator());    // Actinium
            event.register(ModItems.THORIUM.get(), new NumberDecorator());     // Thorium
            event.register(ModItems.PROTACTINIUM.get(), new NumberDecorator());// Protactinium
            event.register(ModItems.URANIUM.get(), new NumberDecorator());     // Uranium
            event.register(ModItems.NEPTUNIUM.get(), new NumberDecorator());   // Neptunium
            event.register(ModItems.PLUTONIUM.get(), new NumberDecorator());   // Plutonium
            event.register(ModItems.AMERICIUM.get(), new NumberDecorator());   // Americium
            event.register(ModItems.CURIUM.get(), new NumberDecorator());      // Curium
            event.register(ModItems.BERKELIUM.get(), new NumberDecorator());   // Berkelium
            event.register(ModItems.CALIFORNIUM.get(), new NumberDecorator()); // Californium
            event.register(ModItems.EINSTEINIUM.get(), new NumberDecorator()); // Einsteinium
            event.register(ModItems.FERMUM.get(), new NumberDecorator());      // Fermium
            event.register(ModItems.MENDELEVIUM.get(), new NumberDecorator()); // Mendelevium
            event.register(ModItems.NOBELIUM.get(), new NumberDecorator());    // Nobelium
            event.register(ModItems.LAWRENCIUM.get(), new NumberDecorator());  // Lawrencium
        }
    }
}