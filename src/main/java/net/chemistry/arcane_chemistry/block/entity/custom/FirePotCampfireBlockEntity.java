package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.FirePotRecipe;
import net.chemistry.arcane_chemistry.recipes.FirePotRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.screen.NickelCompreserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock.ENABLED;
import static net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock.LIT;

public class FirePotCampfireBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inputItems = createItemHandler(5);
    private final ItemStackHandler outputItems = createItemHandler(2);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private int progress = 0;
    private int maxProgress = 50;

    public final ContainerData data;

    public FirePotCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_POT_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FirePotCampfireBlockEntity.this.progress;
                    case 1 -> FirePotCampfireBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FirePotCampfireBlockEntity.this.progress = pValue;
                    case 1 -> FirePotCampfireBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void tick(BlockPos pos, BlockState state, FirePotCampfireBlockEntity blockEntity) {
        boolean isLit = state.getValue(LIT);
        boolean hasWaterBucketInSlot0 = blockEntity.inputItems.getStackInSlot(0).getItem() == Items.WATER_BUCKET;

        if (hasWaterBucketInSlot0) {
            level.setBlockAndUpdate(pos, state.setValue(ENABLED, true));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(ENABLED, false));
        }

        if (hasRecipe()) {
            increaseCraftingProcess();

            if (!isLit) {
                level.setBlockAndUpdate(pos, state.setValue(LIT, true));
            }

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            blockEntity.progress = Math.max(blockEntity.progress - 2, 0);

            if (isLit) {
                level.setBlockAndUpdate(pos, state.setValue(LIT, false));
            }
        }
    }

    private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<FirePotRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FIRE_POT_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlots(inventory) &&
                canInsertItemsIntoOutputSlots(inventory, recipe.get().value().output.copy(), recipe.get().value().output2.copy());
    }

    private void craftItem() {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<FirePotRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FIRE_POT_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (recipeOptional.isPresent()) {
            FirePotRecipe recipe = recipeOptional.get().value();
            ItemStack outputStack1 = recipe.output.copy();
            ItemStack outputStack2 = recipe.output2.copy();

            dropItemInWorld(level, worldPosition, outputStack1);
            dropItemInWorld(level, worldPosition, outputStack2);

            removeUsedIngredients(inventory, recipe);
        }
    }

    private void dropItemInWorld(Level level, BlockPos position, ItemStack stack) {
        if (!stack.isEmpty()) {

            ItemEntity itemEntity = new ItemEntity(level, position.getX() + 0.5, position.getY() + 1.1, position.getZ() + 0.5, stack);
            itemEntity.setDeltaMovement(0, 0.2, 0f);
            level.addFreshEntity(itemEntity);
        }
    }

    private void removeUsedIngredients(SimpleContainer inventory, FirePotRecipe recipe) {
        if (recipe.ingredient0.test(inventory.getItem(0)) && !inventory.getItem(0).is(Items.WATER_BUCKET)) {
            inputItems.extractItem(0, 1, false);
        }
        if (recipe.ingredient1.isPresent() && recipe.ingredient1.get().test(inventory.getItem(1)) && !inventory.getItem(1).is(Items.WATER_BUCKET)) {
            inputItems.extractItem(1, 1, false);
        }
        if (recipe.ingredient2.isPresent() && recipe.ingredient2.get().test(inventory.getItem(2)) && !inventory.getItem(2).is(Items.WATER_BUCKET)) {
            inputItems.extractItem(2, 1, false);
        }
        if (recipe.ingredient3.isPresent() && recipe.ingredient3.get().test(inventory.getItem(3)) && !inventory.getItem(3).is(Items.WATER_BUCKET)) {
            inputItems.extractItem(3, 1, false);
        }
        if (recipe.ingredient4.isPresent() && recipe.ingredient4.get().test(inventory.getItem(4)) && !inventory.getItem(4).is(Items.WATER_BUCKET)) {
            inputItems.extractItem(4, 1, false);
        }
    }


    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
    }

    private boolean canInsertAmountIntoOutputSlots(SimpleContainer inventory) {
        ItemStack outputStack1 = outputItems.getStackInSlot(0);
        ItemStack outputStack2 = outputItems.getStackInSlot(1);

        return (outputStack1.getMaxStackSize() > outputStack1.getCount()) &&
                (outputStack2.getMaxStackSize() > outputStack2.getCount());
    }

    private boolean canInsertItemsIntoOutputSlots(SimpleContainer inventory, ItemStack stack1, ItemStack stack2) {
        ItemStack outputStack1 = outputItems.getStackInSlot(0);
        ItemStack outputStack2 = outputItems.getStackInSlot(1);

        return (outputStack1.isEmpty() || (outputStack1.getItem() == stack1.getItem() && outputStack1.getCount() < stack1.getMaxStackSize())) &&
                (outputStack2.isEmpty() || (outputStack2.getItem() == stack2.getItem() && outputStack2.getCount() < stack2.getMaxStackSize()));
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("my_block_entity.progress", this.progress);
        tag.putInt("my_block_entity.max_progress", this.maxProgress);
        tag.put("my_block_entity.inputs", inputItems.serializeNBT(registries));
        tag.put("my_block_entity.outputs", outputItems.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("my_block_entity.progress");
        maxProgress = tag.getInt("my_block_entity.max_progress");
        inputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.inputs"));
        outputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.outputs"));
    }

    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public Lazy<IItemHandler> getItemHandler() {
        return itemHandler;
    }

    public ItemStackHandler getInputItems() {
        return inputItems;
    }

    public ItemStackHandler getOutputItems() {
        return outputItems;
    }

    public Lazy<IItemHandler> getInputItemHandler() {
        return inputItemHandler;
    }

    public Lazy<IItemHandler> getOutputItemHandler() {
        return outputItemHandler;
    }


    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    @Nullable
    @Override
    public @javax.annotation.Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new NickelCompreserMenu(containerId, player, this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.arcane_chemistry.electrolyzer");
    }

    public boolean isInputEmpty(int slot) {
        return inputItems.getStackInSlot(slot).isEmpty();
    }

    public boolean isOutputEmpty(int slot) {
        return outputItems.getStackInSlot(slot).isEmpty();
    }

    public void clearInput(int slot) {
        inputItems.setStackInSlot(slot, ItemStack.EMPTY);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void clearOutput(int slot) {
        outputItems.setStackInSlot(slot, ItemStack.EMPTY);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public Lazy<IItemHandler> getItems() {
        return itemHandler;
    }

    public ItemStack getItem(int pSlot) {
        if (pSlot < 5) {
            return inputItems.getStackInSlot(pSlot);
        } else {
            return outputItems.getStackInSlot(pSlot - 5);
        }
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot < 5) {
            inputItems.setStackInSlot(slot, stack);
        } else {
            outputItems.setStackInSlot(slot - 2, stack);
        }
        setChanged();
        if (!level.isClientSide) {
            markForUpdate();
        }
    }

    private void markForUpdate() {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}