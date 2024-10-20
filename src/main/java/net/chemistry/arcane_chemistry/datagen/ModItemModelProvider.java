package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.item.custom.AtomItem;
import net.chemistry.arcane_chemistry.item.custom.AtomItemFormelReagent;
import net.chemistry.arcane_chemistry.item.custom.AtomItemReagent;
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

        basicItem(ModItems.PEBBLE.get());
        basicItem(ModItems.NICKEL_INGOT.get());
        basicItem(ModItems.RAW_NICKEL.get());
        basicItem(ModItems.RAW_IMPURE_NICKEL_IRON_MIX.get());
        basicItem(ModItems.RAW_IMPURE_NICKEL.get());
        basicItem(ModItems.RAW_IMPURE_IRON.get());
        basicItem(ModItems.CRUSHED_RAW_IRON.get());

        basicItem(ModItems.SCHEELITE_CRYSTAL.get());
        basicItem(ModItems.CRUSHED_SCHEELITE_CRYSTAL.get());
        basicItem(ModItems.RAW_COBALT.get());
        basicItem(ModItems.COBALT_INGOT.get());
        basicItem(ModItems.CARBON_CHUNK.get());
        basicItem(ModItems.RAW_CARBIDE.get());
        basicItem(ModItems.CARBIDE_INGOT.get());
        basicItem(ModItems.TUNGSTEN_INGOT.get());
        basicItem(ModItems.TUNGSTEN_CARBIDE_INGOT.get());
        basicItem(ModItems.VANADIUM_INGOT.get());
        basicItem(ModItems.VANADIUM_CATALYST.get());
        basicItem(ModItems.CHROMIUM_INGOT.get());

        basicItem(ModItems.SALT.get());

        basicItem(ModItems.REAGENT.get());
        basicItem(ModItems.SODIUM_CHROMATE.get());

        basicItem(ModItems.SULFUR.get());
        basicItem(ModItems.SULFURIC_ACID_MIX.get());
        basicItem(ModItems.SULFURIC_ACID.get());
        basicItem(ModItems.SODIUM_DICHROMATE.get());

        reagentFormelItem(ModItems.WATER_REAGENT);

        reagentFormelItem(ModItems.CHLORINE_GAS);
        reagentFormelItem(ModItems.SODIUM_CHLORIDE);
        reagentFormelItem(ModItems.SALT_REAGENT);

        reagentItem(ModItems.IRON_REAGENT);
        reagentItem(ModItems.RED_IRON_REAGENT);

        atomItem(ModItems.LITHIUM);
        atomItem(ModItems.SODIUM);
        atomItem(ModItems.POTASSIUM);
        atomItem(ModItems.RUBIDIUM);
        atomItem(ModItems.CAESIUM);
        atomItem(ModItems.FRANCIUM);
        atomItem(ModItems.BERYLLIUM);
        atomItem(ModItems.MAGNESIUM);
        atomItem(ModItems.CALCIUM);
        atomItem(ModItems.STRONTIUM);
        atomItem(ModItems.BARIUM);
        atomItem(ModItems.RADIUM);
        atomItem(ModItems.SCANDIUM);
        atomItem(ModItems.TITANIUM);
        atomItem(ModItems.VANADIUM);
        atomItem(ModItems.CHROMIUM);
        atomItem(ModItems.MANGANESE);
        atomItem(ModItems.IRON);
        atomItem(ModItems.COBALT);
        atomItem(ModItems.NICKEL);
        atomItem(ModItems.COPPER);
        atomItem(ModItems.ZINC);
        atomItem(ModItems.YTTRIUM);
        atomItem(ModItems.ZIRCONIUM);
        atomItem(ModItems.NIOBIUM);
        atomItem(ModItems.MOLYBDENUM);
        atomItem(ModItems.TECHNETIUM);
        atomItem(ModItems.RUTHENIUM);
        atomItem(ModItems.RHODIUM);
        atomItem(ModItems.PALLADIUM);
        atomItem(ModItems.SILVER);
        atomItem(ModItems.CADMIUM);
        atomItem(ModItems.HAFNIUM);
        atomItem(ModItems.TANTALUM);
        atomItem(ModItems.TUNGSTEN);
        atomItem(ModItems.RHENIUM);
        atomItem(ModItems.OSMIUM);
        atomItem(ModItems.IRIDIUM);
        atomItem(ModItems.PLATINUM);
        atomItem(ModItems.GOLD);
        atomItem(ModItems.MERCURY);
        atomItem(ModItems.ALUMINIUM);
        atomItem(ModItems.GALLIUM);
        atomItem(ModItems.INDIUM);
        atomItem(ModItems.TIN);
        atomItem(ModItems.THALLIUM);
        atomItem(ModItems.LEAD);
        atomItem(ModItems.BISMUTH);
        atomItem(ModItems.POLONIUM);
        atomItem(ModItems.LANTHANUM);
        atomItem(ModItems.CERIUM);
        atomItem(ModItems.PRASEODYMIUM);
        atomItem(ModItems.NEODYMIUM);
        atomItem(ModItems.PROMETHIUM);
        atomItem(ModItems.SAMARIUM);
        atomItem(ModItems.EUROPIUM);
        atomItem(ModItems.GADOLINIUM);
        atomItem(ModItems.TERBIUM);
        atomItem(ModItems.DYSPROSIUM);
        atomItem(ModItems.HOLMIUM);
        atomItem(ModItems.ERBIUM);
        atomItem(ModItems.THULIUM);
        atomItem(ModItems.YTTERBIUM);
        atomItem(ModItems.LUTETIUM);
        atomItem(ModItems.ACTINIUM);
        atomItem(ModItems.THORIUM);
        atomItem(ModItems.PROTACTINIUM);
        atomItem(ModItems.URANIUM);
        atomItem(ModItems.NEPTUNIUM);
        atomItem(ModItems.PLUTONIUM);
        atomItem(ModItems.AMERICIUM);
        atomItem(ModItems.CURIUM);
        atomItem(ModItems.BERKELIUM);
        atomItem(ModItems.CALIFORNIUM);
        atomItem(ModItems.EINSTEINIUM);
        atomItem(ModItems.FERMUM);
        atomItem(ModItems.MENDELEVIUM);
        atomItem(ModItems.NOBELIUM);
        atomItem(ModItems.LAWRENCIUM);
        atomItem(ModItems.RUTHERFORDIUM);
        atomItem(ModItems.DUBNIUM);
        atomItem(ModItems.SEABORGIUM);
        atomItem(ModItems.BOHRIUM);
        atomItem(ModItems.HASSIUM);

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

    private ItemModelBuilder reagentItem(DeferredItem<AtomItemReagent> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "item/reagent"))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder reagentFormelItem(DeferredItem<AtomItemFormelReagent> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "item/reagent"))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "item/" + item.getId().getPath()));
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