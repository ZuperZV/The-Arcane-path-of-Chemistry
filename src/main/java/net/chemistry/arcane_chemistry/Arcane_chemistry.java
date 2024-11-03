package net.chemistry.arcane_chemistry;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.block.entity.renderer.CentrifugeBlockEntityRenderer;
import net.chemistry.arcane_chemistry.block.entity.renderer.ElectrolyzerBlockEntityRenderer;
import net.chemistry.arcane_chemistry.block.entity.renderer.PedestalSlabBlockEntityRenderer;
import net.chemistry.arcane_chemistry.fluid.ModFluidTypes;
import net.chemistry.arcane_chemistry.fluid.ModFluids;
import net.chemistry.arcane_chemistry.fluid.fluidTypes.BaseFluidType;
import net.chemistry.arcane_chemistry.item.ModCreativeModeTabs;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.item.custom.decorator.NumberDecorator;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.screen.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
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

        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
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
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.CENTRIFUGE_BLOCK_ENTITY.get(), CentrifugeBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.ELECTOLYZER_BLOCK_ENTITY.get(), ElectrolyzerBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SLAB_BE.get(), PedestalSlabBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.HARD_OVEN_MENU.get(), HardOvenScreen::new);
            event.register(ModMenuTypes.NICKEL_COMPRESER_MENU.get(), NickelCompreserScreen::new);
            event.register(ModMenuTypes.FLOTATIONER_MENU.get(), FlotationerScreen::new);
            event.register(ModMenuTypes.ATOMIC_OVEN_MENU.get(), AtomicOvenScreen::new);
            event.register(ModMenuTypes.TUNGSTEN_COMPRESER_MENU.get(), TungstenCompreserScreen::new);
            event.register(ModMenuTypes.HEATER_MENU.get(), HeaterScreen::new);
            event.register(ModMenuTypes.CHAMBER_MENU.get(), ChamberScreen::new);
            event.register(ModMenuTypes.ATOMIC_NUCLEUS_CONSTRUCTOR_MENU.get(), AtomicNucleusConstructorScreen::new);
            event.register(ModMenuTypes.AURORA_CRAFTER_MENU.get(), AuroraCrafterScreen::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                //ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_CRUDE_OIL.get(), RenderType.translucent());
                //ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_CRUDE_OIL.get(), RenderType.translucent());
            });
        }
        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) ModFluidTypes.CRUDE_OIL_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.CRUDE_OIL_FLUID_TYPE.get());
        }

        @SubscribeEvent
        public static void registerItemDecorators(RegisterItemDecorationsEvent event) {

            event.register(ModItems.IRON_REAGENT.get(), new NumberDecorator());
            event.register(ModItems.RED_IRON_REAGENT.get(), new NumberDecorator());

            event.register(ModItems.IRON_NUCLEUS.get(), new NumberDecorator());
            event.register(ModItems.ALUMINIUM_NUCLEUS.get(), new NumberDecorator());
            event.register(ModItems.LEAD_NUCLEUS.get(), new NumberDecorator());
            event.register(ModItems.COPPER_NUCLEUS.get(), new NumberDecorator());
            event.register(ModItems.SILVER_NUCLEUS.get(), new NumberDecorator());

            event.register(ModItems.LITHIUM.get(), new NumberDecorator());
            event.register(ModItems.SODIUM.get(), new NumberDecorator());
            event.register(ModItems.POTASSIUM.get(), new NumberDecorator());
            event.register(ModItems.RUBIDIUM.get(), new NumberDecorator());
            event.register(ModItems.CAESIUM.get(), new NumberDecorator());
            event.register(ModItems.FRANCIUM.get(), new NumberDecorator());
            event.register(ModItems.BERYLLIUM.get(), new NumberDecorator());
            event.register(ModItems.MAGNESIUM.get(), new NumberDecorator());
            event.register(ModItems.CALCIUM.get(), new NumberDecorator());
            event.register(ModItems.STRONTIUM.get(), new NumberDecorator());
            event.register(ModItems.BARIUM.get(), new NumberDecorator());
            event.register(ModItems.RADIUM.get(), new NumberDecorator());
            event.register(ModItems.SCANDIUM.get(), new NumberDecorator());
            event.register(ModItems.TITANIUM.get(), new NumberDecorator());
            event.register(ModItems.VANADIUM.get(), new NumberDecorator());
            event.register(ModItems.CHROMIUM.get(), new NumberDecorator());
            event.register(ModItems.MANGANESE.get(), new NumberDecorator());
            event.register(ModItems.IRON.get(), new NumberDecorator());
            event.register(ModItems.COBALT.get(), new NumberDecorator());
            event.register(ModItems.NICKEL.get(), new NumberDecorator());
            event.register(ModItems.COPPER.get(), new NumberDecorator());
            event.register(ModItems.ZINC.get(), new NumberDecorator());
            event.register(ModItems.YTTRIUM.get(), new NumberDecorator());
            event.register(ModItems.ZIRCONIUM.get(), new NumberDecorator());
            event.register(ModItems.NIOBIUM.get(), new NumberDecorator());
            event.register(ModItems.MOLYBDENUM.get(), new NumberDecorator());
            event.register(ModItems.TECHNETIUM.get(), new NumberDecorator());
            event.register(ModItems.RUTHENIUM.get(), new NumberDecorator());
            event.register(ModItems.RHODIUM.get(), new NumberDecorator());
            event.register(ModItems.PALLADIUM.get(), new NumberDecorator());
            event.register(ModItems.SILVER.get(), new NumberDecorator());
            event.register(ModItems.CADMIUM.get(), new NumberDecorator());
            event.register(ModItems.HAFNIUM.get(), new NumberDecorator());
            event.register(ModItems.TANTALUM.get(), new NumberDecorator());
            event.register(ModItems.TUNGSTEN.get(), new NumberDecorator());
            event.register(ModItems.RHENIUM.get(), new NumberDecorator());
            event.register(ModItems.OSMIUM.get(), new NumberDecorator());
            event.register(ModItems.IRIDIUM.get(), new NumberDecorator());
            event.register(ModItems.PLATINUM.get(), new NumberDecorator());
            event.register(ModItems.GOLD.get(), new NumberDecorator());
            event.register(ModItems.MERCURY.get(), new NumberDecorator());
            event.register(ModItems.ALUMINIUM.get(), new NumberDecorator());
            event.register(ModItems.GALLIUM.get(), new NumberDecorator());
            event.register(ModItems.INDIUM.get(), new NumberDecorator());
            event.register(ModItems.TIN.get(), new NumberDecorator());
            event.register(ModItems.THALLIUM.get(), new NumberDecorator());
            event.register(ModItems.LEAD.get(), new NumberDecorator());
            event.register(ModItems.BISMUTH.get(), new NumberDecorator());
            event.register(ModItems.POLONIUM.get(), new NumberDecorator());
            event.register(ModItems.LANTHANUM.get(), new NumberDecorator());
            event.register(ModItems.CERIUM.get(), new NumberDecorator());
            event.register(ModItems.PRASEODYMIUM.get(), new NumberDecorator());
            event.register(ModItems.NEODYMIUM.get(), new NumberDecorator());
            event.register(ModItems.PROMETHIUM.get(), new NumberDecorator());
            event.register(ModItems.SAMARIUM.get(), new NumberDecorator());
            event.register(ModItems.EUROPIUM.get(), new NumberDecorator());
            event.register(ModItems.GADOLINIUM.get(), new NumberDecorator());
            event.register(ModItems.TERBIUM.get(), new NumberDecorator());
            event.register(ModItems.DYSPROSIUM.get(), new NumberDecorator());
            event.register(ModItems.HOLMIUM.get(), new NumberDecorator());
            event.register(ModItems.ERBIUM.get(), new NumberDecorator());
            event.register(ModItems.THULIUM.get(), new NumberDecorator());
            event.register(ModItems.YTTERBIUM.get(), new NumberDecorator());
            event.register(ModItems.LUTETIUM.get(), new NumberDecorator());
            event.register(ModItems.ACTINIUM.get(), new NumberDecorator());
            event.register(ModItems.THORIUM.get(), new NumberDecorator());
            event.register(ModItems.PROTACTINIUM.get(), new NumberDecorator());
            event.register(ModItems.URANIUM.get(), new NumberDecorator());
            event.register(ModItems.NEPTUNIUM.get(), new NumberDecorator());
            event.register(ModItems.PLUTONIUM.get(), new NumberDecorator());
            event.register(ModItems.AMERICIUM.get(), new NumberDecorator());
            event.register(ModItems.CURIUM.get(), new NumberDecorator());
            event.register(ModItems.BERKELIUM.get(), new NumberDecorator());
            event.register(ModItems.CALIFORNIUM.get(), new NumberDecorator());
            event.register(ModItems.EINSTEINIUM.get(), new NumberDecorator());
            event.register(ModItems.FERMUM.get(), new NumberDecorator());
            event.register(ModItems.MENDELEVIUM.get(), new NumberDecorator());
            event.register(ModItems.NOBELIUM.get(), new NumberDecorator());
            event.register(ModItems.LAWRENCIUM.get(), new NumberDecorator());
            event.register(ModItems.RUTHERFORDIUM.get(), new NumberDecorator());
            event.register(ModItems.DUBNIUM.get(), new NumberDecorator());
            event.register(ModItems.SEABORGIUM.get(), new NumberDecorator());
            event.register(ModItems.BOHRIUM.get(), new NumberDecorator());
            event.register(ModItems.HASSIUM.get(), new NumberDecorator());

            event.register(ModItems.CRYON.get(), new NumberDecorator());
        }
    }
}