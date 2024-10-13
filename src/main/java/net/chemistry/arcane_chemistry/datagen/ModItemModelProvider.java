package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.item.custom.AtomItem;
import net.chemistry.arcane_chemistry.item.custom.ElementItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Arcane_chemistry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        handheldItem(ModItems.FLINT_SWORD);
        handheldItem(ModItems.FLINT_PICKAXE);
        handheldItem(ModItems.FLINT_AXE);
        handheldItem(ModItems.FLINT_SHOVEL);
        handheldItem(ModItems.FLINT_HOE);

        handheldItem(ModItems.PEBBLE);

        // Alkali Metals
        atomItem(ModItems.LITHIUM);     // Lithium
        atomItem(ModItems.SODIUM);      // Sodium
        atomItem(ModItems.POTASSIUM);   // Potassium
        atomItem(ModItems.RUBIDIUM);    // Rubidium
        atomItem(ModItems.CAESIUM);     // Caesium
        atomItem(ModItems.FRANCIUM);    // Francium

        // Alkaline Earth Metals
        atomItem(ModItems.BERYLLIUM);   // Beryllium
        atomItem(ModItems.MAGNESIUM);   // Magnesium
        atomItem(ModItems.CALCIUM);     // Calcium
        atomItem(ModItems.STRONTIUM);   // Strontium
        atomItem(ModItems.BARIUM);      // Barium
        atomItem(ModItems.RADIUM);      // Radium

        // Transition Metals
        atomItem(ModItems.SCANDIUM);    // Scandium
        atomItem(ModItems.TITANIUM);    // Titanium
        atomItem(ModItems.VANADIUM);    // Vanadium
        atomItem(ModItems.CHROMIUM);    // Chromium
        atomItem(ModItems.MANGANESE);   // Manganese
        atomItem(ModItems.IRON);        // Iron
        atomItem(ModItems.COBALT);      // Cobalt
        atomItem(ModItems.NICKEL);      // Nickel
        atomItem(ModItems.COPPER);      // Copper
        atomItem(ModItems.ZINC);        // Zinc
        atomItem(ModItems.YTTRIUM);     // Yttrium
        atomItem(ModItems.ZIRCONIUM);   // Zirconium
        atomItem(ModItems.NIOBIUM);     // Niobium
        atomItem(ModItems.MOLYBDENUM);  // Molybdenum
        atomItem(ModItems.TECHNETIUM);  // Technetium
        atomItem(ModItems.RUTHENIUM);   // Ruthenium
        atomItem(ModItems.RHODIUM);     // Rhodium
        atomItem(ModItems.PALLADIUM);   // Palladium
        atomItem(ModItems.SILVER);      // Silver
        atomItem(ModItems.CADMIUM);     // Cadmium
        atomItem(ModItems.HAFNIUM);     // Hafnium
        atomItem(ModItems.TANTALUM);    // Tantalum
        atomItem(ModItems.TUNGSTEN);    // Tungsten
        atomItem(ModItems.RHENIUM);     // Rhenium
        atomItem(ModItems.OSMIUM);      // Osmium
        atomItem(ModItems.IRIDIUM);     // Iridium
        atomItem(ModItems.PLATINUM);    // Platinum
        atomItem(ModItems.GOLD);        // Gold
        atomItem(ModItems.MERCURY);     // Mercury

        // Post-Transition Metals
        atomItem(ModItems.ALUMINIUM);   // Aluminium
        atomItem(ModItems.GALLIUM);     // Gallium
        atomItem(ModItems.INDIUM);      // Indium
        atomItem(ModItems.TIN);         // Tin
        atomItem(ModItems.THALLIUM);    // Thallium
        atomItem(ModItems.LEAD);        // Lead
        atomItem(ModItems.BISMUTH);     // Bismuth

        // Lanthanides
        atomItem(ModItems.LANTHANUM);   // Lanthanum
        atomItem(ModItems.CERIUM);      // Cerium
        atomItem(ModItems.PRASEODYMIUM);// Praseodymium
        atomItem(ModItems.NEODYMIUM);   // Neodymium
        atomItem(ModItems.PROMETHIUM);  // Promethium
        atomItem(ModItems.SAMARIUM);    // Samarium
        atomItem(ModItems.EUROPIUM);    // Europium
        atomItem(ModItems.GADOLINIUM);  // Gadolinium
        atomItem(ModItems.TERBIUM);     // Terbium
        atomItem(ModItems.DYSPROSIUM);  // Dysprosium
        atomItem(ModItems.HOLMIUM);     // Holmium
        atomItem(ModItems.ERBIUM);      // Erbium
        atomItem(ModItems.THULIUM);     // Thulium
        atomItem(ModItems.YTTERBIUM);   // Ytterbium
        atomItem(ModItems.LUTETIUM);    // Lutetium

        // Actinides
        atomItem(ModItems.ACTINIUM);    // Actinium
        atomItem(ModItems.THORIUM);     // Thorium
        atomItem(ModItems.PROTACTINIUM);// Protactinium
        atomItem(ModItems.URANIUM);     // Uranium
        atomItem(ModItems.NEPTUNIUM);   // Neptunium
        atomItem(ModItems.PLUTONIUM);   // Plutonium
        atomItem(ModItems.AMERICIUM);   // Americium
        atomItem(ModItems.CURIUM);      // Curium
        atomItem(ModItems.BERKELIUM);   // Berkelium
        atomItem(ModItems.CALIFORNIUM);// Californium
        atomItem(ModItems.EINSTEINIUM);// Einsteinium
        atomItem(ModItems.FERMUM);      // Fermium
        atomItem(ModItems.MENDELEVIUM);// Mendelevium
        atomItem(ModItems.NOBELIUM);    // Nobelium
        atomItem(ModItems.LAWRENCIUM);  // Lawrencium
    }

    private ItemModelBuilder atomItem(DeferredItem<AtomItem> item) {
        int maxSkall = 18;
        String textureLayer = "elektron_1";

        AtomItem atomItemInstance = item.get();
        String Skall = atomItemInstance.Skall;

        String ElementName = atomItemInstance.elementName;

        try {
            int skallValue = Integer.parseInt(Skall);

            if (skallValue >= 1 && skallValue <= maxSkall) {
                textureLayer = "elektron_" + skallValue;
            }
        } catch (NumberFormatException e) {
            textureLayer = "elektron_1";
        }

        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "item/" + textureLayer));
    }


    private ItemModelBuilder handheldItem(DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID,"block/" + item.getId().getPath()));
    }
    
    public void buttonItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

}