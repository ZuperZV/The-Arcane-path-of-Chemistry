package net.chemistry.arcane_chemistry.datagen;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.HARD_OVEN.get());

        dropSelf(ModBlocks.FIRE_POT.get());

        dropSelf(ModBlocks.GRAVITY.get());
        dropSelf(ModBlocks.GRAVITY_CONTROLLER.get());

        dropSelf(ModBlocks.NICKEL_BLOCK.get());
        dropSelf(ModBlocks.TUNGSTEN_BLOCK.get());

        dropSelf(ModBlocks.NICKEL_COMPRESER.get());
        dropSelf(ModBlocks.TUNGSTEN_COMPRESER.get());

        dropSelf(ModBlocks.HEATER.get());
        dropSelf(ModBlocks.CHAMBER.get());
        dropSelf(ModBlocks.DISTILLATION_CONNECTER.get());

        dropSelf(ModBlocks.VANADIUM_BLOCK.get());
        dropSelf(ModBlocks.CHROMIUM_BLOCK.get());

        dropSelf(ModBlocks.ATOMIC_OVEN.get());
        dropSelf(ModBlocks.FLOTATIONER.get());
        dropSelf(ModBlocks.CENTRIFUGE.get());
        dropSelf(ModBlocks.ELECTOLYZER.get());

        dropSelf(ModBlocks.LIMESTONE.get());

        dropSelf(ModBlocks.ATOMIC_NUCLEUS_CONSTRUCTOR.get());
        dropSelf(ModBlocks.LATEX_BOWL.get());

        dropSelf(ModBlocks.ADVANCED_BIO_HARVESTER.get());
        dropSelf(ModBlocks.BIO_HARVESTER.get());

        dropSelf(ModBlocks.PEDESTAL_SLAB.get());
        dropSelf(ModBlocks.AURORA_CRAFTER.get());

        this.add(ModBlocks.FIRE_POT_CAMPFIRE.get(),
                block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.FIRE_POT.get()))
                        )
        );

        this.add(ModBlocks.SULFUR_SOUL_SAND.get(),
                block -> create2ItemsMultipleOreDrops(ModBlocks.SULFUR_SOUL_SAND.get(), Blocks.SOUL_SAND.asItem(), ModItems.SULFUR.get(), 1, 2));

        this.add(ModBlocks.AURORA_WIRE.get(),
                block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.AURORA_DUST.get()))
                        )
        );

        this.add(ModBlocks.CLAY_WIRE.get(),
                block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.CLAY_DUST.get()))
                        )
        );
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }
    protected LootTable.Builder create2ItemsMultipleOreDrops(Block pBlock, Item item, Item block, float minDrops, float maxDrops) {
        LootPool.Builder itemPool = LootPool.lootPool()
                .add(LootItem.lootTableItem(block)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops))));

        LootPool.Builder blockPool = LootPool.lootPool()
                .add(LootItem.lootTableItem(item)
                      .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));

        return LootTable.lootTable()
                .withPool(itemPool)
                .withPool(blockPool);
    }

    protected LootTable.Builder createMultipleItemLogDrops(Block pBlock, Item log, Item item, float minDrops, float maxDrops) {
        LootPool.Builder logPool = LootPool.lootPool()
                .add(LootItem.lootTableItem(log)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));

        LootPool.Builder itemPool = LootPool.lootPool()
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .when(LootItemRandomChanceCondition.randomChance(0.8f)));

        return LootTable.lootTable()
                .withPool(logPool)
                .withPool(itemPool);
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}