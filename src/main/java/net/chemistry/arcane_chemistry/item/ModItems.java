package net.chemistry.arcane_chemistry.item;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.item.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Arcane_chemistry.MOD_ID);

    public static final DeferredItem<Item> AMAFIST_SWORD = ITEMS.register("amafist_sword",
            () -> new ElementalSwordItem(
                    ModToolTiers.AMAFIST,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.AMAFIST, 3, -2.4F)), "aurora", 0xffda39
            ));

    public static final DeferredItem<Item> RAW_CARVIUM = ITEMS .register("raw_carvium",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CARVIUM_INGOT = ITEMS .register("carvium_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SKRAP_AURORA = ITEMS .register("skrap_aurora",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AURORA_INGOT = ITEMS .register("aurora_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PEBBLE = ITEMS .register("pebble",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<AtomItemEmptyReagent> REAGENT = ITEMS .register("reagent",
            () -> new AtomItemEmptyReagent(new Item.Properties(), "SiO2", 0xffda39));

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
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModToolTiers.FLINT, -1.5F, 0.0F)), "SiO2", 0xffda39
            ));

    public static final DeferredItem<Item> NICKEL_INGOT = ITEMS .register("nickel_ingot",
            () -> new NormalItem(new Item.Properties(), "Ni", 0xffda39));

    public static final DeferredItem<Item> RAW_NICKEL = ITEMS .register("raw_nickel",
            () -> new NormalItem(new Item.Properties(), "Ni", 0xffda39));

    public static final DeferredItem<Item> RAW_IMPURE_NICKEL_IRON_MIX = ITEMS .register("raw_impure_nickel_iron_mix",
            () -> new NormalItem(new Item.Properties(), "NiFe", 0xffda39));

    public static final DeferredItem<Item> RAW_IMPURE_NICKEL = ITEMS .register("raw_impure_nickel",
            () -> new NormalItem(new Item.Properties(), "Ni", 0xffda39));

    public static final DeferredItem<Item> RAW_IMPURE_IRON = ITEMS .register("raw_impure_iron",
            () -> new NormalItem(new Item.Properties(), "Fe", 0xffda39));

    public static final DeferredItem<Item> CRUSHED_RAW_IRON = ITEMS .register("crushed_raw_iron",
            () -> new NormalItem(new Item.Properties(), "Fe", 0xffda39));

    public static final DeferredItem<Item> TUNGSTEN_INGOT = ITEMS .register("tungsten_ingot",
            () -> new NormalItem(new Item.Properties(), "W", 0xffda39));

    public static final DeferredItem<Item> TUNGSTEN_CARBIDE_INGOT = ITEMS .register("tungsten_carbide_ingot",
            () -> new NormalItem(new Item.Properties(), "WC", 0xffda39));

    public static final DeferredItem<Item> RAW_CARBIDE = ITEMS .register("raw_carbide",
            () -> new NormalItem(new Item.Properties(), "C", 0xffda39));

    public static final DeferredItem<Item> CARBIDE_INGOT = ITEMS .register("carbide_ingot",
            () -> new NormalItem(new Item.Properties(), "C", 0xffda39));

    public static final DeferredItem<Item> CARBON_CHUNK = ITEMS .register("carbon_chunk",
            () -> new NormalItem(new Item.Properties(), "C", 0xffda39));

    public static final DeferredItem<Item> COBALT_INGOT = ITEMS .register("cobalt_ingot",
            () -> new NormalItem(new Item.Properties(), "Co", 0xffda39));

    public static final DeferredItem<Item> RAW_COBALT = ITEMS .register("raw_cobalt",
            () -> new NormalItem(new Item.Properties(), "Co", 0xffda39));

    public static final DeferredItem<Item> SCHEELITE_CRYSTAL = ITEMS .register("scheelite_crystal",
            () -> new NormalItem(new Item.Properties(), "CaWO₄", 0xffda39));

    public static final DeferredItem<Item> CRUSHED_SCHEELITE_CRYSTAL = ITEMS .register("crushed_scheelite_crystal",
            () -> new NormalItem(new Item.Properties(), "CaWO₄", 0xffda39));

    public static final DeferredItem<Item> CRUSHED_AMETHYST_SHARD = ITEMS .register("crushed_amethyst_shard",
            () -> new NormalItem(new Item.Properties(), "WO₄", 0xffda39));

    public static final DeferredItem<Item> AMETHYST_SHARDS = ITEMS .register("amethyst_shards",
            () -> new NormalItem(new Item.Properties(), "WO₄", 0xffda39));

    public static final DeferredItem<Item> VANADIUM_INGOT = ITEMS .register("vanadium_ingot",
            () -> new NormalItem(new Item.Properties(), "V", 0xffda39));

    public static final DeferredItem<Item> VANADIUM_CATALYST = ITEMS .register("vanadium_catalyst",
            () -> new NormalItem(new Item.Properties(), "V", 0xffda39));

    public static final DeferredItem<AtomItemReagent> IRON_REAGENT = ITEMS .register("iron_reagent",
            () -> new AtomItemReagent(new Item.Properties(), "2", "Fe", 0xffda39,
                    "26", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "14", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    0xc6c6c6));

    public static final DeferredItem<AtomItemReagent> RED_IRON_REAGENT = ITEMS .register("red_iron_reagent",
            () -> new AtomItemReagent(new Item.Properties(), "2", "Fe", 0xffda39,
                    "26", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "14", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    0xc6c6c6));

    public static final DeferredItem<AtomItemFormelReagent> WATER_REAGENT = ITEMS .register("water_reagent",
            () -> new AtomItemFormelReagent(new Item.Properties(), "H₂O", 0xffda39));

    public static final DeferredItem<Item> CHROMIUM_INGOT = ITEMS .register("chromium_ingot",
            () -> new NormalItem(new Item.Properties(), "Cr", 0xffda39));

    public static final DeferredItem<Item> CHROMIUM_CHUNK_MIX = ITEMS .register("chromium_chunk_mix",
            () -> new NormalItem(new Item.Properties(), "Cr", 0xffda39));

    public static final DeferredItem<Item> SALT = ITEMS .register("salt",
            () -> new NormalItem(new Item.Properties(), "NaCl", 0xffda39));

    public static final DeferredItem<AtomItemFormelReagent> SALT_REAGENT = ITEMS .register("salt_reagent",
            () -> new AtomItemFormelReagent(new Item.Properties(), "NaCl", 0xffda39));

    public static final DeferredItem<AtomItemFormelReagent> SODIUM_CHLORIDE = ITEMS .register("sodium_chloride",
            () -> new AtomItemFormelReagent(new Item.Properties(), "NaCl", 0xffda39));

    public static final DeferredItem<AtomItemFormelReagent> CHLORINE_GAS = ITEMS .register("chlorine_gas",
            () -> new AtomItemFormelReagent(new Item.Properties(), "NaCl", 0xffda39));

    public static final DeferredItem<Item> SODIUM_CHROMATE = ITEMS .register("sodium_chromate",
            () -> new NormalItem(new Item.Properties(), "Na₂CrO₄", 0xffda39));

    public static final DeferredItem<Item> SULFUR = ITEMS .register("sulfur",
            () -> new NormalItem(new Item.Properties(), "S", 0xffda39));

    public static final DeferredItem<AtomItemFormelReagent> SULFURIC_ACID_MIX = ITEMS .register("sulfuric_acid_mix",
            () -> new AtomItemFormelReagent(new Item.Properties(), "SO₂ + H₂O", 0xffda39));

    public static final DeferredItem<AtomItemFormelReagent> SULFURIC_ACID = ITEMS .register("sulfuric_acid",
            () -> new AtomItemFormelReagent(new Item.Properties(), "H₂SO₄", 0xffda39));

    public static final DeferredItem<Item> SODIUM_DICHROMATE = ITEMS .register("sodium_dichromate",
            () -> new NormalItem(new Item.Properties(), "Na₂Cr₂O₇", 0xffda39));

    public static final DeferredItem<Item> LATEX_BALL = ITEMS .register("latex_ball",
            () -> new NormalItem(new Item.Properties(), "C₅H₈n", 0xffda39));

    public static final DeferredItem<Item> FLAT_LATEX = ITEMS .register("flat_latex",
            () -> new NormalItem(new Item.Properties(), "C₅H₈n", 0xffda39));

    public static final DeferredItem<Item> SMOKED_LATEX = ITEMS .register("smoked_latex",
            () -> new NormalItem(new Item.Properties(), "C₅H₈n", 0xffda39));

    public static final DeferredItem<Item> PROTON = ITEMS .register("proton",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> NEUTRON = ITEMS .register("neutron",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ELECTRON = ITEMS .register("electron",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> NUCLEUS = ITEMS .register("nucleus",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AURORA_DUST = ITEMS.register("aurora_dust",
            () -> new ItemNameBlockItem(ModBlocks.AURORA_WIRE.get(), new Item.Properties()));

    public static final DeferredItem<Item> CLAY_DUST = ITEMS.register("clay_dust",
            () -> new ItemNameBlockItem(ModBlocks.CLAY_WIRE.get(), new Item.Properties()));

    public static final DeferredItem<Item> LATEX_CLAY_BALL = ITEMS .register("latex_clay_ball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> HARD_LATEX_CLAY_BALL = ITEMS .register("hard_latex_clay_ball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<NormalItem> IRON_NUCLEUS = ITEMS .register("iron_nucleus",
            () -> new NormalItem(new Item.Properties(), "Fe", 0xffda39));

    public static final DeferredItem<NormalItem> ALUMINIUM_NUCLEUS = ITEMS .register("aluminium_nucleus",
            () -> new NormalItem(new Item.Properties(), "Al", 0xffda39));

    public static final DeferredItem<NormalItem> LEAD_NUCLEUS = ITEMS .register("lead_nucleus",
            () -> new NormalItem(new Item.Properties(), "Pb", 0xffda39));

    public static final DeferredItem<NormalItem> COPPER_NUCLEUS = ITEMS .register("copper_nucleus",
            () -> new NormalItem(new Item.Properties(), "Cu", 0xffda39));

    public static final DeferredItem<NormalItem> SILVER_NUCLEUS = ITEMS .register("silver_nucleus",
            () -> new NormalItem(new Item.Properties(), "Ag", 0xffda39));

    public static final DeferredItem<AtomItem> LITHIUM = ITEMS .register("lithium",
            () -> new AtomItem(new Item.Properties(), "1", "Li", 0xffda39,
                    "3", 474747,
                    "2",0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> BERYLLIUM = ITEMS .register("beryllium",
            () -> new AtomItem(new Item.Properties(), "2", "Be", 0xffda39,
                    "4", 474747,
                    "2",0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> SODIUM = ITEMS .register("sodium",
            () -> new AtomItem(new Item.Properties(), "1", "Na", 0xffda39,
                    "11", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> MAGNESIUM = ITEMS .register("magnesium",
            () -> new AtomItem(new Item.Properties(), "2", "Mg", 0xffda39,
                    "12", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> ALUMINIUM = ITEMS .register("aluminium",
            () -> new AtomItem(new Item.Properties(), "3", "Al", 0xffda39,
                    "13", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "3", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> POTASSIUM = ITEMS .register("potassium",
            () -> new AtomItem(new Item.Properties(), "1", "K", 0xffda39,
                    "19", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "8", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> CALCIUM = ITEMS .register("calcium",
            () -> new AtomItem(new Item.Properties(), "2", "Ca", 0xffda39,
                    "20", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> SCANDIUM = ITEMS .register("scandium",
            () -> new AtomItem(new Item.Properties(), "2", "Sc", 0xffda39,
                    "21", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> TITANIUM = ITEMS .register("titanium",
            () -> new AtomItem(new Item.Properties(), "2", "Ti", 0xffda39,
                    "22", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "10", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> VANADIUM = ITEMS .register("vanadium",
            () -> new AtomItem(new Item.Properties(), "2", "V", 0xffda39,
                    "23", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "11", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> CHROMIUM = ITEMS .register("chromium",
            () -> new AtomItem(new Item.Properties(), "1", "Cr", 0xffda39,
                    "24", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "13", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> MANGANESE = ITEMS .register("manganese",
            () -> new AtomItem(new Item.Properties(), "2", "Mn", 0xffda39,
                    "25", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "13", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> IRON = ITEMS .register("iron",
            () -> new AtomItem(new Item.Properties(), "2", "Fe", 0xffda39,
                    "26", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "14", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> COBALT = ITEMS .register("cobalt",
            () -> new AtomItem(new Item.Properties(), "2", "Co", 0xffda39,
                    "27", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "15", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> NICKEL = ITEMS .register("nickel",
            () -> new AtomItem(new Item.Properties(), "2", "Ni", 0xffda39,
                    "28", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "16", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> COPPER = ITEMS .register("copper",
            () -> new AtomItem(new Item.Properties(), "1", "Cu", 0xffda39,
                    "29", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> ZINC = ITEMS .register("zinc",
            () -> new AtomItem(new Item.Properties(), "2", "Zn", 0xffda39,
                    "30", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> GALLIUM = ITEMS .register("gallium",
            () -> new AtomItem(new Item.Properties(), "3", "Ga", 0xffda39,
                    "31", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "3", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> RUBIDIUM = ITEMS .register("rubidium",
            () -> new AtomItem(new Item.Properties(), "1", "Rb", 0xffda39,
                    "37", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "8", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> STRONTIUM = ITEMS .register("strontium",
            () -> new AtomItem(new Item.Properties(), "2", "Sr", 0xffda39,
                    "38", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> YTTRIUM = ITEMS .register("yttrium",
            () -> new AtomItem(new Item.Properties(), "2", "Y", 0xffda39,
                    "39", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> ZIRCONIUM = ITEMS .register("zirconium",
            () -> new AtomItem(new Item.Properties(), "2", "Zr", 0xffda39,
                    "40", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "10", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> NIOBIUM = ITEMS .register("niobium",
            () -> new AtomItem(new Item.Properties(), "1", "Nb", 0xffda39,
                    "41", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "12", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> MOLYBDENUM = ITEMS .register("molybdenum",
            () -> new AtomItem(new Item.Properties(), "1", "Mo", 0xffda39,
                    "42", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "13", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> TECHNETIUM = ITEMS .register("technetium",
            () -> new AtomItem(new Item.Properties(), "2", "Tc", 0xffda39,
                    "43", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "13", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> RUTHENIUM = ITEMS .register("ruthenium",
            () -> new AtomItem(new Item.Properties(), "1", "Ru", 0xffda39,
                    "44", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "15", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> RHODIUM = ITEMS .register("rhodium",
            () -> new AtomItem(new Item.Properties(), "1", "Rh", 0xffda39,
                    "45", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "16", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> PALLADIUM = ITEMS .register("palladium",
            () -> new AtomItem(new Item.Properties(), "18", "Pd", 0xffda39,
                    "46", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> SILVER = ITEMS .register("silver",
            () -> new AtomItem(new Item.Properties(), "1", "Ag", 0xffda39,
                    "47", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> CADMIUM = ITEMS .register("cadmium",
            () -> new AtomItem(new Item.Properties(), "2", "Cd", 0xffda39,
                    "48", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> INDIUM = ITEMS .register("indium",
            () -> new AtomItem(new Item.Properties(), "3", "In", 0xffda39,
                    "49", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "3", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> TIN = ITEMS .register("tin",
            () -> new AtomItem(new Item.Properties(), "4", "Sn", 0xffda39,
                    "50", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "4", 0x282834,
                    "0", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> CAESIUM = ITEMS .register("caesium",
            () -> new AtomItem(new Item.Properties(), "1", "Cs", 0xffda39,
                    "55", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "8", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> BARIUM = ITEMS .register("barium",
            () -> new AtomItem(new Item.Properties(), "2", "Ba", 0xffda39,
                    "56", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> LANTHANUM = ITEMS .register("lanthanum",
            () -> new AtomItem(new Item.Properties(), "2", "La", 0xffda39,
                    "57", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "18", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> CERIUM = ITEMS .register("cerium",
            () -> new AtomItem(new Item.Properties(), "2", "Ce", 0xffda39,
                    "58", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "19", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> PRASEODYMIUM = ITEMS .register("praseodymium",
            () -> new AtomItem(new Item.Properties(), "2", "Pr", 0xffda39,
                    "59", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "21", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> NEODYMIUM = ITEMS .register("neodymium",
            () -> new AtomItem(new Item.Properties(), "2", "Nd", 0xffda39,
                    "60", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "22", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> PROMETHIUM = ITEMS .register("promethium",
            () -> new AtomItem(new Item.Properties(), "2", "Pm", 0xffda39,
                    "61", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "23", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> SAMARIUM = ITEMS .register("samarium",
            () -> new AtomItem(new Item.Properties(), "2", "Sm", 0xffda39,
                    "62", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "24", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> EUROPIUM = ITEMS .register("europium",
            () -> new AtomItem(new Item.Properties(), "2", "Eu", 0xffda39,
                    "63", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "25", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> GADOLINIUM = ITEMS .register("gadolinium",
            () -> new AtomItem(new Item.Properties(), "2", "Gd", 0xffda39,
                    "64", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "25", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> TERBIUM = ITEMS .register("terbium",
            () -> new AtomItem(new Item.Properties(), "2", "Tb", 0xffda39,
                    "65", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "27", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> DYSPROSIUM = ITEMS .register("dysprosium",
            () -> new AtomItem(new Item.Properties(), "2", "Dy", 0xffda39,
                    "66", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "28", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> HOLMIUM = ITEMS .register("holmium",
            () -> new AtomItem(new Item.Properties(), "2", "Ho", 0xffda39,
                    "67", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "29", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> ERBIUM = ITEMS .register("erbium",
            () -> new AtomItem(new Item.Properties(), "2", "Er", 0xffda39,
                    "68", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "30", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> THULIUM = ITEMS .register("thulium",
            () -> new AtomItem(new Item.Properties(), "2", "Tm", 0xffda39,
                    "69", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "31", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> YTTERBIUM = ITEMS .register("ytterbium",
            () -> new AtomItem(new Item.Properties(), "2", "Yb", 0xffda39,
                    "70", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> LUTETIUM = ITEMS .register("lutetium",
            () -> new AtomItem(new Item.Properties(), "2", "Lu", 0xffda39,
                    "71", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> HAFNIUM = ITEMS .register("hafnium",
            () -> new AtomItem(new Item.Properties(), "2", "Hf", 0xffda39,
                    "72", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "10", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> TANTALUM = ITEMS .register("tantalum",
            () -> new AtomItem(new Item.Properties(), "2", "Ta", 0xffda39,
                    "73", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "11", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> TUNGSTEN = ITEMS .register("tungsten",
            () -> new AtomItem(new Item.Properties(), "2", "W", 0xffda39,
                    "74", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "12", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> RHENIUM = ITEMS .register("rhenium",
            () -> new AtomItem(new Item.Properties(), "2", "Re", 0xffda39,
                    "75", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "13", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> OSMIUM = ITEMS .register("osmium",
            () -> new AtomItem(new Item.Properties(), "2", "Os", 0xffda39,
                    "76", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "14", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> IRIDIUM = ITEMS .register("iridium",
            () -> new AtomItem(new Item.Properties(), "2", "Ir", 0xffda39,
                    "77", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "15", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> PLATINUM = ITEMS .register("platinum",
            () -> new AtomItem(new Item.Properties(), "1", "Pt", 0xffda39,
                    "78", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "17", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> GOLD = ITEMS .register("gold",
            () -> new AtomItem(new Item.Properties(), "1", "Au", 0xffda39,
                    "79", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "1", 0x282834,
                    "0", 0x282834));

    //Mercury/kviksølv
    public static final DeferredItem<AtomItem> MERCURY = ITEMS .register("mercury",
            () -> new AtomItem(new Item.Properties(), "2", "Hg", 0xffda39,
                    "80", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "2", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> THALLIUM = ITEMS .register("thallium",
            () -> new AtomItem(new Item.Properties(), "3", "Ti", 0xffda39,
                    "81", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "3", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> LEAD = ITEMS .register("lead",
            () -> new AtomItem(new Item.Properties(), "4", "Pb", 0xffda39,
                    "82", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "4", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> BISMUTH = ITEMS .register("bismuth",
            () -> new AtomItem(new Item.Properties(), "5", "Bi", 0xffda39,
                    "83", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "5", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> POLONIUM = ITEMS .register("polonium",
            () -> new AtomItem(new Item.Properties(), "6", "Po", 0xffda39,
                    "83", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "6", 0x282834,
                    "0", 0x282834));

    public static final DeferredItem<AtomItem> FRANCIUM = ITEMS .register("francium",
            () -> new AtomItem(new Item.Properties(), "1", "Fr", 0xffda39,
                    "87", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "8", 0x282834,
                    "1", 0x282834));

    public static final DeferredItem<AtomItem> RADIUM = ITEMS .register("radium",
            () -> new AtomItem(new Item.Properties(), "2", "Ra", 0xffda39,
                    "88", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> ACTINIUM = ITEMS .register("actinium",
            () -> new AtomItem(new Item.Properties(), "2", "Ac", 0xffda39,
                    "89", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> THORIUM = ITEMS .register("thorium",
            () -> new AtomItem(new Item.Properties(), "2", "Th", 0xffda39,
                    "90", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "18", 0x282834,
                    "10", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> PROTACTINIUM = ITEMS .register("protactinium",
            () -> new AtomItem(new Item.Properties(), "2", "Pa", 0xffda39,
                    "91", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "20", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> URANIUM = ITEMS .register("uranium",
            () -> new AtomItem(new Item.Properties(), "2", "U", 0xffda39,
                    "92", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "21", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> NEPTUNIUM = ITEMS .register("neptunium",
            () -> new AtomItem(new Item.Properties(), "2", "Np", 0xffda39,
                    "93", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "22", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> PLUTONIUM = ITEMS .register("plutonium",
            () -> new AtomItem(new Item.Properties(), "2", "Pu", 0xffda39,
                    "94", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "24", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> AMERICIUM = ITEMS .register("americium",
            () -> new AtomItem(new Item.Properties(), "2", "Am", 0xffda39,
                    "95", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "25", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> CURIUM = ITEMS .register("curium",
            () -> new AtomItem(new Item.Properties(), "2", "Cm", 0xffda39,
                    "96", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "25", 0x282834,
                    "9", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> BERKELIUM = ITEMS .register("berkelium",
            () -> new AtomItem(new Item.Properties(), "2", "Bk", 0xffda39,
                    "97", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "27", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> CALIFORNIUM = ITEMS .register("californium",
            () -> new AtomItem(new Item.Properties(), "2", "Cf", 0xffda39,
                    "98", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "28", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> EINSTEINIUM = ITEMS .register("einsteinium",
            () -> new AtomItem(new Item.Properties(), "2", "Es", 0xffda39,
                    "99", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "29", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> FERMUM = ITEMS .register("fermium",
            () -> new AtomItem(new Item.Properties(), "2", "Fm", 0xffda39,
                    "100", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "30", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> MENDELEVIUM = ITEMS .register("mendelevium",
            () -> new AtomItem(new Item.Properties(), "2", "Md", 0xffda39,
                    "101", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "31", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> NOBELIUM = ITEMS .register("nobelium",
            () -> new AtomItem(new Item.Properties(), "2", "No", 0xffda39,
                    "102", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "8", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> LAWRENCIUM = ITEMS .register("lawrencium",
            () -> new AtomItem(new Item.Properties(), "3", "Lr", 0xffda39,
                    "103", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "8", 0x282834,
                    "3", 0x282834));

    public static final DeferredItem<AtomItem> RUTHERFORDIUM = ITEMS .register("rutherfordium",
            () -> new AtomItem(new Item.Properties(), "2", "Rf", 0xffda39,
                    "104", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "10", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> DUBNIUM = ITEMS .register("dubnium",
            () -> new AtomItem(new Item.Properties(), "2", "Db", 0xffda39,
                    "105", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "11", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> SEABORGIUM = ITEMS .register("seaborgium",
            () -> new AtomItem(new Item.Properties(), "2", "Sg", 0xffda39,
                    "106", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "12", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> BOHRIUM = ITEMS .register("bohrium",
            () -> new AtomItem(new Item.Properties(), "2", "Bh", 0xffda39,
                    "107", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "13", 0x282834,
                    "2", 0x282834));

    public static final DeferredItem<AtomItem> HASSIUM = ITEMS .register("hassium",
            () -> new AtomItem(new Item.Properties(), "2", "Hs", 0xffda39,
                    "108", 474747,
                    "2",0x282834,
                    "8", 0x282834,
                    "18", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "14", 0x282834,
                    "2", 0x282834));



    public static final DeferredItem<AtomItem> CRYON = ITEMS .register("cryon",
            () -> new AtomItem(new Item.Properties(), "18", "Cy", 0xffda39,
                    "150", 474747,
                    "5",0x282834,
                    "21", 0x282834,
                    "19", 0x282834,
                    "32", 0x282834,
                    "32", 0x282834,
                    "14", 0x282834,
                    "18", 0x282834));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}