package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.NickelCompreserBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.ElectrolyzerRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.recipes.ElectrolyzerRecipe;
import net.chemistry.arcane_chemistry.screen.NickelCompreserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
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

import static net.chemistry.arcane_chemistry.block.custom.ElectrolyzerBlock.LIT;
import static net.chemistry.arcane_chemistry.block.custom.ElectrolyzerBlock.EXTENDED;

public class ElectrolyzerBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    private final ItemStackHandler inputItems = createItemHandler(2);
    private final ItemStackHandler outputItems = createItemHandler(2);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 3};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};

    private int progress = 0;
    private int maxProgress = 300;

    public final ContainerData data;

    public ElectrolyzerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTOLYZER_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ElectrolyzerBlockEntity.this.progress;
                    case 1 -> ElectrolyzerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ElectrolyzerBlockEntity.this.progress = pValue;
                    case 1 -> ElectrolyzerBlockEntity.this.maxProgress = pValue;
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

    public void tick(BlockPos pos, BlockState state, ElectrolyzerBlockEntity blockEntity) {
        boolean isLit = state.getValue(LIT);

        boolean hasWaterBucketInSlot0 = blockEntity.inputItems.getStackInSlot(0).getItem() == Items.WATER_BUCKET;

        if (hasWaterBucketInSlot0) {
            level.setBlockAndUpdate(pos, state.setValue(EXTENDED, true));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(EXTENDED, false));
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

    private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<ElectrolyzerRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.ELECTOLYZER_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

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

        Optional<RecipeHolder<ElectrolyzerRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.ELECTOLYZER_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (recipeOptional.isPresent()) {
            ElectrolyzerRecipe recipe = recipeOptional.get().value();
            ItemStack outputStack1 = recipe.output.copy();
            ItemStack outputStack2 = recipe.output2.copy();

            ItemStack existingOutput1 = outputItems.getStackInSlot(0);
            ItemStack existingOutput2 = outputItems.getStackInSlot(1);

            int maxStackSize1 = outputStack1.getMaxStackSize();
            int availableSpace1 = maxStackSize1 - existingOutput1.getCount();
            int amountToAdd1 = Math.min(availableSpace1, outputStack1.getCount());

            int maxStackSize2 = outputStack2.getMaxStackSize();
            int availableSpace2 = maxStackSize2 - existingOutput2.getCount();
            int amountToAdd2 = Math.min(availableSpace2, outputStack2.getCount());

            if (existingOutput1.isEmpty()) {
                outputItems.setStackInSlot(0, new ItemStack(outputStack1.getItem(), amountToAdd1));
            } else if (existingOutput1.getItem() == outputStack1.getItem()) {
                existingOutput1.grow(amountToAdd1);
            }

            if (existingOutput2.isEmpty()) {
                outputItems.setStackInSlot(1, new ItemStack(outputStack2.getItem(), amountToAdd2));
            } else if (existingOutput2.getItem() == outputStack2.getItem()) {
                existingOutput2.grow(amountToAdd2);
            }

            if (recipe.ingredient0.test(inventory.getItem(0)) && !inventory.getItem(0).is(Items.WATER_BUCKET)) {
                inputItems.extractItem(0, 1, false);
            }
            if (recipe.ingredient1.isPresent() && recipe.ingredient1.get().test(inventory.getItem(1)) && !inventory.getItem(1).is(Items.WATER_BUCKET)) {
                inputItems.extractItem(1, 1, false);
            }

            outputStack1.shrink(amountToAdd1);
            outputStack2.shrink(amountToAdd2);
        }
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
        if (pSlot < 2) {
            return inputItems.getStackInSlot(pSlot);
        } else {
            return outputItems.getStackInSlot(pSlot - 2);
        }
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot < 2) {
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

    @Override
    public int[] getSlotsForFace(Direction p_58363_) {
        if (p_58363_ == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return p_58363_ == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int p_58336_, ItemStack p_58337_, @javax.annotation.Nullable Direction p_58338_) {
        return this.canPlaceItem(p_58336_, p_58337_);
    }

    @Override
    public boolean canTakeItemThroughFace(int p_58392_, ItemStack p_58393_, Direction p_58394_) {
        return p_58394_ == Direction.DOWN && p_58392_ == 1 ? p_58393_.is(Items.WATER_BUCKET) || p_58393_.is(Items.BUCKET) : true;
    }

    @Override
    public int getContainerSize() {
        return itemHandler.get().getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inputItems.getSlots(); i++) {
            if (!inputItems.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        for (int i = 0; i < outputItems.getSlots(); i++) {
            if (!outputItems.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack removeItem(int slotIndex, int count) {
        int adjustedIndex = slotIndex - inputItems.getSlots();

        if (adjustedIndex < outputItems.getSlots()) {
            ItemStack stackInSlot = outputItems.getStackInSlot(adjustedIndex);
            if (!stackInSlot.isEmpty()) {
                return outputItems.extractItem(adjustedIndex, count, false);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slotIndex) {
        if (slotIndex < inputItems.getSlots()) {
            ItemStack stackInSlot = inputItems.getStackInSlot(slotIndex);

            if (!stackInSlot.isEmpty()) {
                inputItems.setStackInSlot(slotIndex, ItemStack.EMPTY);
                return stackInSlot;
            }
        }
        int adjustedIndex = slotIndex - inputItems.getSlots();
        if (adjustedIndex < outputItems.getSlots()) {
            ItemStack stackInSlot = outputItems.getStackInSlot(adjustedIndex);

            if (!stackInSlot.isEmpty()) {
                outputItems.setStackInSlot(adjustedIndex, ItemStack.EMPTY);
                return stackInSlot;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        final double MAX_DISTANCE = 64.0;
        double distanceSquared = player.distanceToSqr(this.worldPosition.getX() + 0.5,
                this.worldPosition.getY() + 0.5,
                this.worldPosition.getZ() + 0.5);
        return distanceSquared <= MAX_DISTANCE;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inputItems.setStackInSlot(i, ItemStack.EMPTY);
        }

        for (int i = 0; i < outputItems.getSlots(); i++) {
            outputItems.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}